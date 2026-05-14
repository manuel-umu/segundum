using Microsoft.EntityFrameworkCore;
using MicroservicioValoraciones.Models;

namespace MicroservicioValoraciones.Repositories;

public class RepositorioValoracionesEF : IRepositorioValoraciones
{
    private readonly ValoracionesDbContext _context;

    public RepositorioValoracionesEF(ValoracionesDbContext context)
    {
        _context = context;
    }

    public async Task<long> AddAsync(Valoracion valoracion)
    {
        _context.Valoraciones.Add(valoracion);
        await _context.SaveChangesAsync();
        return valoracion.Id;
    }

    public async Task<Valoracion?> GetByIdAsync(long id)
    {
        return await _context.Valoraciones.FindAsync(id);
    }

    public async Task<bool> ExisteValoracionAsync(string idCompraventa, RolValorado rol)
    {
        return await _context.Valoraciones
            .AnyAsync(v => v.IdCompraventa == idCompraventa && v.RolUsuarioValorado == rol);
    }

    public async Task<List<Valoracion>> GetByUsuarioValoradoYRolAsync(string idUsuarioValorado, RolValorado rol)
    {
        return await _context.Valoraciones
            .Where(v => v.IdUsuarioValorado == idUsuarioValorado && v.RolUsuarioValorado == rol)
            .ToListAsync();
    }
}
