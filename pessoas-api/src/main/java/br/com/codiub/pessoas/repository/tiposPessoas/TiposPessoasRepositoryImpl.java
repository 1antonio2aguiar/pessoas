package br.com.codiub.pessoas.repository.tiposPessoas;

import br.com.codiub.pessoas.entity.TiposPessoas;
import br.com.codiub.pessoas.entity.TiposPessoas_;
import br.com.codiub.pessoas.filter.TiposPessoasFilter;
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

public class TiposPessoasRepositoryImpl implements TiposPessoasRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<TiposPessoas> filtrar(TiposPessoasFilter tiposPessoasFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<TiposPessoas> criteria = builder.createQuery(TiposPessoas.class);
    Root<TiposPessoas> root = criteria.from(TiposPessoas.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(tiposPessoasFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<TiposPessoas> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(tiposPessoasFilter));
  }

  private Predicate[] criarRestricoes(
      TiposPessoasFilter tiposPessoasFilter, CriteriaBuilder builder, Root<TiposPessoas> root) {
    List<Predicate> predicates = new ArrayList<>();

    // DESCRICAO
    if (StringUtils.hasLength(tiposPessoasFilter.getDescricao())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(TiposPessoas_.DESCRICAO)),
              "%" + tiposPessoasFilter.getDescricao().toLowerCase() + "%"));
    }

    // ID
//    if (tiposPessoasFilter.getId() != null) {
//      predicates.add(builder.equal(root.get(TiposPessoas_.ID), tiposPessoasFilter.getId()));
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

  private Long total(TiposPessoasFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<TiposPessoas> root = criteria.from(TiposPessoas.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
