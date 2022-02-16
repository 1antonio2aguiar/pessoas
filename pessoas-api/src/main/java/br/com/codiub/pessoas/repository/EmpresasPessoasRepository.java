package br.com.codiub.pessoas.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.codiub.pessoas.entity.EmpresasPessoas;
import br.com.codiub.pessoas.repository.empresasPessoas.EmpresasPessoasRepositoryQuery;

public interface EmpresasPessoasRepository extends JpaRepository<EmpresasPessoas, Long>, EmpresasPessoasRepositoryQuery{
	
	List<EmpresasPessoas> findByPessoasEmpresasId(Long codigo); 
	
	List<EmpresasPessoas> findByPessoasPessoasId(Long codigo);
	
}
