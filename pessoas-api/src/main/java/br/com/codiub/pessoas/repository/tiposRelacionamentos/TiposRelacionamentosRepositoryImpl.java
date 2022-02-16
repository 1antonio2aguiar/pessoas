package br.com.codiub.pessoas.repository.tiposRelacionamentos;

import br.com.codiub.pessoas.entity.TiposRelacionamentos;
import br.com.codiub.pessoas.entity.TiposRelacionamentos_;
import br.com.codiub.pessoas.filter.TiposRelacionamentosFilter;
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

public class TiposRelacionamentosRepositoryImpl implements TiposRelacionamentosRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<TiposRelacionamentos> filtrar(
      TiposRelacionamentosFilter tiposRelacionamentosFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<TiposRelacionamentos> criteria = builder.createQuery(TiposRelacionamentos.class);
    Root<TiposRelacionamentos> root = criteria.from(TiposRelacionamentos.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(tiposRelacionamentosFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<TiposRelacionamentos> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(tiposRelacionamentosFilter));
  }

  private Predicate[] criarRestricoes(
      TiposRelacionamentosFilter tiposRelacionamentosFilter,
      CriteriaBuilder builder,
      Root<TiposRelacionamentos> root) {
    List<Predicate> predicates = new ArrayList<>();

    // DESCRICAO
    if (StringUtils.hasLength(tiposRelacionamentosFilter.getDescricao())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(TiposRelacionamentos_.DESCRICAO)),
              "%" + tiposRelacionamentosFilter.getDescricao().toLowerCase() + "%"));
    }

    // ID
    if (tiposRelacionamentosFilter.getId() != null) {
      predicates.add(
          builder.equal(root.get(TiposRelacionamentos_.ID), tiposRelacionamentosFilter.getId()));
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

  private Long total(TiposRelacionamentosFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<TiposRelacionamentos> root = criteria.from(TiposRelacionamentos.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
