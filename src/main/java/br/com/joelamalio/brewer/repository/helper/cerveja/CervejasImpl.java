package br.com.joelamalio.brewer.repository.helper.cerveja;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.joelamalio.brewer.model.Cerveja;
import br.com.joelamalio.brewer.repository.filter.CervejaFilter;

public class CervejasImpl implements CervejasQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);

		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistros = paginaAtual * totalRegistrosPorPagina;
		
		criteria.setFirstResult(primeiroRegistros);
		criteria.setMaxResults(totalRegistrosPorPagina);
		
		return new PageImpl<Cerveja>(criteria.list(), pageable, total(filter));
	}

	private Long total(CervejaFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(CervejaFilter filter, Criteria criteria) {
		if (filter != null) {
			if (!StringUtils.isEmpty(filter.getSku())) {
				criteria.add(Restrictions.eq("sku", filter.getSku()));
			}
			
			if (!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			}
			
			if (isEstiloPresente(filter)) {
				criteria.add(Restrictions.eq("estilo", filter.getEstilo()));
			}
			
			if (filter.getSabor() != null) {
				criteria.add(Restrictions.eq("sabor", filter.getSabor()));
			}
			
			if (filter.getOrigem() != null) {
				criteria.add(Restrictions.eq("origem", filter.getOrigem()));
			}
			
			if (filter.getValorDe() != null) {
				criteria.add(Restrictions.ge("valor", filter.getValorDe()));
			}
			
			if (filter.getValorAte() != null) {
				criteria.add(Restrictions.le("valor", filter.getValorAte()));
			}
 		}
	}

	private boolean isEstiloPresente(CervejaFilter filter) {
		return filter.getEstilo() != null && filter.getEstilo().getCodigo() != null;
	}

}
