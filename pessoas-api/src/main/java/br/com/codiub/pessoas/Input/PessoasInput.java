package br.com.codiub.pessoas.Input;
import java.util.Date;

import br.com.codiub.pessoas.entity.Situacoes;
import br.com.codiub.pessoas.entity.TiposPessoas;
import lombok.Data;

@Data
public class PessoasInput {
	
	private Long id;
	private String nome;
	private String fisicaJuridica;
	private String cpfCnpj;
	private String usuario;
	private String observacao;
	private Long mesEnvioSicom;
	private Long anoEnvioSicom;

	private Date dataCadastro;
	private Date dataRegistro;
	private Date dataAlteracao;
	
	private Situacoes situacoes = new Situacoes();
	private TiposPessoas tiposPessoas = new TiposPessoas();
	private DadosPfInput dadosPf;
	
	
	public String getNome() {
		return nome == null ? null :nome.toUpperCase();
	}
	
	public String getObservacao() {
		return observacao == null ? null :observacao.toUpperCase();
	}
	
	public String getUsuario() {
		return usuario == null ? null :usuario.toUpperCase();
	} 
}
