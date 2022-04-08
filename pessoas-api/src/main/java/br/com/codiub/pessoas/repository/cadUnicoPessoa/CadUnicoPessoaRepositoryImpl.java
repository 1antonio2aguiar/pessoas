package br.com.codiub.pessoas.repository.cadUnicoPessoa;

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

import br.com.codiub.pessoas.entity.CadUnicoPessoa;
import br.com.codiub.pessoas.entity.CadUnicoPessoa_;
import br.com.codiub.pessoas.entity.PessoasOrigem_;
import br.com.codiub.pessoas.filter.CadUnicoPessoaFilter;


public class CadUnicoPessoaRepositoryImpl implements CadUnicoPessoaRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<CadUnicoPessoa> filtrar(CadUnicoPessoaFilter cadUnicoPessoaFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<CadUnicoPessoa> criteria = builder.createQuery(CadUnicoPessoa.class);
	    Root<CadUnicoPessoa> root = criteria.from(CadUnicoPessoa.class);
	    
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

	    Predicate[] predicates = criarRestricoes(cadUnicoPessoaFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);


	    TypedQuery<CadUnicoPessoa> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);

	    return new PageImpl<>(query.getResultList(), pageable, total(cadUnicoPessoaFilter));
		
	}
	
	private Predicate[] criarRestricoes(
			CadUnicoPessoaFilter cadUnicoPessoaFilter, CriteriaBuilder builder, Root<CadUnicoPessoa> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// ID
		if (cadUnicoPessoaFilter.getId() != null) {
		  predicates.add(builder.equal(root.get(CadUnicoPessoa_.ID), cadUnicoPessoaFilter.getId()));
		}
		
		// CODIGO DE ORIGEM (é o codigo da pessoa la na base antiga)
		if (cadUnicoPessoaFilter.getCdOrigem() != null) {
		  predicates.add(builder.equal(root.get(CadUnicoPessoa_.PESSOAS_ORIGEM).get(PessoasOrigem_.PESSOA), cadUnicoPessoaFilter.getCdOrigem()));
		}
		
		// NOVO CODIGO DA PESSOA
		if (cadUnicoPessoaFilter.getPessoasCdUnico() != null) {
		  predicates.add(builder.equal(root.get(CadUnicoPessoa_.PESSOAS_CD_UNICO), cadUnicoPessoaFilter.getPessoasCdUnico()));
		}
	    
		// NOME
	    if (StringUtils.hasLength(cadUnicoPessoaFilter.getNome())) {
	      predicates.add(
	          builder.like(
	              builder.lower(
	            	root.get(CadUnicoPessoa_.NOME)),
	              		"%" + cadUnicoPessoaFilter.getNome().toLowerCase() + "%"));
	    }
	    
	    // DATA_REGISTRO - MESMO QUE DATA NASCIMENTO
	    if (cadUnicoPessoaFilter.getDataNascimento() != null) {
			Date data = cadUnicoPessoaFilter.getDataNascimento();
			//remove time portion from specified date: now dd/mm/yy 00:00
		    Date startDate = DateUtils.truncate(data, Calendar.DATE);
		    //new date with time initialized to 23:59:59
		    Date endDate = DateUtils.addSeconds(DateUtils.addDays(startDate, 1), - 1);
		    System.err.println("DATA  : " + data + " start " + startDate + " end " + endDate);
			predicates.add(
		    		builder.between(root.get(CadUnicoPessoa_.DATA_NASCIMENTO), startDate, endDate));
				
			// Isso aqui e para exemplos futuros	    	
			//	    	predicates.add(
			//					builder.greaterThanOrEqualTo(root.get(PessoasGeral_.DATA_REGISTRO), dataInicial));
			//			
			//			predicates.add(
			//					builder.lessThanOrEqualTo(root.get(PessoasGeral_.DATA_REGISTRO), dataFinal));
	    }
	    
	    // CPFCNPJ
	    if (StringUtils.hasLength(cadUnicoPessoaFilter.getCpfCnpj())) {
		      predicates.add(
		          builder.like(
		              builder.lower(
		            	root.get(CadUnicoPessoa_.CPF_CNPJ)),
		              		"%" + cadUnicoPessoaFilter.getCpfCnpj().toLowerCase() + "%"));
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

	  private Long total(CadUnicoPessoaFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<CadUnicoPessoa> root = criteria.from(CadUnicoPessoa.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);

	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	  }

}
