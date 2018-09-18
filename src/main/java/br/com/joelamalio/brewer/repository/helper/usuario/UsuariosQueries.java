package br.com.joelamalio.brewer.repository.helper.usuario;

import java.util.Optional;

import br.com.joelamalio.brewer.model.Usuario;

public interface UsuariosQueries {
	
	public Optional<Usuario> porEmailEAtivo(String email);

}
