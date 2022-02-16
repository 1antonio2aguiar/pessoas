package br.com.codiub.pessoas.repository.contatos;

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

import br.com.codiub.pessoas.entity.Contatos;
import br.com.codiub.pessoas.entity.Contatos_;
import br.com.codiub.pessoas.entity.Pessoas_;
import br.com.codiub.pessoas.filter.ContatosFilter;


public class ContatosRepositoryImpl implements ContatosRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<Contatos> filtrar(ContatosFilter contatosFilter, Pageable pageable) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Contatos> criteria = builder.createQuery(Contatos.class);
	    Root<Contatos> root = criteria.from(Contatos.class);
	
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
	
	    Predicate[] predicates = criarRestricoes(contatosFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);
	
	    TypedQuery<Contatos> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);
	
	    return new PageImpl<>(query.getResultList(), pageable, total(contatosFilter));
	}	
	
	@SuppressWarnings("unchecked")
	private Predicate[] criarRestricoes(
			ContatosFilter contatosFilter, CriteriaBuilder builder, Root<Contatos> root) {	
		
		List<Predicate> predicates = new ArrayList<>();
		
		// CONTATO ID
		if(contatosFilter.getId() != null) {
			predicates.add(builder.equal(root.get(Contatos_.ID), contatosFilter.getId()));
		}
		
		// PESSOA
		if(contatosFilter.getPessoasFilter().getId() != null) {
			predicates.add(builder.equal(root.get(Contatos_.PESSOAS).get(Pessoas_.ID),contatosFilter.getPessoasFilter().getId()));
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
	
	private Long total(ContatosFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<Contatos> root = criteria.from(Contatos.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);

	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}

}
