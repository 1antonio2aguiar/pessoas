package br.com.codiub.pessoas.repository.tiposContatos;

import br.com.codiub.pessoas.entity.TiposContatos;
import br.com.codiub.pessoas.entity.TiposContatos_;
import br.com.codiub.pessoas.filter.TiposContatosFilter;
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

public class TiposContatosRepositoryImpl implements TiposContatosRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<TiposContatos> filtrar(TiposContatosFilter tiposContatosFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<TiposContatos> criteria = builder.createQuery(TiposContatos.class);
    Root<TiposContatos> root = criteria.from(TiposContatos.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(tiposContatosFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<TiposContatos> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(tiposContatosFilter));
  }

  private Predicate[] criarRestricoes(
      TiposContatosFilter tiposContatosFilter, CriteriaBuilder builder, Root<TiposContatos> root) {
    List<Predicate> predicates = new ArrayList<>();

    // DESCRICAO
    if (StringUtils.hasLength(tiposContatosFilter.getDescricao())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(TiposContatos_.DESCRICAO)),
              "%" + tiposContatosFilter.getDescricao().toLowerCase() + "%"));
    }

    // ID
    if (tiposContatosFilter.getId() != null) {
      predicates.add(builder.equal(root.get(TiposContatos_.ID), tiposContatosFilter.getId()));
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

  private Long total(TiposContatosFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<TiposContatos> root = criteria.from(TiposContatos.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
