package br.com.codiub.pessoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.pessoas.entity.Distritos;
import br.com.codiub.pessoas.repository.distritos.DistritosRepositoryQuery;

public interface DistritosRepository extends JpaRepository<Distritos, Long>, DistritosRepositoryQuery{

}
