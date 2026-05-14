using MicroservicioValoraciones.Eventos;
using MicroservicioValoraciones.Models;

namespace MicroservicioValoraciones.Services;

public class ServicioValoraciones : IServicioValoraciones
{
    private readonly IRepositorioValoraciones _repositorio;
    private readonly IServicioCompraventas _servicioCompraventas;
    private readonly IPublicadorEventos _publicador;

    public ServicioValoraciones(
        IRepositorioValoraciones repositorio,
        IServicioCompraventas servicioCompraventas,
        IPublicadorEventos publicador)
    {
        _repositorio = repositorio;
        _servicioCompraventas = servicioCompraventas;
        _publicador = publicador;
    }

    public Task<Resultado<Valoracion>> RegistrarValoracionVendedorAsync(
        string idCompraventa, string idComprador, int puntuacion, string? comentario)
        => RegistrarAsync(idCompraventa, idComprador, puntuacion, comentario, RolValorado.Vendedor);

    public Task<Resultado<Valoracion>> RegistrarValoracionCompradorAsync(
        string idCompraventa, string idVendedor, int puntuacion, string? comentario)
        => RegistrarAsync(idCompraventa, idVendedor, puntuacion, comentario, RolValorado.Comprador);

    private async Task<Resultado<Valoracion>> RegistrarAsync(
        string idCompraventa,
        string idUsuarioValora,
        int puntuacion,
        string? comentario,
        RolValorado rolValorado)
    {
        if (puntuacion < 1 || puntuacion > 5)
            return Resultado<Valoracion>.Error("La puntuación debe estar entre 1 y 5");

        if (await _repositorio.ExisteValoracionAsync(idCompraventa, rolValorado))
            return Resultado<Valoracion>.Conflict(
                $"Ya existe una valoración para la compraventa {idCompraventa} con rol {rolValorado}");

        var compraventa = await _servicioCompraventas.GetByIdAsync(idCompraventa);

        if (compraventa is null)
            return Resultado<Valoracion>.NotFound($"No existe la compraventa {idCompraventa}");

        var (idEsperado, idUsuarioValorado) = rolValorado == RolValorado.Vendedor
            ? (compraventa.IdComprador, compraventa.IdVendedor)
            : (compraventa.IdVendedor, compraventa.IdComprador);

        if (idUsuarioValora != idEsperado)
        {
            var rolEmisor = rolValorado == RolValorado.Vendedor ? "comprador" : "vendedor";
            return Resultado<Valoracion>.Error(
                $"El usuario {idUsuarioValora} no es el {rolEmisor} de la compraventa {idCompraventa}");
        }

        var valoracion = new Valoracion
        {
            IdCompraventa = idCompraventa,
            IdUsuarioValora = idUsuarioValora,
            IdUsuarioValorado = idUsuarioValorado,
            RolUsuarioValorado = rolValorado,
            Puntuacion = puntuacion,
            Comentario = comentario
        };

        await _repositorio.AddAsync(valoracion);

        var evento = new ValoracionCreada
        {
            IdValoracion = valoracion.Id,
            IdCompraventa = valoracion.IdCompraventa,
            IdUsuarioValora = valoracion.IdUsuarioValora,
            IdUsuarioValorado = valoracion.IdUsuarioValorado,
            RolUsuarioValorado = valoracion.RolUsuarioValorado.ToString(),
            Puntuacion = valoracion.Puntuacion
        };
        await _publicador.PublicarAsync(evento);

        return Resultado<Valoracion>.Ok(valoracion);
    }

    public Task<Valoracion?> GetByIdAsync(long id) => _repositorio.GetByIdAsync(id);

    public Task<List<Valoracion>> GetValoracionesUsuarioComoVendedorAsync(string idUsuario)
        => _repositorio.GetByUsuarioValoradoYRolAsync(idUsuario, RolValorado.Vendedor);

    public Task<List<Valoracion>> GetValoracionesUsuarioComoCompradorAsync(string idUsuario)
        => _repositorio.GetByUsuarioValoradoYRolAsync(idUsuario, RolValorado.Comprador);
}
