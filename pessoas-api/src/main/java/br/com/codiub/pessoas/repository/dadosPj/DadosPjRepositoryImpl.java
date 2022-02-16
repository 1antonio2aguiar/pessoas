package br.com.codiub.pessoas.repository.dadosPj;

import br.com.codiub.pessoas.entity.DadosPf_;
import br.com.codiub.pessoas.entity.DadosPj;
import br.com.codiub.pessoas.entity.DadosPj_;
import br.com.codiub.pessoas.entity.Pessoas_;
import br.com.codiub.pessoas.entity.TiposPessoas_;
import br.com.codiub.pessoas.filter.DadosPjFilter;
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

public class DadosPjRepositoryImpl implements DadosPjRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<DadosPj> filtrar(DadosPjFilter dadosPjFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<DadosPj> criteria = builder.createQuery(DadosPj.class);
    Root<DadosPj> root = criteria.from(DadosPj.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(dadosPjFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<DadosPj> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(dadosPjFilter));
  }

  private Predicate[] criarRestricoes(
      DadosPjFilter dadosPjFilter, CriteriaBuilder builder, Root<DadosPj> root) {
    List<Predicate> predicates = new ArrayList<>();

    //System.err.println("CNPJ: " + dadosPjFilter.getNomeFantasia());
   
    // CNPJ
    if (StringUtils.hasLength(dadosPjFilter.getCnpj())) {
	    predicates.add(
	        builder.like(
	            builder.lower(root.get(DadosPj_.CNPJ)),
	            "%" + dadosPjFilter.getCnpj().toLowerCase() + "%"));
	}
    
    // CONJUGE
    if (StringUtils.hasLength(dadosPjFilter.getConjuge())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(DadosPj_.CONJUGE)),
              "%" + dadosPjFilter.getConjuge().toLowerCase() + "%"));
    }

    // ID
//    if (dadosPjFilter.getId() != null) {
//      predicates.add(builder.equal(root.get(DadosPj_.ID), dadosPjFilter.getId()));
//    }
    
    // MICRO_EMPRESA
//    if (StringUtils.hasLength(dadosPjFilter.getMicroEmpresa())) {
//      predicates.add(
//          builder.like(
//              builder.lower(root.get(DadosPj_.MICRO_EMPRESA)),
//              "%" + dadosPjFilter.getMicroEmpresa().toLowerCase() + "%"));
//    }

    // NOME_FANTASIA
    if (StringUtils.hasLength(dadosPjFilter.getNomeFantasia())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(DadosPj_.NOME_FANTASIA)),
              "%" + dadosPjFilter.getNomeFantasia().toLowerCase() + "%"));
    }

    // OBJETO_SOCIAL
    if (StringUtils.hasLength(dadosPjFilter.getObjetoSocial())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(DadosPj_.OBJETO_SOCIAL)),
              "%" + dadosPjFilter.getObjetoSocial().toLowerCase() + "%"));
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

  private Long total(DadosPjFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<DadosPj> root = criteria.from(DadosPj.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
