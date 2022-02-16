package br.com.codiub.pessoas.repository.tiposEstadosCivis;

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

import br.com.codiub.pessoas.entity.TiposEstadosCivis;
import br.com.codiub.pessoas.entity.TiposEstadosCivis_;
import br.com.codiub.pessoas.filter.TiposEstadosCivisFilter;

public class TiposEstadosCivisRepositoryImpl implements TiposEstadosCivisRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<TiposEstadosCivis> filtrar(
      TiposEstadosCivisFilter tiposEstadosCivisFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<TiposEstadosCivis> criteria = builder.createQuery(TiposEstadosCivis.class);
    Root<TiposEstadosCivis> root = criteria.from(TiposEstadosCivis.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(tiposEstadosCivisFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<TiposEstadosCivis> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(tiposEstadosCivisFilter));
  }

  private Predicate[] criarRestricoes(
      TiposEstadosCivisFilter tiposEstadosCivisFilter,
      CriteriaBuilder builder,
      Root<TiposEstadosCivis> root) {
    List<Predicate> predicates = new ArrayList<>();

    // DESCRICAO
    if (StringUtils.hasLength(tiposEstadosCivisFilter.getDescricao())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(TiposEstadosCivis_.DESCRICAO)),
              "%" + tiposEstadosCivisFilter.getDescricao().toLowerCase() + "%"));
    }

    // ID
    if (tiposEstadosCivisFilter.getId() != null) {
      predicates.add(
          builder.equal(root.get(TiposEstadosCivis_.ID), tiposEstadosCivisFilter.getId()));
    }
    // SIGLA
    if (StringUtils.hasLength(tiposEstadosCivisFilter.getSigla())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(TiposEstadosCivis_.SIGLA)),
              "%" + tiposEstadosCivisFilter.getSigla().toLowerCase() + "%"));
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

  private Long total(TiposEstadosCivisFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<TiposEstadosCivis> root = criteria.from(TiposEstadosCivis.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
