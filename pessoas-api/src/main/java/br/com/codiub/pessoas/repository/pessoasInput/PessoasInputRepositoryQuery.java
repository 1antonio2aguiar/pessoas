package br.com.codiub.pessoas.repository.pessoasInput;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import br.com.codiub.pessoas.Input.PessoasInput;
import br.com.codiub.pessoas.filter.PessoasInputFilter;

public interface PessoasInputRepositoryQuery {
	
	public Page<PessoasInput> filtrar(PessoasInputFilter pessoasInputFilter, Pageable pageable);
	 //public List<PessoasInput> filtrar(PessoasInputFilter pessoasInputFilter);

}
