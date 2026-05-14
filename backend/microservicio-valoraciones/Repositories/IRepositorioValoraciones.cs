namespace MicroservicioValoraciones.Models;

public interface IRepositorioValoraciones
{
    Task<long> AddAsync(Valoracion valoracion);

    Task<Valoracion?> GetByIdAsync(long id);

    Task<bool> ExisteValoracionAsync(string idCompraventa, RolValorado rol);

    Task<List<Valoracion>> GetByUsuarioValoradoYRolAsync(string idUsuarioValorado, RolValorado rol);
}
