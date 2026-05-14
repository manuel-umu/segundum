using MicroservicioValoraciones.Models;

namespace MicroservicioValoraciones.DTOs;

public record ValoracionResumenDTO(
    long Id,
    double Puntuacion,
    string Href)
{
    public static ValoracionResumenDTO From(Valoracion v, string baseUrl) =>
        new(v.Id, v.Puntuacion, $"{baseUrl}/api/valoraciones/{v.Id}");
}

public record ListaValoracionesDTO(
    int Total,
    IReadOnlyList<ValoracionResumenDTO> Items);
