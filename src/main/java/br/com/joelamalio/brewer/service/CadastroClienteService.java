package br.com.joelamalio.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.joelamalio.brewer.model.Cliente;
import br.com.joelamalio.brewer.repository.Clientes;
import br.com.joelamalio.brewer.service.exception.CpfCnpjClienteJaCadastradoException;

@Service
public class CadastroClienteService {
	
	@Autowired
	private Clientes clientes;
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		Optional<Cliente> clienteExistente = clientes.findByCpfOuCnpjIgnoreCase(cliente.getCpfOuCnpjFormatacao());
		if (clienteExistente.isPresent()) {
			throw new CpfCnpjClienteJaCadastradoException("CPF/CNPJ já cadastrado");
		}
 		
		return clientes.save(cliente);
	}

}
