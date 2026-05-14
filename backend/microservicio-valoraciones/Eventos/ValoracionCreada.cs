namespace MicroservicioValoraciones.Eventos;

public class ValoracionCreada : Evento
{
    public override string RoutingKey => "bus.valoraciones.creada";

    public string tipo => "valoracion-creada";
    public string id { get; set; } = string.Empty;
    public string rol { get; set; } = string.Empty;
    public int puntuacion { get; set; }
}
