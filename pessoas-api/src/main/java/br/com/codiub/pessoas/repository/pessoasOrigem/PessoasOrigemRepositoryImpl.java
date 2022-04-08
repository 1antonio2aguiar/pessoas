package br.com.codiub.pessoas.repository.pessoasOrigem;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;

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

import br.com.codiub.pessoas.entity.CepsOrigem;
import br.com.codiub.pessoas.entity.CepsOrigem_;
import br.com.codiub.pessoas.entity.PessoasOrigem;
import br.com.codiub.pessoas.entity.PessoasOrigem_;
import br.com.codiub.pessoas.filter.PessoasOrigemFilter;

public class PessoasOrigemRepositoryImpl implements PessoasOrigemRepositoryQuery{

	@PersistenceContext private EntityManager manager;
	
	@Override
	public Page<PessoasOrigem> filtrar(PessoasOrigemFilter pessoasOrigemFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<PessoasOrigem> criteria = builder.createQuery(PessoasOrigem.class);
	    Root<PessoasOrigem> root = criteria.from(PessoasOrigem.class);
	    
	    List<Order> orders = QueryUtils.toOrders(pageable.getSort(), root, builder);
	    
	    Predicate[] predicates = criarRestricoes(pessoasOrigemFilter, builder, root);
	    criteria.where(predicates).orderBy(orders);


	    TypedQuery<PessoasOrigem> query = manager.createQuery(criteria);
	    //adicionarRestricoesDePaginacao(query, pageable);
	    
	    return new PageImpl<>(query.getResultList(), pageable, total(pessoasOrigemFilter));
		
	} 
	
	private Predicate[] criarRestricoes(
		PessoasOrigemFilter pessoasOrigemFilter, CriteriaBuilder builder, Root<PessoasOrigem> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		//List<Order> orderList = QueryUtils.toOrders(pageable.getSort(), root, builder);
		//query.orderBy(orderList);
		
		
		/*Join<PessoasOrigem, CepsOrigem> cepJoin = root.join(PessoasOrigem_.CEPS_ORIGEM);
		
		predicates.add(builder.between(
			root.get(PessoasOrigem_.NUMERO), 
			cepJoin.get(CepsOrigem_.NUMERO_INI), 
			cepJoin.get(CepsOrigem_.NUMERO_FIM)));*/
		
		System.err.println("OLHA ONDE ELE VÊIO !! ");
		
		// PESSOA
		if (pessoasOrigemFilter.getPessoa() != null) {
		  predicates.add(builder.equal(root.get(PessoasOrigem_.PESSOA), pessoasOrigemFilter.getPessoa()));
		}
		
		// NOME
	    if (StringUtils.hasLength(pessoasOrigemFilter.getNome())) {
	      predicates.add(
	          builder.like(
	              builder.lower(
	            	root.get(PessoasOrigem_.NOME)),
	              		"%" + pessoasOrigemFilter.getNome().toLowerCase() + "%"));
	    }
	    
	    // CEP MAIOR 0U IGUAL AO NUMERO
	    /*if (pessoasOrigemFilter.getCepsFilter().getNumeroIni() != null) {
	    	System.err.println("OLHA ONDE ELE VÊIO !! " + pessoasOrigemFilter.getCepsFilter().getNumeroIni());
	    	predicates.add(builder.greaterThanOrEqualTo(root.get(PessoasOrigem_.NUMERO), 
	    			pessoasOrigemFilter.getCepsFilter().getNumeroIni()));
	    }
	    
	    // CEP MENOR 0U IGUAL AO NUMERO
	    if (pessoasOrigemFilter.getCepsFilter().getNumeroFim() != null) {
	    	System.err.println("OLHA ONDE ELE VÊIO !! " + pessoasOrigemFilter.getCepsFilter().getNumeroFim());
	    	predicates.add(builder.lessThanOrEqualTo(root.get(PessoasOrigem_.NUMERO), 
	    			pessoasOrigemFilter.getCepsFilter().getNumeroFim()));
	    } */
	    
	    return predicates.toArray(new Predicate[predicates.size()]);
	    
	}
	
	/*private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
	    int paginaAtual = pageable.getPageNumber();
	    int totalRegistrosPorPagina = pageable.getPageSize();
	    int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
	
	    query.setFirstResult(primeiroRegistroDaPagina);
	    query.setMaxResults(totalRegistrosPorPagina);
	}*/

	private Long total(PessoasOrigemFilter filter) {
	    CriteriaBuilder builder = manager.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
	    Root<PessoasOrigem> root = criteria.from(PessoasOrigem.class);
	
	    Predicate[] predicates = criarRestricoes(filter, builder, root);
	    criteria.where(predicates);
	
	    criteria.select(builder.count(root));
	    return manager.createQuery(criteria).getSingleResult();
	}

}
