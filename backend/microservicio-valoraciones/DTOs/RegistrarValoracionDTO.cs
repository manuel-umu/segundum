namespace MicroservicioValoraciones.DTOs;

public record RegistrarValoracionDTO(
    string IdCompraventa,
    string IdUsuarioValora,
    double Puntuacion,
    string? Comentario);
