package compraventas.puertos;

import compraventas.eventos.Evento;

public interface PublicadorEventos {

	void publicarEvento(Evento evento);
	
}
