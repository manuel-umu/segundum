package repositorios.especificos;
import java.util.List;
import modelo.Categoria;
import repositorios.EntidadNoEncontrada;
import repositorios.RepositorioException;
import repositorios.RepositorioString;

public interface RepositorioCategoriasAdHoc extends RepositorioString<Categoria> {

	public List<Categoria> getBySubcategorias(String id) throws RepositorioException, EntidadNoEncontrada;

}
