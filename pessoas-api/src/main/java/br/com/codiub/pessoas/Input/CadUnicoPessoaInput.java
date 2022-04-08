package br.com.codiub.pessoas.Input;

import java.util.Date;

import br.com.codiub.pessoas.entity.PessoasOrigem;
import lombok.Data;

@Data
public class CadUnicoPessoaInput {
	
	private Long id;
	private Long tipoPessoa;
	private String nome;
	private String fisicaJuridica;
	private Date dataCadastro;
	private String cpfCnpj;
	private Date dataNascimento;
	private String estadoCivil;
	private String sexo;
	private Long cidadeNascimento;
	private String observacao;
	private String email;
	private String banco;
	private String usuario;
	private Date dataAlteracao;
	private Long pessoasCdUnico;
	private String status;
	private String nomeSomdex;
	private String nomeTransf;
	
	private PessoasOrigem pessoasOrigem = new PessoasOrigem();

}
