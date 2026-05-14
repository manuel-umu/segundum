namespace MicroservicioValoraciones.Eventos;

public abstract class Evento
{
    public Guid Id { get; init; } = Guid.NewGuid();
    public DateTime OccurredAt { get; init; } = DateTime.UtcNow;
    public abstract string RoutingKey { get; }
}
