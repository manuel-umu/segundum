package usuarios.puertos;

import usuarios.eventos.Evento;

public interface IPublicadorEventos {
	void publicarEvento(Evento evento) throws Exception;
}
