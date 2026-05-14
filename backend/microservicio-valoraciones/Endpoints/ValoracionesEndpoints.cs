using MicroservicioValoraciones.DTOs;
using MicroservicioValoraciones.Models;
using MicroservicioValoraciones.Services;

namespace MicroservicioValoraciones.Endpoints;

public static class ValoracionesEndpoints
{
    public static void MapValoracionesEndpoints(this WebApplication app)
    {
        var group = app.MapGroup("/api/valoraciones");

        group.MapGet("/{id:long}", GetById).WithName("GetValoracion");
        group.MapPost("/vendedor", RegistrarVendedor);
        group.MapPost("/comprador", RegistrarComprador);
        group.MapGet("/usuarios/{idUsuario}/vendedor", GetComoVendedor);
        group.MapGet("/usuarios/{idUsuario}/comprador", GetComoComprador);
    }

    private static async Task<IResult> GetById(long id, IServicioValoraciones servicio)
    {
        var valoracion = await servicio.GetByIdAsync(id);
        if (valoracion is null)
            return Results.NotFound();
        return Results.Ok(Valoracion.toDto(valoracion));
    }

    private static async Task<IResult> RegistrarVendedor(
        RegistrarValoracionDTO dto,
        IServicioValoraciones servicio)
    {
        var resultado = await servicio.RegistrarValoracionVendedorAsync(
            dto.IdCompraventa, dto.IdUsuarioValora, dto.Puntuacion, dto.Comentario);

        if (!resultado.Success)
            return Results.BadRequest(resultado.Message);

        var dtoSalida = Valoracion.toDto(resultado.Value!);
        return Results.CreatedAtRoute("GetValoracion", new { id = dtoSalida.Id }, dtoSalida);
    }

    private static async Task<IResult> RegistrarComprador(
        RegistrarValoracionDTO dto,
        IServicioValoraciones servicio)
    {
        var resultado = await servicio.RegistrarValoracionCompradorAsync(
            dto.IdCompraventa, dto.IdUsuarioValora, dto.Puntuacion, dto.Comentario);

        if (!resultado.Success)
            return Results.BadRequest(resultado.Message);

        var dtoSalida = Valoracion.toDto(resultado.Value!);
        return Results.CreatedAtRoute("GetValoracion", new { id = dtoSalida.Id }, dtoSalida);
    }

    private static async Task<IResult> GetComoVendedor(
        string idUsuario,
        IServicioValoraciones servicio,
        HttpContext context)
    {
        var lista = await servicio.GetValoracionesUsuarioComoVendedorAsync(idUsuario);
        return Results.Ok(ConstruirLista(lista, context));
    }

    private static async Task<IResult> GetComoComprador(
        string idUsuario,
        IServicioValoraciones servicio,
        HttpContext context)
    {
        var lista = await servicio.GetValoracionesUsuarioComoCompradorAsync(idUsuario);
        return Results.Ok(ConstruirLista(lista, context));
    }

    private static ListaValoracionesDTO ConstruirLista(List<Valoracion> valoraciones, HttpContext context)
    {
        var baseUrl = $"{context.Request.Scheme}://{context.Request.Host.Value}";
        var items = valoraciones.Select(v => ValoracionResumenDTO.From(v, baseUrl)).ToList();
        return new ListaValoracionesDTO(items.Count, items);
    }
}
