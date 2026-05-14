using System.Text.Json.Serialization;

namespace MicroservicioValoraciones.Eventos;

public abstract class Evento
{
    [JsonIgnore]
    public Guid IdEvento { get; init; } = Guid.NewGuid();

    [JsonIgnore]
    public DateTime OccurredAt { get; init; } = DateTime.UtcNow;

    [JsonIgnore]
    public abstract string RoutingKey { get; }
}
