package br.com.codiub.pessoas.repository.empresasPessoas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.StringUtils;

import br.com.codiub.pessoas.entity.EmpresasPessoas;
import br.com.codiub.pessoas.entity.EmpresasPessoas_;
import br.com.codiub.pessoas.filter.EmpresasPessoasFilter;

public class EmpresasPessoasRepositoryImpl implements EmpresasPessoasRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<EmpresasPessoas> filtrar(EmpresasPessoasFilter empresasPessoasFilter, Pageable pageable) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<EmpresasPessoas> criteria = builder.createQuery(EmpresasPessoas.class);
	    Root<EmpresasPessoas> root = criteria.from(EmpresasPessoas.class);
	
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
	
	    Predicate[] predicates = criarRestricoes(empresasPessoasFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);
	
	    TypedQuery<EmpresasPessoas> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);
	
	    return new PageImpl<>(query.getResultList(), pageable, total(empresasPessoasFilter));
	}
	
	private Predicate[] criarRestricoes(
			EmpresasPessoasFilter empresasPessoasFilter, CriteriaBuilder builder, Root<EmpresasPessoas> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// ID
	    if (empresasPessoasFilter.getId() != null) {
	      predicates.add(builder.equal(root.get(EmpresasPessoas_.ID), empresasPessoasFilter.getId()));
	    }
	    
	    // NOME DA EMPRESA
	    /*if (StringUtils.hasLength(empresasPessoasFilter.getPessoasGeralFilter().getNome())) {
	      predicates.add(
	          builder.like(
	              builder.lower(
	            	root.get(EmpresasPessoas_.PESSOAS_EMPRESA)),
	              		"%" + empresasPessoasFilter.getPessoasGeralFilter().getNome().toLowerCase() + "%"));
	    }
	    
	    // NOME DA PESSOA
	    if (StringUtils.hasLength(empresasPessoasFilter.getPessoasGeralFilter().getNome())) {
	      predicates.add(
	          builder.like(
	              builder.lower(
	            	root.get(EmpresasPessoas_.PESSOAS_PESSOA)),
	              		"%" + empresasPessoasFilter.getPessoasGeralFilter().getNome().toLowerCase() + "%"));
	    }*/
	    
	    return predicates.toArray(new Predicate[predicates.size()]);
		
	}
	
	private Long total(EmpresasPessoasFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<EmpresasPessoas> root = criteria.from(EmpresasPessoas.class);
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

}
