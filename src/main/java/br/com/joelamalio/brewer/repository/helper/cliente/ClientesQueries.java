package br.com.joelamalio.brewer.repository.helper.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.joelamalio.brewer.model.Cliente;
import br.com.joelamalio.brewer.repository.filter.ClienteFilter;

public interface ClientesQueries {

	public Page<Cliente> filtrar(ClienteFilter filter, Pageable pageable);
	
	public Cliente buscarCompleto(Long codigo);
	
}
