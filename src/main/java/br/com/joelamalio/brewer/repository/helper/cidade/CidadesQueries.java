package br.com.joelamalio.brewer.repository.helper.cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.joelamalio.brewer.model.Cidade;
import br.com.joelamalio.brewer.repository.filter.CidadeFilter;

public interface CidadesQueries {

	public Page<Cidade> filtrar(CidadeFilter filter, Pageable pageable);
	
}
