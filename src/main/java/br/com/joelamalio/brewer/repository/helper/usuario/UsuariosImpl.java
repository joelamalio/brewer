package br.com.joelamalio.brewer.repository.helper.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.joelamalio.brewer.model.Grupo;
import br.com.joelamalio.brewer.model.Usuario;
import br.com.joelamalio.brewer.model.UsuarioGrupo;
import br.com.joelamalio.brewer.repository.filter.UsuarioFilter;
import br.com.joelamalio.brewer.repository.paginacao.PaginacaoUtil;

public class UsuariosImpl implements UsuariosQueries {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil; 
	
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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable) {
		Criteria criteria = em.unwrap(Session.class).createCriteria(Usuario.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filter, criteria);
		
		List<Usuario> filtrados = criteria.list();
		filtrados.forEach(u -> Hibernate.initialize(u.getGrupos()));

		return new PageImpl<Usuario>(filtrados, pageable, total(filter));
	}
	
	@Transactional(readOnly = true)
	@Override
	public Usuario buscarComGrupos(Long codigo) {
		Criteria criteria = em.unwrap(Session.class).createCriteria(Usuario.class);
		criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Usuario) criteria.uniqueResult();
	}
	
	private Long total(UsuarioFilter filter) {
		Criteria criteria = em.unwrap(Session.class).createCriteria(Usuario.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(UsuarioFilter filter, Criteria criteria) {
		if (filter != null) {
			if (!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			}

			if (!StringUtils.isEmpty(filter.getEmail())) {
				criteria.add(Restrictions.ilike("email", filter.getEmail(), MatchMode.START));
			}
			
			if (filter.getGrupos() != null && !filter.getGrupos().isEmpty()) {
				List<Criterion> subqueries = new ArrayList<Criterion>();
				for (Long codigoGrupo : filter.getGrupos().stream().mapToLong(Grupo::getCodigo).toArray()) {
					DetachedCriteria dc = DetachedCriteria.forClass(UsuarioGrupo.class);
					dc.add(Restrictions.eq("id.grupo.codigo", codigoGrupo));
					dc.setProjection(Projections.property("id.usuario"));
					
					subqueries.add(Subqueries.propertyIn("codigo", dc));
				}
				
				Criterion[] criterions = new Criterion[subqueries.size()];
				criteria.add(Restrictions.and(subqueries.toArray(criterions)));
			}
			
			
 		}
	}

}
