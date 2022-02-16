package br.com.codiub.pessoas.repository.PessoasGeral;

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

import br.com.codiub.pessoas.entity.DadosPfGeral;
import br.com.codiub.pessoas.entity.DadosPfGeral_;
import br.com.codiub.pessoas.entity.DadosPjGeral;
import br.com.codiub.pessoas.entity.DadosPjGeral_;
import br.com.codiub.pessoas.entity.PessoasGeral;
import br.com.codiub.pessoas.entity.PessoasGeral_;
import br.com.codiub.pessoas.entity.TiposPessoas_;
import br.com.codiub.pessoas.filter.PessoasGeralFilter;

public class PessoasGeralRepositoryImpl implements PessoasGeralRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<PessoasGeral> filtrar(PessoasGeralFilter pessoasGeralFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<PessoasGeral> criteria = builder.createQuery(PessoasGeral.class);
		Root<PessoasGeral> root = criteria.from(PessoasGeral.class);
		
		List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
		
		Predicate[] predicates = criarRestricoes(pessoasGeralFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);
	    
	    TypedQuery<PessoasGeral> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);
	    
	    return new PageImpl<>(query.getResultList(), pageable, total(pessoasGeralFilter));
	}
	
    private Predicate[] criarRestricoes(
		      PessoasGeralFilter pessoasGeralFilter, CriteriaBuilder builder, Root<PessoasGeral> root)  {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// DATA_CADASTRO
	    if (pessoasGeralFilter.getDataCadastro() != null) {
	      predicates.add(
	          builder.equal(root.get(PessoasGeral_.DATA_CADASTRO), pessoasGeralFilter.getDataCadastro()));
	    }

	    // DATA_REGISTRO - DATA NASCIMENTO
	    if (pessoasGeralFilter.getDataRegistro() != null) {
    		Date data = pessoasGeralFilter.getDataRegistro();
			//remove time portion from specified date: now dd/mm/yy 00:00
		    Date startDate = DateUtils.truncate(data, Calendar.DATE);
		    //new date with time initialized to 23:59:59
		    Date endDate = DateUtils.addSeconds(DateUtils.addDays(startDate, 1), - 1);
		    //System.err.println("DATA  : " + data + " start " + startDate + " end " + endDate);
			predicates.add(
		    		builder.between(root.get(PessoasGeral_.DATA_REGISTRO), startDate, endDate));
				
			// Isso aqui e para exemplos futuros	    	
			//	    	predicates.add(
			//					builder.greaterThanOrEqualTo(root.get(PessoasGeral_.DATA_REGISTRO), dataInicial));
			//			
			//			predicates.add(
			//					builder.lessThanOrEqualTo(root.get(PessoasGeral_.DATA_REGISTRO), dataFinal));
	    }
	    
	    // ID
	    if (pessoasGeralFilter.getId() != null) {
	      predicates.add(builder.equal(root.get(PessoasGeral_.ID), pessoasGeralFilter.getId()));
	    }
		
		// NOME
	    if (StringUtils.hasLength(pessoasGeralFilter.getNome())) {
	      predicates.add(
	          builder.like(
	              builder.lower(
	            	root.get(PessoasGeral_.NOME)),
	              		"%" + pessoasGeralFilter.getNome().toLowerCase() + "%")); 
	    }
	    
	    if (pessoasGeralFilter.getDadosPfFilter().getDataRegistro() != null)
	    
		    //NOME DA MÃE
		    //System.err.println(pessoasGeralFilter.getDadosPfFilter().getMae() );
		    if (StringUtils.hasLength(pessoasGeralFilter.getDadosPfFilter().getMae())) {
		   		predicates.add(
		   			builder.like(
						builder.lower(root.get(PessoasGeral_.DADOS_PF_GERAL).get(DadosPfGeral_.MAE)),
						"%" + pessoasGeralFilter.getDadosPfFilter().getMae().toLowerCase() + "%"));
	    }
	    
	    //CPF
  		if (StringUtils.hasLength(pessoasGeralFilter.getDadosPfFilter().getCpf())) {
  			//System.err.println("Entrou na bagaça!@$? CPF ");
  		    predicates.add(
  		        builder.like(
  		            builder.lower(root.get(PessoasGeral_.DADOS_PF_GERAL).get(DadosPfGeral_.CPF)),
  		            "%" + pessoasGeralFilter.getDadosPfFilter().getCpf() + "%"));
  		}
	    
	    //CNPJ
  		if (StringUtils.hasLength(pessoasGeralFilter.getDadosPjFilter().getCnpj())) {
  			//System.err.println("Entrou na bagaça!@$? CNPJ");
  		    predicates.add(
  		        builder.like(
  		            builder.lower(root.get(PessoasGeral_.DADOS_PJ_GERAL).get(DadosPjGeral_.CNPJ)),
  		            "%" + pessoasGeralFilter.getDadosPjFilter().getCnpj() + "%"));
  		}
  		
  		// CPF E CNPJ NA MESMA LISTA
	    if (StringUtils.hasLength(pessoasGeralFilter.getCpfCnpj())) {
	    	
	    	Join<PessoasGeral ,DadosPfGeral> pfJoin = root.join("dadosPfGeral", JoinType.LEFT);
			Join<PessoasGeral, DadosPjGeral> pjJoin = root.join("dadosPjGeral", JoinType.LEFT);
			
	   		predicates.add(
	   			builder.or(
		   			builder.like(
						builder.lower(
								pfJoin.get(DadosPfGeral_.CPF)),
								"%" + pessoasGeralFilter.getCpfCnpj() + "%"),
		   			builder.like(
						builder.lower(
							pjJoin.get(DadosPjGeral_.CNPJ)),
							"%" + pessoasGeralFilter.getCpfCnpj() + "%")
	   		));
	    }
	    
	    
	    // FISICA JURIDICA
	    if (pessoasGeralFilter.getFisicaJuridica() != null) {
	    	predicates.add(builder.equal(root.get(PessoasGeral_.FISICA_JURIDICA), pessoasGeralFilter.getFisicaJuridica()));
	    	//predicates.add(builder.equal(root.get(PessoasGeral_.FISICA_JURIDICA), "F"));
	    }
	    
	    // TIPO_PESSOA
	    if (pessoasGeralFilter.getTiposPessoasFilter() != null) {
	    	// DESCRICAO
	    	if (StringUtils.hasLength(pessoasGeralFilter.getTiposPessoasFilter().getDescricao())) {
	    		predicates.add(
	    				builder.like(
	    						builder.lower(
	    								root.get(PessoasGeral_.TIPOS_PESSOAS).get(TiposPessoas_.DESCRICAO)),
            "%"
            		+ pessoasGeralFilter.getTiposPessoasFilter().getDescricao().toLowerCase()
            		+ "%"));
	    	}
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
	
	private Long total(PessoasGeralFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<PessoasGeral> root = criteria.from(PessoasGeral.class);
	
	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);
	
	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}

}
