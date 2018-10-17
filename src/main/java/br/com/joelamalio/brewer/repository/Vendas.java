package br.com.joelamalio.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.joelamalio.brewer.model.Venda;

@Repository
public interface Vendas extends JpaRepository<Venda, Long> {
	
}
