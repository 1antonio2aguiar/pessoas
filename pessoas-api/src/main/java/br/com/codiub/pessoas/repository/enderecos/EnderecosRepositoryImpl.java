package br.com.codiub.pessoas.repository.enderecos;

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

import br.com.codiub.pessoas.entity.Enderecos;
import br.com.codiub.pessoas.entity.Enderecos_;
import br.com.codiub.pessoas.entity.Pessoas_;
import br.com.codiub.pessoas.filter.EnderecosFilter;

public class EnderecosRepositoryImpl implements EnderecosRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<Enderecos> filtrar(EnderecosFilter enderecosFilter, Pageable pageable) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Enderecos> criteria = builder.createQuery(Enderecos.class);
	    Root<Enderecos> root = criteria.from(Enderecos.class);
	
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
	
	    Predicate[] predicates = criarRestricoes(enderecosFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);
	
	    TypedQuery<Enderecos> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);
	
	    return new PageImpl<>(query.getResultList(), pageable, total(enderecosFilter));
	}
	
	@SuppressWarnings("unchecked")
	private Predicate[] criarRestricoes(
			EnderecosFilter enderecosFilter, CriteriaBuilder builder, Root<Enderecos> root) {	
		
		List<Predicate> predicates = new ArrayList<>();
		
		// ENDERECO ID
		if(enderecosFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Enderecos_.ID), enderecosFilter.getId()));
		}
		
		// PESSOA
		if(enderecosFilter.getPessoasFilter().getId() != null) {
			predicates.add(builder.equal(root.get(Enderecos_.PESSOAS).get(Pessoas_.ID),enderecosFilter.getPessoasFilter().getId()));
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
	
	private Long total(EnderecosFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<Enderecos> root = criteria.from(Enderecos.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);

	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}	

}
