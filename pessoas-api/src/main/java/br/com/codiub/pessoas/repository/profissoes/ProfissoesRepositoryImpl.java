package br.com.codiub.pessoas.repository.profissoes;

import br.com.codiub.pessoas.entity.Profissoes;
import br.com.codiub.pessoas.entity.Profissoes_;
import br.com.codiub.pessoas.filter.ProfissoesFilter;
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

public class ProfissoesRepositoryImpl implements ProfissoesRepositoryQuery {

  @PersistenceContext private EntityManager manager;

  @Override
  public Page<Profissoes> filtrar(ProfissoesFilter profissoesFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Profissoes> criteria = builder.createQuery(Profissoes.class);
    Root<Profissoes> root = criteria.from(Profissoes.class);

    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

    Predicate[] predicates = criarRestricoes(profissoesFilter, builder, root);
    criteria.where(predicates).orderBy(orders);

    TypedQuery<Profissoes> query = manager.createQuery(criteria);
    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(profissoesFilter));
  }

  private Predicate[] criarRestricoes(
      ProfissoesFilter profissoesFilter, CriteriaBuilder builder, Root<Profissoes> root) {
    List<Predicate> predicates = new ArrayList<>();

    // ID
    if (profissoesFilter.getId() != null) {
      predicates.add(builder.equal(root.get(Profissoes_.ID), profissoesFilter.getId()));
    }
    // NOME
    if (StringUtils.hasLength(profissoesFilter.getNome())) {
      predicates.add(
          builder.like(
              builder.lower(root.get(Profissoes_.NOME)),
              "%" + profissoesFilter.getNome().toLowerCase() + "%"));
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

  private Long total(ProfissoesFilter filter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
    Root<Profissoes> root = criteria.from(Profissoes.class);

    Predicate[] predicates = criarRestricoes(filter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }
}
