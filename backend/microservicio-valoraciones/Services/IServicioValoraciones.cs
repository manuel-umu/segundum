using MicroservicioValoraciones.Models;

namespace MicroservicioValoraciones.Services;

public interface IServicioValoraciones
{
    Task<Resultado<Valoracion>> RegistrarValoracionVendedorAsync(
        string idCompraventa,
        string idComprador,
        int puntuacion,
        string? comentario);

    Task<Resultado<Valoracion>> RegistrarValoracionCompradorAsync(
        string idCompraventa,
        string idVendedor,
        int puntuacion,
        string? comentario);

    Task<Valoracion?> GetByIdAsync(long id);

    Task<List<Valoracion>> GetValoracionesUsuarioComoVendedorAsync(string idUsuario);

    Task<List<Valoracion>> GetValoracionesUsuarioComoCompradorAsync(string idUsuario);
}
