package br.com.codiub.pessoas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "PES_PESSOAS", schema = "DBO_CC_PESSOAS")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PessoasOrigem {
	
	@Id
	@Column(name = "PESSOA", unique = true, nullable = false)
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
	
	//@OneToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "TIPO_PESSOA")
	//private TiposPessoas tiposPessoas = new TiposPessoas();
	
	//@OneToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "TIPO_DOCUMENTO")
	//private TiposDocumentos tiposDocumentos = new TiposDocumentos();
	
	/*@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CIDADE", updatable = false, insertable = false)
	private CidadesOrigem cidadesOrigem = new CidadesOrigem();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CIDADE", referencedColumnName = "CIDADE"),
			@JoinColumn(name = "DISTRITO", referencedColumnName = "DISTRITO") })
	private DistritosOrigem distritosOrigem = new DistritosOrigem();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns(value = {
		@JoinColumn(name = "CIDADE", referencedColumnName = "CIDADE", updatable = false, insertable = false),
		@JoinColumn(name = "DISTRITO", referencedColumnName = "DISTRITO", updatable = false, insertable = false),
	    @JoinColumn(name = "LOGRADOURO", referencedColumnName = "LOGRADOURO", updatable = false, insertable = false)
	})
	private LogradourosOrigem logradourosOrigem = new LogradourosOrigem();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns(value = {
		@JoinColumn(name = "CIDADE", referencedColumnName = "CIDADE", updatable = false, insertable = false),
		@JoinColumn(name = "DISTRITO", referencedColumnName = "DISTRITO", updatable = false, insertable = false),
	    @JoinColumn(name = "BAIRRO", referencedColumnName = "BAIRRO", updatable = false, insertable = false)
	})
	private BairrosOrigem bairrosOrigem = new BairrosOrigem(); */
	
	/*ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns(value = {
		@JoinColumn(name = "CIDADE", referencedColumnName = "CIDADE", updatable = false, insertable = false),
		@JoinColumn(name = "DISTRITO", referencedColumnName = "DISTRITO", updatable = false, insertable = false),
		@JoinColumn(name = "LOGRADOURO", referencedColumnName = "LOGRADOURO", updatable = false, insertable = false) 
	})
	private CepsOrigem cepsOrigem = new CepsOrigem(); */
	
}
