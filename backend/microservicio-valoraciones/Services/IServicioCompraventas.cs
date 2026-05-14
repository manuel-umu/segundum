namespace MicroservicioValoraciones.Services;

public record CompraventaInfo(string Id, string IdComprador, string IdVendedor);

public interface IServicioCompraventas
{
    Task<CompraventaInfo?> GetByIdAsync(string idCompraventa);
}
