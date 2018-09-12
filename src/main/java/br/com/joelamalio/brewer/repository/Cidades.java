package br.com.joelamalio.brewer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.joelamalio.brewer.model.Cidade;

@Repository
public interface Cidades extends JpaRepository<Cidade, Long> {

	public List<Cidade> findByEstadoCodigo(Long codigoEstado);
	
}
