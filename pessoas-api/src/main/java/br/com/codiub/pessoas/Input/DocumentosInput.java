package br.com.codiub.pessoas.Input;

import java.util.Date;

import javax.persistence.Id;

import br.com.codiub.pessoas.entity.TiposDocumentos;
import lombok.Data;

@Data
public class DocumentosInput {
	
	@Id
	private Long id;
	private Long pessoa;
	
	private String numeroDocumento;
	private Date dataDocumento;
	private Date dataExpedicao;
	private String documentoOrigem;
	private String orgaoExpedidor;
	private String numeroRegistroCnh;
	private Date dataPrimeiraCnh;
	private Date dataValidadeCnh;
	private String categoriaCnh;
	private Long tituloEleitoral;
	private Long zona;
	private Long secao;
	private String termo;
	private String folha;
	private String livro;
	private Date dtEmisCert;
	private Long ufCartorio;
	private Long cidadeCartorio;
	private Long cartorio;
	private String usuario;
	private Date dataAlteracao;
	
	TiposDocumentos tiposDocumentos = new TiposDocumentos();
	PessoasInput pessoas = new PessoasInput();

}
