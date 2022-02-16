package br.com.codiub.pessoas.repository.tiposDocumentos;

import br.com.codiub.pessoas.entity.TiposDocumentos;
import br.com.codiub.pessoas.entity.TiposDocumentos_;
import br.com.codiub.pessoas.filter.TiposDocumentosFilter;
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

public class TiposDocumentosRepositoryImpl implements TiposDocumentosRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<TiposDocumentos> filtrar(
      TiposDocumentosFilter tiposDocumentosFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<TiposDocumentos> criteria = builder.createQuery(TiposDocumentos.class);
    Root<TiposDocumentos> root = criteria.from(TiposDocumentos.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(tiposDocumentosFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<TiposDocumentos> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(tiposDocumentosFilter));
  }

  private Predicate[] criarRestricoes(
      TiposDocumentosFilter tiposDocumentosFilter,
      CriteriaBuilder builder,
      Root<TiposDocumentos> root) {
    List<Predicate> predicates = new ArrayList<>();

    // DESCRICAO
    if (StringUtils.hasLength(tiposDocumentosFilter.getDescricao())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(TiposDocumentos_.DESCRICAO)),
              "%" + tiposDocumentosFilter.getDescricao().toLowerCase() + "%"));
    }

    // ID
    if (tiposDocumentosFilter.getId() != null) {
      predicates.add(builder.equal(root.get(TiposDocumentos_.ID), tiposDocumentosFilter.getId()));
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

  private Long total(TiposDocumentosFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<TiposDocumentos> root = criteria.from(TiposDocumentos.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
