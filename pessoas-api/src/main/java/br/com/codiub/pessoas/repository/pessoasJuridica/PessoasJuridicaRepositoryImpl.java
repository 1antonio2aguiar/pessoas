package br.com.codiub.pessoas.repository.pessoasJuridica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.StringUtils;

import br.com.codiub.pessoas.entity.DadosPj_;
import br.com.codiub.pessoas.entity.PessoasJuridica;
import br.com.codiub.pessoas.entity.PessoasJuridica_;
import br.com.codiub.pessoas.filter.PessoasJuridicaFilter;

public class PessoasJuridicaRepositoryImpl implements PessoasJuridicaRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<PessoasJuridica> filtrar(PessoasJuridicaFilter pessoasJuridicaFilter, Pageable pageable) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<PessoasJuridica> criteria = builder.createQuery(PessoasJuridica.class);
	    Root<PessoasJuridica> root = criteria.from(PessoasJuridica.class);
    
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

	    Predicate[] predicates = criarRestricoes(pessoasJuridicaFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);

	    TypedQuery<PessoasJuridica> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);
    
	    return new PageImpl<>(query.getResultList(), pageable, total(pessoasJuridicaFilter));
	}
	
	private Predicate[] criarRestricoes(
		      PessoasJuridicaFilter pessoasJuridicaFilter, CriteriaBuilder builder, Root<PessoasJuridica> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// DATA_CADASTRO
	    if (pessoasJuridicaFilter.getDataCadastro() != null) {
	      predicates.add(
	          builder.equal(root.get(PessoasJuridica_.DATA_CADASTRO), pessoasJuridicaFilter.getDataCadastro()));
	    }
	    
	    // ID
	    if (pessoasJuridicaFilter.getId() != null) {
	      predicates.add(builder.equal(root.get(PessoasJuridica_.ID), pessoasJuridicaFilter.getId()));
	    }
	    
	    // NOME
	    if (StringUtils.hasLength(pessoasJuridicaFilter.getNome())) {
	      predicates.add(
	          builder.like(
	              builder.lower(
	            	root.get(PessoasJuridica_.NOME)),
	              		"%" + pessoasJuridicaFilter.getNome().toLowerCase() + "%"));
	    }
	    
	    //CNPJ
	    if (StringUtils.hasLength(pessoasJuridicaFilter.getDadosPjFilter().getCnpj())) {
	    	predicates.add(
	    		builder.like(
			        builder.lower(
			        	root.get(PessoasJuridica_.DADOS_PJ).get(DadosPj_.CNPJ)),
			            	"%" + pessoasJuridicaFilter.getDadosPjFilter().getCnpj() + "%"));
	    }
	    
	    //NOME FANTASIA
	    if (StringUtils.hasLength(pessoasJuridicaFilter.getDadosPjFilter().getNomeFantasia())) {
	    	predicates.add(
	  	          builder.like(
	  	              builder.lower(
	  	            	root.get(PessoasJuridica_.DADOS_PJ).get(DadosPj_.NOME_FANTASIA)),
			            	"%" + pessoasJuridicaFilter.getDadosPjFilter().getNomeFantasia().toLowerCase() + "%"));
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
	
	private Long total(PessoasJuridicaFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<PessoasJuridica> root = criteria.from(PessoasJuridica.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);
    
	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}

}
