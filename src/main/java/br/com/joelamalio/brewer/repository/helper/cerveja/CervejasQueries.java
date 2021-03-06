package br.com.joelamalio.brewer.repository.helper.cerveja;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.joelamalio.brewer.dto.CervejaDTO;
import br.com.joelamalio.brewer.dto.EstoqueDTO;
import br.com.joelamalio.brewer.model.Cerveja;
import br.com.joelamalio.brewer.repository.filter.CervejaFilter;

public interface CervejasQueries {

	public Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable);
	
	public List<CervejaDTO> porSkuOuNome(String skuOuNome);
	
	public EstoqueDTO obterEstoque();
	
}
