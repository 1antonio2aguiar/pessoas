package br.com.codiub.pessoas.Input;

import java.util.Date;

import br.com.codiub.pessoas.entity.BairrosOrigem;
import br.com.codiub.pessoas.entity.LogradourosOrigem;
import br.com.codiub.pessoas.entity.TiposDocumentos;
import br.com.codiub.pessoas.entity.TiposPessoas;
import lombok.Data;

@Data
public class PessoasOrigemInput {
	
	private Long   pessoa             ; 
	private Long   cgc_cpf            ; 
	private Long   numero             ; 
	private Long   cidade_nascimento  ; 
	private Long   titulo_eleitoral   ; 
	private Long   zona               ; 
	private Long   secao              ; 
	private Long   telefone           ; 
	private Long   recado             ; 
	private Long   celular            ; 
	private Long   fax                ; 
	private Long   pessoa_matriz      ; 
	private Long   profissao          ; 
	private Long   usuario            ; 
	private Long   mes_envio_sicom    ; 
	private Long   ano_envio_sicom    ; 
	private String complemento        ; 
	private String nome               ; 
	private String fisica_juridica    ; 
	private String estado_civil       ; 
	private String sexo               ; 
	private String numero_docto       ; 
	private String orgao_docto        ; 
	private String mae                ; 
	private String pai                ; 
	private String e_mail             ; 
	private String pagina_web         ; 
	private String inscricao_estadual ; 
	private String fantasia           ; 
	private String observacao         ; 
	private String conjugue           ; 
	private String usuario_alteracao  ; 
	private String objeto_social      ; 
	private String microempresa       ; 
	private String justificativa      ; 
	private String nome_social        ; 
	private Date   emissao_docto      ; 
	private Date   data_nascimento    ; 
	private Date   dt_alteracao       ; 
	
	private TiposPessoas tiposPessoas = new TiposPessoas();
	private TiposDocumentos tiposDocumentos = new TiposDocumentos();
	private LogradourosOrigem logradourosOrigem = new LogradourosOrigem();
	private BairrosOrigem bairrosOrigem = new BairrosOrigem();

}
