package br.com.codiub.pessoas.repository.tiposEnderecos;

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

import br.com.codiub.pessoas.entity.TiposEnderecos;
import br.com.codiub.pessoas.entity.TiposEnderecos_;
import br.com.codiub.pessoas.filter.TiposEnderecosFilter;

public class TiposEnderecosRepositoryImpl implements TiposEnderecosRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;

	  @Override
	  public Page<TiposEnderecos> filtrar(
	      TiposEnderecosFilter tiposEnderecosFilter, Pageable pageable) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<TiposEnderecos> criteria = builder.createQuery(TiposEnderecos.class);
	    Root<TiposEnderecos> root = criteria.from(TiposEnderecos.class);

	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);

	    Predicate[] predicates = criarRestricoes(tiposEnderecosFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);

	    TypedQuery<TiposEnderecos> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);

	    return new PageImpl<>(query.getResultList(), pageable, total(tiposEnderecosFilter));
	  }

	  private Predicate[] criarRestricoes(
	      TiposEnderecosFilter tiposEnderecosFilter,
	      CriteriaBuilder builder,
	      Root<TiposEnderecos> root) {
	    List<Predicate> predicates = new ArrayList<>();

	    // DESCRICAO
	    if (StringUtils.hasLength(tiposEnderecosFilter.getDescricao())) {
	      predicates.add(
	          builder.like(
	              builder.lower(root.get(TiposEnderecos_.DESCRICAO)),
	              "%" + tiposEnderecosFilter.getDescricao().toLowerCase() + "%"));
	    }

	    // ID
	    if (tiposEnderecosFilter.getId() != null) {
	      predicates.add(builder.equal(root.get(TiposEnderecos_.ID), tiposEnderecosFilter.getId()));
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

	  private Long total(TiposEnderecosFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<TiposEnderecos> root = criteria.from(TiposEnderecos.class);

	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);

	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	  }
}
