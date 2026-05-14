using System.Text;
using System.Text.Json;
using RabbitMQ.Client;

namespace MicroservicioValoraciones.Eventos;

public class PublicadorEventosRabbitMQ : IPublicadorEventos
{
    private readonly IConnection _connection;
    private readonly string _exchangeName;

    public PublicadorEventosRabbitMQ(IConnection connection, IConfiguration config)
    {
        _connection = connection;
        _exchangeName = config["RabbitMQ:Exchange"] ?? "valoraciones";
    }

    public async Task PublicarAsync(Evento evento)
    {
        await using var channel = await _connection.CreateChannelAsync();

        await channel.ExchangeDeclareAsync(
            exchange: _exchangeName,
            type: ExchangeType.Topic,
            durable: true);

        var json = JsonSerializer.Serialize(evento, evento.GetType());
        var body = Encoding.UTF8.GetBytes(json);

        var properties = new BasicProperties
        {
            ContentType = "application/json",
            DeliveryMode = DeliveryModes.Persistent
        };

        await channel.BasicPublishAsync(
            exchange: _exchangeName,
            routingKey: evento.RoutingKey,
            mandatory: false,
            basicProperties: properties,
            body: body);
    }
}
