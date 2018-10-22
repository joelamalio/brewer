package br.com.joelamalio.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.joelamalio.brewer.model.Cliente;
import br.com.joelamalio.brewer.repository.Clientes;
import br.com.joelamalio.brewer.service.exception.CpfCnpjClienteJaCadastradoException;
import br.com.joelamalio.brewer.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class CadastroClienteService {
	
	@Autowired
	private Clientes clientes;
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		Optional<Cliente> clienteExistente = clientes.findByCpfOuCnpjIgnoreCase(cliente.getCpfOuCnpjFormatacao());
		if (clienteExistente.isPresent() && !clienteExistente.get().equals(cliente)) {
			throw new CpfCnpjClienteJaCadastradoException("CPF/CNPJ já cadastrado");
		}
 		
		return clientes.save(cliente);
	}

	@Transactional
	public void excluir(Cliente cliente) {
		try {
			clientes.delete(cliente);
			clientes.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar cliente, pois o registro está vinculado a outro no banco de dados.");
		}
	}

}
