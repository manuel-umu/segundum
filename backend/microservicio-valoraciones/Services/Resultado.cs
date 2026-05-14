namespace MicroservicioValoraciones.Services;

public record Resultado<T>(bool Success, T? Value, string? Message)
{
    public static Resultado<T> Ok(T value) => new(true, value, null);
    public static Resultado<T> Error(string msg) => new(false, default, msg);
    public static Resultado<T> NotFound(string msg) => new(false, default, msg);
    public static Resultado<T> Conflict(string msg) => new(false, default, msg);
}
