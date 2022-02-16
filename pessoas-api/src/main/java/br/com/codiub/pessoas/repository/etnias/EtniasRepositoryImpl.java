package br.com.codiub.pessoas.repository.etnias;

import br.com.codiub.pessoas.entity.Etnias;
import br.com.codiub.pessoas.entity.Etnias_;
import br.com.codiub.pessoas.filter.EtniasFilter;
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

public class EtniasRepositoryImpl implements EtniasRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<Etnias> filtrar(EtniasFilter etniasFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Etnias> criteria = builder.createQuery(Etnias.class);
    Root<Etnias> root = criteria.from(Etnias.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(etniasFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<Etnias> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(etniasFilter));
  }

  private Predicate[] criarRestricoes(
      EtniasFilter etniasFilter, CriteriaBuilder builder, Root<Etnias> root) {
    List<Predicate> predicates = new ArrayList<>();

    // DESCRICAO
    if (StringUtils.hasLength(etniasFilter.getDescricao())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(Etnias_.DESCRICAO)),
              "%" + etniasFilter.getDescricao().toLowerCase() + "%"));
    }

    // ID
    if (etniasFilter.getId() != null) {
      predicates.add(builder.equal(root.get(Etnias_.ID), etniasFilter.getId()));
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

  private Long total(EtniasFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<Etnias> root = criteria.from(Etnias.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
