package br.com.joelamalio.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.joelamalio.brewer.model.Cliente;

@Repository
public interface Clientes extends JpaRepository<Cliente, Long> {

	public Optional<Cliente> findByCpfOuCnpjIgnoreCase(String cpfOuCnpj);
	
}
