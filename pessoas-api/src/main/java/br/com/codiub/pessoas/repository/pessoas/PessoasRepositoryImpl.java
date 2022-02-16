package br.com.codiub.pessoas.repository.pessoas;

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

import br.com.codiub.pessoas.entity.DadosPfGeral_;
import br.com.codiub.pessoas.entity.Pessoas;
import br.com.codiub.pessoas.entity.Pessoas_;
import br.com.codiub.pessoas.entity.TiposPessoas_;
import br.com.codiub.pessoas.filter.PessoasFilter;

public class PessoasRepositoryImpl implements PessoasRepositoryQuery {
	
  @PersistenceContext private EntityManager manager;
  
  @Override
  public Page<Pessoas> filtrar(PessoasFilter pessoasFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Pessoas> criteria = builder.createQuery(Pessoas.class);
    Root<Pessoas> root = criteria.from(Pessoas.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(pessoasFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<Pessoas> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(pessoasFilter));
  }

  private Predicate[] criarRestricoes(
      PessoasFilter pessoasFilter, CriteriaBuilder builder, Root<Pessoas> root) {
	  
	  //Join<Pessoas, DadosPf> dadosPfJoin = root.join(DadosPf_.ID);
	  
	  List<Predicate> predicates = new ArrayList<>();

    // DATA_CADASTRO
    if (pessoasFilter.getDataCadastro() != null) {
      predicates.add(
          builder.equal(root.get(Pessoas_.DATA_CADASTRO), pessoasFilter.getDataCadastro()));
    }

    // DATA_REGISTRO - MESMO QUE DATA NASCIMENTO
    if (pessoasFilter.getDataRegistro() != null) {
		Date data = pessoasFilter.getDataRegistro();
		//remove time portion from specified date: now dd/mm/yy 00:00
	    Date startDate = DateUtils.truncate(data, Calendar.DATE);
	    //new date with time initialized to 23:59:59
	    Date endDate = DateUtils.addSeconds(DateUtils.addDays(startDate, 1), - 1);
	    //System.err.println("DATA  : " + data + " start " + startDate + " end " + endDate);
		predicates.add(
	    		builder.between(root.get(Pessoas_.DATA_REGISTRO), startDate, endDate));
			
		// Isso aqui e para exemplos futuros	    	
		//	    	predicates.add(
		//					builder.greaterThanOrEqualTo(root.get(PessoasGeral_.DATA_REGISTRO), dataInicial));
		//			
		//			predicates.add(
		//					builder.lessThanOrEqualTo(root.get(PessoasGeral_.DATA_REGISTRO), dataFinal));
    }
    
    // ID
    if (pessoasFilter.getId() != null) {
      predicates.add(builder.equal(root.get(Pessoas_.ID), pessoasFilter.getId()));
    }
    
    // NOME
    if (StringUtils.hasLength(pessoasFilter.getNome())) {
      predicates.add(
          builder.like(
              builder.lower(
            	root.get(Pessoas_.NOME)),
              		"%" + pessoasFilter.getNome().toLowerCase() + "%"));
    }
    
    //NOME DA MÃE
    //System.err.println(pessoasFilter.getDadosPfGeralFilter().getMae() );
    if (StringUtils.hasLength(pessoasFilter.getDadosPfGeralFilter().getMae())) {
	   		predicates.add(
	   			builder.like(
					builder.lower(root.get(Pessoas_.DADOS_PF_GERAL).get(DadosPfGeral_.MAE)),
					"%" + pessoasFilter.getDadosPfGeralFilter().getMae().toLowerCase() + "%"));
	}
    
    //CPF
    if (StringUtils.hasLength(pessoasFilter.getDadosPfGeralFilter().getCpf())) {
    	predicates.add(
    		builder.like(
		        builder.lower(root.get(Pessoas_.DADOS_PF_GERAL).get(DadosPfGeral_.CPF)),
		            "%" + pessoasFilter.getDadosPfGeralFilter().getCpf() + "%"));
    }
    
    // FISICA JURIDICA
    if (pessoasFilter.getFisicaJuridica() != null) {
        predicates.add(builder.equal(root.get(Pessoas_.FISICA_JURIDICA), pessoasFilter.getFisicaJuridica()));
      }

    // TIPO_PESSOA
    if (pessoasFilter.getTiposPessoasFilter() != null) {
      // DESCRICAO
      if (StringUtils.hasLength(pessoasFilter.getTiposPessoasFilter().getDescricao())) {
        predicates.add(
            builder.like(
                builder.lower(
                    root.get(Pessoas_.TIPOS_PESSOAS).get(TiposPessoas_.DESCRICAO)),
                "%"
                    + pessoasFilter.getTiposPessoasFilter().getDescricao().toLowerCase()
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

  private Long total(PessoasFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<Pessoas> root = criteria.from(Pessoas.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
