package br.com.codiub.pessoas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.codiub.pessoas.entity.Enderecos;
import br.com.codiub.pessoas.repository.enderecos.EnderecosRepositoryQuery;

public interface EnderecosRepository extends JpaRepository<Enderecos, Long>, EnderecosRepositoryQuery{
	
	List<Enderecos> findByPessoasId(Long codigo);

}
