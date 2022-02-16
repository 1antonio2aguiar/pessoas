package br.com.codiub.pessoas.repository.tiposVinculosEmpresas;

import br.com.codiub.pessoas.entity.TiposVinculosEmpresas;
import br.com.codiub.pessoas.entity.TiposVinculosEmpresas_;
import br.com.codiub.pessoas.filter.TiposVinculosEmpresasFilter;
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

public class TiposVinculosEmpresasRepositoryImpl implements TiposVinculosEmpresasRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<TiposVinculosEmpresas> filtrar(
      TiposVinculosEmpresasFilter tiposVinculosEmpresasFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<TiposVinculosEmpresas> criteria =
        builder.createQuery(TiposVinculosEmpresas.class);
    Root<TiposVinculosEmpresas> root = criteria.from(TiposVinculosEmpresas.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(tiposVinculosEmpresasFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<TiposVinculosEmpresas> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(tiposVinculosEmpresasFilter));
  }

  private Predicate[] criarRestricoes(
      TiposVinculosEmpresasFilter tiposVinculosEmpresasFilter,
      CriteriaBuilder builder,
      Root<TiposVinculosEmpresas> root) {
    List<Predicate> predicates = new ArrayList<>();

    // DESCRICAO
    if (StringUtils.hasLength(tiposVinculosEmpresasFilter.getDescricao())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(TiposVinculosEmpresas_.DESCRICAO)),
              "%" + tiposVinculosEmpresasFilter.getDescricao().toLowerCase() + "%"));
    }

    // ID
    if (tiposVinculosEmpresasFilter.getId() != null) {
      predicates.add(
          builder.equal(root.get(TiposVinculosEmpresas_.ID), tiposVinculosEmpresasFilter.getId()));
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

  private Long total(TiposVinculosEmpresasFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<TiposVinculosEmpresas> root = criteria.from(TiposVinculosEmpresas.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
