package br.com.codiub.pessoas.repository.tiposEmpresas;

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

import br.com.codiub.pessoas.entity.TiposEmpresas;
import br.com.codiub.pessoas.entity.TiposEmpresas_;
import br.com.codiub.pessoas.filter.TiposEmpresasFilter;

public class TiposEmpresasRepositoryImpl implements TiposEmpresasRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<TiposEmpresas> filtrar(TiposEmpresasFilter tiposEmpresasFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<TiposEmpresas> criteria = builder.createQuery(TiposEmpresas.class);
    Root<TiposEmpresas> root = criteria.from(TiposEmpresas.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(tiposEmpresasFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<TiposEmpresas> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(tiposEmpresasFilter));
  }

  private Predicate[] criarRestricoes(
      TiposEmpresasFilter tiposEmpresasFilter, CriteriaBuilder builder, Root<TiposEmpresas> root) {
    List<Predicate> predicates = new ArrayList<>();

    // DESCRICAO
    if (StringUtils.hasLength(tiposEmpresasFilter.getDescricao())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(TiposEmpresas_.TIPO_ESTABELECIMENTO).get(TiposEmpresas_.DESCRICAO)),
              "%" + tiposEmpresasFilter.getDescricao().toLowerCase() + "%"));
    }

    // TIPO_ESTABELECIMENTO
    if (tiposEmpresasFilter.getTipoEstabelecimento() != null) {
      predicates.add(
          builder.equal(
              root.get(TiposEmpresas_.TIPO_ESTABELECIMENTO).get(TiposEmpresas_.TIPO_ESTABELECIMENTO),
              tiposEmpresasFilter.getTipoEstabelecimento()));
    }
    // USUARIO
    if (tiposEmpresasFilter.getUsuario() != null) {
      predicates.add(
          builder.equal(root.get(TiposEmpresas_.USUARIO).get(TiposEmpresas_.USUARIO), tiposEmpresasFilter.getUsuario()));
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

  private Long total(TiposEmpresasFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<TiposEmpresas> root = criteria.from(TiposEmpresas.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
