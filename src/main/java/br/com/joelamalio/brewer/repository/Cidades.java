package br.com.joelamalio.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.joelamalio.brewer.model.Cidade;
import br.com.joelamalio.brewer.model.Estado;
import br.com.joelamalio.brewer.repository.helper.cidade.CidadesQueries;

@Repository
public interface Cidades extends JpaRepository<Cidade, Long>, CidadesQueries {

	public List<Cidade> findByEstadoCodigo(Long codigoEstado);
	
	public Optional<Cidade> findByEstadoAndNomeIgnoreCase(Estado estado, String nome);
	
}
