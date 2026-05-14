using Microsoft.EntityFrameworkCore;
using MicroservicioValoraciones.Endpoints;
using MicroservicioValoraciones.Eventos;
using MicroservicioValoraciones.Infrastructure;
using MicroservicioValoraciones.Infrastructure.Http;
using MicroservicioValoraciones.Models;
using MicroservicioValoraciones.Repositories;
using MicroservicioValoraciones.Services;
using RabbitMQ.Client;

var builder = WebApplication.CreateBuilder(args);

// EF Core + MySQL
var connectionString = builder.Configuration.GetConnectionString("MySql")!;
var mysqlVersion = new MySqlServerVersion(new Version(8, 0, 36));
builder.Services.AddDbContext<ValoracionesDbContext>(options =>
    options.UseMySql(connectionString, mysqlVersion));

// Repositorio y servicio de aplicación
builder.Services.AddScoped<IRepositorioValoraciones, RepositorioValoracionesEF>();
builder.Services.AddScoped<IServicioValoraciones, ServicioValoraciones>();

// Cliente HTTP del microservicio Compraventas
builder.Services.AddHttpClient<IServicioCompraventas, ServicioCompraventasHttp>(client =>
{
    client.BaseAddress = new Uri(builder.Configuration["Compraventas:BaseUrl"]!);
});

// RabbitMQ
var factory = new ConnectionFactory
{
    Uri = new Uri(builder.Configuration["RabbitMQ:ConnectionString"]!)
};
var connection = await factory.CreateConnectionAsync();
builder.Services.AddSingleton<IConnection>(connection);
builder.Services.AddSingleton<IPublicadorEventos, PublicadorEventosRabbitMQ>();

// OpenAPI (NSwag)
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddOpenApiDocument(config =>
{
    config.DocumentName = "MicroservicioValoraciones";
    config.Title = "Microservicio Valoraciones";
    config.Version = "v1";
});

var app = builder.Build();

using (var scope = app.Services.CreateScope())
{
    var db = scope.ServiceProvider.GetRequiredService<ValoracionesDbContext>();
    db.Database.EnsureCreated();
}

app.UseMiddleware<ExceptionMiddleware>();

if (app.Environment.IsDevelopment())
{
    app.UseOpenApi();
    app.UseSwaggerUi(config =>
    {
        config.DocumentTitle = "Valoraciones API";
        config.Path = "/swagger";
        config.DocumentPath = "/swagger/{documentName}/swagger.json";
        config.DocExpansion = "list";
    });
}

app.MapValoracionesEndpoints();

app.Run();
