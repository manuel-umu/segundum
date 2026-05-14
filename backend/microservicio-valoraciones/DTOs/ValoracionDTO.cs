using MicroservicioValoraciones.Models;

namespace MicroservicioValoraciones.DTOs;

public record ValoracionDTO(
    long Id,
    string IdCompraventa,
    string IdUsuarioValora,
    string IdUsuarioValorado,
    string RolUsuarioValorado,
    int Puntuacion,
    string? Comentario)
{
    
}
