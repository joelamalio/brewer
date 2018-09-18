package br.com.joelamalio.brewer.repository.helper.usuario;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.joelamalio.brewer.model.Usuario;

public class UsuariosImpl implements UsuariosQueries {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Optional<Usuario> porEmailEAtivo(String email) {
		return em.createQuery("from Usuario where email = :email and ativo = true", Usuario.class)
				  .setParameter("email", email).getResultList().stream().findFirst();
	}

}
