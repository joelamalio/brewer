package br.com.joelamalio.brewer.repository.helper.usuario;

import java.util.List;
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

	@Override
	public List<String> permissoes(Usuario usuario) {
		StringBuilder query = new StringBuilder();
		query.append("select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario");
		return em.createQuery(query.toString(), String.class).setParameter("usuario", usuario).getResultList();
	}

}
