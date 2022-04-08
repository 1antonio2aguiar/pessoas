package br.com.codiub.pessoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.codiub.pessoas.entity.CadUnicoPessoa;
import br.com.codiub.pessoas.repository.cadUnicoPessoa.CadUnicoPessoaRepositoryQuery;

public interface CadUnicoPessoaRepository extends JpaRepository<CadUnicoPessoa, Long>, CadUnicoPessoaRepositoryQuery{

}
