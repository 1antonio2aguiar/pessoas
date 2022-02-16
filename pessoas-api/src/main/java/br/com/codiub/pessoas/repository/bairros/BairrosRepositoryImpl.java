package br.com.codiub.pessoas.repository.bairros;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.StringUtils;

import br.com.codiub.pessoas.entity.Bairros;
import br.com.codiub.pessoas.entity.Bairros_;
import br.com.codiub.pessoas.filter.BairrosFilter;


public class BairrosRepositoryImpl implements BairrosRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<Bairros> filtrar(BairrosFilter bairrosFilter, Pageable pageable) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Bairros> criteria = builder.createQuery(Bairros.class);
	    Root<Bairros> root = criteria.from(Bairros.class);
	
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
	
	    Predicate[] predicates = criarRestricoes(bairrosFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);
	
	    TypedQuery<Bairros> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);
	
	    return new PageImpl<>(query.getResultList(), pageable, total(bairrosFilter));
	}
	
	@SuppressWarnings("unchecked")
	private Predicate[] criarRestricoes(
			BairrosFilter bairrosFilter, CriteriaBuilder builder, Root<Bairros> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// BAIRRO ID
		if(bairrosFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Bairros_.ID), bairrosFilter.getId()));
		}
		
		// BAIRRO NOME
	    if (StringUtils.hasLength(bairrosFilter.getNome())) {
	    	
	    	predicates.add(
	    		builder.like(
		    		builder.lower(root.get(Bairros_.NOME)), 
		    		"%" + bairrosFilter.getNome() + "%")) ;
	    }
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
	    int paginaAtual = pageable.getPageNumber();
	    int totalRegistrosPorPagina = pageable.getPageSize();
	    int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
	
	    query.setFirstResult(primeiroRegistroDaPagina);
	    query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(BairrosFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<Bairros> root = criteria.from(Bairros.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);

	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}

}
