namespace MicroservicioValoraciones.DTOs;

public record RegistrarValoracionDTO(
    string IdCompraventa,
    string IdUsuarioValora,
    int Puntuacion,
    string? Comentario);
