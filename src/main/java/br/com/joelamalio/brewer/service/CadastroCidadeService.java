package br.com.joelamalio.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.joelamalio.brewer.model.Cidade;
import br.com.joelamalio.brewer.repository.Cidades;
import br.com.joelamalio.brewer.service.exception.ImpossivelExcluirEntidadeException;
import br.com.joelamalio.brewer.service.exception.NomeCidadeJaCadastradoException;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private Cidades cidades;
	
	@Transactional
	public Cidade salvar(Cidade cidade) {
		Optional<Cidade> cidadeOptional = cidades.findByEstadoAndNomeIgnoreCase(cidade.getEstado(), cidade.getNome());
		if (cidadeOptional.isPresent()) {
			throw new NomeCidadeJaCadastradoException("Nome da cidade já cadastrado");
		}
 		
		return cidades.saveAndFlush(cidade);
	}
	
	@Transactional
	public void excluir(Cidade cidade) {
		try {
			cidades.delete(cidade);
			cidades.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar cidade. Já foi usada em algum relacionamento.");
		}
	}

}
