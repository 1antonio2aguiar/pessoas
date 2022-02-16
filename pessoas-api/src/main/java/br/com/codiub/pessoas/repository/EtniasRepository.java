package br.com.codiub.pessoas.repository;

import br.com.codiub.pessoas.entity.Etnias;
import br.com.codiub.pessoas.repository.etnias.EtniasRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtniasRepository extends JpaRepository<Etnias, Long>, EtniasRepositoryQuery {}
