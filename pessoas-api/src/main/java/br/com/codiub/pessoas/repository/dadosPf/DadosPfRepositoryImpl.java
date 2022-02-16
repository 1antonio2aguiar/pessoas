package br.com.codiub.pessoas.repository.dadosPf;

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

import br.com.codiub.pessoas.entity.DadosPf;
import br.com.codiub.pessoas.entity.DadosPf_;
import br.com.codiub.pessoas.entity.Pessoas_;
import br.com.codiub.pessoas.filter.DadosPfFilter;
import br.com.codiub.pessoas.filter.PessoasFilter;

public class DadosPfRepositoryImpl implements DadosPfRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<DadosPf> filtrar(DadosPfFilter dadosPfFilter, Pageable pageable) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<DadosPf> criteria = builder.createQuery(DadosPf.class);
	    Root<DadosPf> root = criteria.from(DadosPf.class);

	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

	    Predicate[] predicates = criarRestricoes(dadosPfFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);

	    TypedQuery<DadosPf> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);

	    return new PageImpl<>(query.getResultList(), pageable, total(dadosPfFilter));
	}
	
	private Predicate[] criarRestricoes(DadosPfFilter dadosPfFilter, CriteriaBuilder builder, Root<DadosPf> root) {
		List<Predicate> predicates = new ArrayList<>();
		
	    
	    //CPF
		if (StringUtils.hasLength(dadosPfFilter.getCpf())) {
		    predicates.add(
		        builder.like(
		            builder.lower(root.get(DadosPf_.CPF)),
		            "%" + dadosPfFilter.getCpf().toLowerCase() + "%"));
		}
	    
	    // NOME DO PAI
		if (StringUtils.hasLength(dadosPfFilter.getPai())) {
			predicates.add(
				builder.like(
					builder.lower(root.get(DadosPf_.PAI)),
					"%" + dadosPfFilter.getPai().toLowerCase() + "%"));
		}
	    
		// NOME DA MÃE
		if (StringUtils.hasLength(dadosPfFilter.getMae())) {
			predicates.add(
				builder.like(
					builder.lower(root.get(DadosPf_.MAE)),
					"%" + dadosPfFilter.getMae().toLowerCase() + "%"));
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
	
	private Long total(DadosPfFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<DadosPf> root = criteria.from(DadosPf.class);
	
	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);
	
	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}

}
