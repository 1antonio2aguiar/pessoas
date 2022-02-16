package br.com.codiub.pessoas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.pessoas.entity.Documentos;
import br.com.codiub.pessoas.repository.documentos.DocumentosRepositoryQuery;

public interface DocumentosRepository extends JpaRepository<Documentos, Long>, DocumentosRepositoryQuery{
	List<Documentos> findByPessoasId(Long codigo);
}