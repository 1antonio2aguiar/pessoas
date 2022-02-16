package br.com.codiub.pessoas.repository.situacoes;

import br.com.codiub.pessoas.entity.Situacoes;
import br.com.codiub.pessoas.entity.Situacoes_;
import br.com.codiub.pessoas.filter.SituacoesFilter;
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

public class SituacoesRepositoryImpl implements SituacoesRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<Situacoes> filtrar(SituacoesFilter situacoesFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Situacoes> criteria = builder.createQuery(Situacoes.class);
    Root<Situacoes> root = criteria.from(Situacoes.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(situacoesFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<Situacoes> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(situacoesFilter));
  }

  private Predicate[] criarRestricoes(
      SituacoesFilter situacoesFilter, CriteriaBuilder builder, Root<Situacoes> root) {
    List<Predicate> predicates = new ArrayList<>();

    // DESCRICAO
    if (StringUtils.hasLength(situacoesFilter.getDescricao())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(Situacoes_.DESCRICAO)),
              "%" + situacoesFilter.getDescricao().toLowerCase() + "%"));
    }

    // ID
//    if (situacoesFilter.getId() != null) {
//      predicates.add(builder.equal(root.get(Situacoes_.ID), situacoesFilter.getId()));
//    }

    return predicates.toArray(new Predicate[predicates.size()]);
  }

  private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
    int paginaAtual = pageable.getPageNumber();
    int totalRegistrosPorPagina = pageable.getPageSize();
    int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

    query.setFirstResult(primeiroRegistroDaPagina);
    query.setMaxResults(totalRegistrosPorPagina);
  }

  private Long total(SituacoesFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<Situacoes> root = criteria.from(Situacoes.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
