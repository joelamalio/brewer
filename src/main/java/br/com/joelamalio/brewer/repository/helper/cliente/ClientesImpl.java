package br.com.joelamalio.brewer.repository.helper.cliente;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.joelamalio.brewer.model.Cliente;
import br.com.joelamalio.brewer.repository.filter.ClienteFilter;
import br.com.joelamalio.brewer.repository.paginacao.PaginacaoUtil;

public class ClientesImpl implements ClientesQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> filtrar(ClienteFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);

		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filter, criteria);
		criteria.createAlias("endereco.cidade", "c", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("c.estado", "e", JoinType.LEFT_OUTER_JOIN);

		return new PageImpl<Cliente>(criteria.list(), pageable, total(filter));
	}
	
	@Transactional(readOnly = true)
	@Override
	public Cliente buscarCompleto(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		criteria.createAlias("endereco.cidade", "c", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("c.estado", "e", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Cliente cliente = (Cliente) criteria.uniqueResult();
		
		if (cliente.getEndereco() != null && cliente.getEndereco().getCidade() != null) {
			cliente.getEndereco().setEstado(cliente.getEndereco().getCidade().getEstado());
		}
		
		return cliente;
	}

	private Long total(ClienteFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(ClienteFilter filter, Criteria criteria) {
		if (filter != null) {
			if (!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			}

			if (!StringUtils.isEmpty(filter.getCpfOuCnpj())) {
				criteria.add(Restrictions.eq("cpfOuCnpj", filter.getCpfOuCnpj()));
			}
		}
	}

}
