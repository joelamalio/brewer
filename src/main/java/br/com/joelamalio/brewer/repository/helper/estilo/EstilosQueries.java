package br.com.joelamalio.brewer.repository.helper.estilo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.joelamalio.brewer.model.Estilo;
import br.com.joelamalio.brewer.repository.filter.EstiloFilter;

public interface EstilosQueries {

	public Page<Estilo> filtrar(EstiloFilter filter, Pageable pageable);
	
}
