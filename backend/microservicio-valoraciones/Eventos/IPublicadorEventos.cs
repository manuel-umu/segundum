namespace MicroservicioValoraciones.Eventos;

public interface IPublicadorEventos
{
    Task PublicarAsync(Evento evento);
}
