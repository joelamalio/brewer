package br.com.joelamalio.brewer.repository.helper.cerveja;

import java.util.List;

import br.com.joelamalio.brewer.model.Cerveja;
import br.com.joelamalio.brewer.repository.filter.CervejaFilter;

public interface CervejasQueries {

	public List<Cerveja> filtrar(CervejaFilter filter);
	
}
