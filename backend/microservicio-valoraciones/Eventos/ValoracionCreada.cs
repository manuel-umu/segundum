namespace MicroservicioValoraciones.Eventos;

public class ValoracionCreada : Evento
{
    public override string RoutingKey => "valoracion.creada";

    public long IdValoracion { get; set; }
    public string IdCompraventa { get; set; } = string.Empty;
    public string IdUsuarioValora { get; set; } = string.Empty;
    public string IdUsuarioValorado { get; set; } = string.Empty;
    public string RolUsuarioValorado { get; set; } = string.Empty;
    public int Puntuacion { get; set; }
}
