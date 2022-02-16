package br.com.codiub.pessoas.repository.pessoasInput;

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

import br.com.codiub.pessoas.Input.PessoasInput;
import br.com.codiub.pessoas.entity.PessoasGeral_;
import br.com.codiub.pessoas.filter.PessoasInputFilter;

public class PessoasInputRepositoryImpl implements PessoasInputRepositoryQuery{
	
	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<PessoasInput> filtrar(PessoasInputFilter pessoasInputFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<PessoasInput> criteria = builder.createQuery(PessoasInput.class);
		Root<PessoasInput> root = criteria.from(PessoasInput.class);
		
		List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
		
		Predicate[] predicates = criarRestricoes(pessoasInputFilter, builder, root);
		criteria.where(predicates).orderBy(orders);
		
		TypedQuery<PessoasInput> query = manager.createQuery(criteria);
	    adicionarRestricoesDePaginacao(query, pageable);
	    
	    return new PageImpl<>(query.getResultList(), pageable, total(pessoasInputFilter));
	    
	}
	
	private Predicate[] criarRestricoes(
	    PessoasInputFilter pessoasInputFilter, CriteriaBuilder builder, Root<PessoasInput> root)  {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// DATA_CADASTRO
	    if (pessoasInputFilter.getDataCadastro() != null) {
	      predicates.add(
	          builder.equal(root.get(PessoasGeral_.DATA_CADASTRO), pessoasInputFilter.getDataCadastro()));
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
	
	private Long total(PessoasInputFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<PessoasInput> root = criteria.from(PessoasInput.class);
	
	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);
	
	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}

}
