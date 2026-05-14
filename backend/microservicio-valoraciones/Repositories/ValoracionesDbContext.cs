using Microsoft.EntityFrameworkCore;
using MicroservicioValoraciones.Models;

namespace MicroservicioValoraciones.Repositories;

public class ValoracionesDbContext : DbContext
{
    public ValoracionesDbContext(DbContextOptions<ValoracionesDbContext> options) : base(options)
    {
    }

    public DbSet<Valoracion> Valoraciones => Set<Valoracion>();

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Valoracion>(entity =>
        {
            entity.Property(v => v.RolUsuarioValorado)
                  .HasConversion<string>()
                  .HasMaxLength(16);

            entity.HasIndex(v => new { v.IdCompraventa, v.RolUsuarioValorado })
                  .IsUnique();

            entity.HasIndex(v => new { v.IdUsuarioValorado, v.RolUsuarioValorado });
        });
    }
}
