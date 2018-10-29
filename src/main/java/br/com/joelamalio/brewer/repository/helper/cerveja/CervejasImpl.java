package br.com.joelamalio.brewer.repository.helper.cerveja;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.joelamalio.brewer.dto.CervejaDTO;
import br.com.joelamalio.brewer.dto.EstoqueDTO;
import br.com.joelamalio.brewer.model.Cerveja;
import br.com.joelamalio.brewer.repository.filter.CervejaFilter;
import br.com.joelamalio.brewer.repository.paginacao.PaginacaoUtil;
import br.com.joelamalio.brewer.storage.FotoStorage;

public class CervejasImpl implements CervejasQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil; 

	@Autowired
	private FotoStorage fotoStorage; 
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filter, criteria);

		return new PageImpl<Cerveja>(criteria.list(), pageable, total(filter));
	}
	
	@Override
	public List<CervejaDTO> porSkuOuNome(String skuOuNome) {
		StringBuilder jpql = new StringBuilder();
		jpql.append(" SELECT new br.com.joelamalio.brewer.dto.CervejaDTO(codigo, sku, nome, origem, valor, foto) ");
		jpql.append(" FROM Cerveja ");
		jpql.append(" WHERE lower(sku) LIKE lower(:skuOuNome) or lower(nome) like lower(:skuOuNome) ");
		List<CervejaDTO> cervejasFiltradas = manager.createQuery(jpql.toString(), CervejaDTO.class)
				.setParameter("skuOuNome", skuOuNome.concat("%")) 
				.getResultList();
		
		cervejasFiltradas.forEach(c -> c.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + c.getFoto())));
		
		return cervejasFiltradas;
	}
	
	@Override
	public EstoqueDTO obterEstoque() {
		StringBuilder jpql = new StringBuilder();
		jpql.append(" SELECT new br.com.joelamalio.brewer.dto.EstoqueDTO(sum(valor * quantidadeEstoque), sum(quantidadeEstoque)) ");
		jpql.append(" FROM Cerveja ");
		return (EstoqueDTO) manager.createQuery(jpql.toString(), EstoqueDTO.class).getSingleResult();
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
