using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using MicroservicioValoraciones.DTOs;
namespace MicroservicioValoraciones.Models;

[Table("valoraciones")]
public class Valoracion
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long Id { get; set; }

    [Required]
    [MaxLength(64)]
    public string IdCompraventa { get; set; } = string.Empty;

    [Required]
    [MaxLength(64)]
    public string IdUsuarioValora { get; set; } = string.Empty;

    [Required]
    [MaxLength(64)]
    public string IdUsuarioValorado { get; set; } = string.Empty;

    [Required]
    public RolValorado RolUsuarioValorado { get; set; }

    [Range(1, 5)]
    public double Puntuacion { get; set; }

    [MaxLength(1000)]
    public string? Comentario { get; set; }
    
    public static ValoracionDTO toDto(Valoracion v) => new(
        v.Id,
        v.IdCompraventa,
        v.IdUsuarioValora,
        v.IdUsuarioValorado,
        v.RolUsuarioValorado.ToString(),
        v.Puntuacion,
        v.Comentario);

}
