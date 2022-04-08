package br.com.codiub.pessoas.repository.cepsOrigem;

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

import br.com.codiub.pessoas.entity.CepsOrigem;
import br.com.codiub.pessoas.entity.CepsOrigem_;
import br.com.codiub.pessoas.filter.CepsOrigemFilter;

public class CepsOrigemRepositoryImpl implements CepsOrigemRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<CepsOrigem> filtrar(CepsOrigemFilter cepsOrigemFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<CepsOrigem> criteria = builder.createQuery(CepsOrigem.class);
	    Root<CepsOrigem> root = criteria.from(CepsOrigem.class);
	    
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
	    
	    Predicate[] predicates = criarRestricoes(cepsOrigemFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);

	    //System.err.println("OLHA ONDE ELE VÊIO !! NENEM " );
	    
	    TypedQuery<CepsOrigem> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);

	    return new PageImpl<>(query.getResultList(), pageable, total(cepsOrigemFilter));
		
	}
	
	private Predicate[] criarRestricoes(
		CepsOrigemFilter cepsOrigemFilter, CriteriaBuilder builder, Root<CepsOrigem> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// CEP
		if (cepsOrigemFilter.getCep() != null) {
		  predicates.add(builder.equal(root.get(CepsOrigem_.CEP), cepsOrigemFilter.getCep()));
		}
		
		//System.err.println("OLHA ONDE ELE VÊIO !! " + "");
		
		return predicates.toArray(new Predicate[predicates.size()]);
	    
	}
	
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
	    int paginaAtual = pageable.getPageNumber();
	    int totalRegistrosPorPagina = pageable.getPageSize();
	    int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
	
	    query.setFirstResult(primeiroRegistroDaPagina);
	    query.setMaxResults(totalRegistrosPorPagina);
	}

	private Long total(CepsOrigemFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<CepsOrigem> root = criteria.from(CepsOrigem.class);
	
	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);
	
	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}
}



