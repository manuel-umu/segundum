using System.Net;
using System.Net.Http.Json;
using MicroservicioValoraciones.Services;

namespace MicroservicioValoraciones.Infrastructure.Http;

public class ServicioCompraventasHttp : IServicioCompraventas
{
    private readonly HttpClient _httpClient;

    public ServicioCompraventasHttp(HttpClient httpClient)
    {
        _httpClient = httpClient;
    }

    public async Task<CompraventaInfo?> GetByIdAsync(string idCompraventa)
    {
        var response = await _httpClient.GetAsync(Uri.EscapeDataString(idCompraventa));

        if (response.StatusCode == HttpStatusCode.NotFound)
            return null;

        response.EnsureSuccessStatusCode();

        var dto = await response.Content.ReadFromJsonAsync<CompraventaDto>();
        if (dto is null)
            return null;

        return new CompraventaInfo(
            dto.Id ?? idCompraventa,
            dto.IdComprador ?? string.Empty,
            dto.IdVendedor ?? string.Empty);
    }

    private sealed class CompraventaDto
    {
        public string? Id { get; set; }
        public string? IdComprador { get; set; }
        public string? IdVendedor { get; set; }
    }
}
