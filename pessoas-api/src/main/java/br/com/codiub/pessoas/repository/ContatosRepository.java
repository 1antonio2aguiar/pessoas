package br.com.codiub.pessoas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.pessoas.entity.Contatos;
import br.com.codiub.pessoas.repository.contatos.ContatosRepositoryQuery;


public interface ContatosRepository extends JpaRepository<Contatos, Long>, ContatosRepositoryQuery{
	List<Contatos> findByPessoasId(Long codigo);
}

