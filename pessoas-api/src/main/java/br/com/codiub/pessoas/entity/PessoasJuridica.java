package br.com.codiub.pessoas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "PESSOAS", schema = "DBO_CC_PESSOAS")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PessoasJuridica {
	@Id
	@GeneratedValue(generator = "SEQ_PESSOAS")
	@SequenceGenerator(name = "SEQ_PESSOAS", sequenceName = "SEQ_PESSOAS", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
	private String nome;
	private String fisicaJuridica;
	private Date dataCadastro;
	private Date dataRegistro;
	private String observacao;
	private String usuario;
	private Date dataAlteracao;
	private Long mesEnvioSicom;
	private Long anoEnvioSicom;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIPO_PESSOA")
	private TiposPessoas tiposPessoas;
	public TiposPessoas getTiposPessoas() {
		return this.tiposPessoas;
	}

	public void setTiposPessoas(TiposPessoas tiposPessoas) {
		this.tiposPessoas = tiposPessoas;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID")
	private DadosPj dadosPj;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SITUACAO")
	private Situacoes situacoes;
	public Situacoes getSituacoes() {
		return this.situacoes;
	}

	public void setSituacoes(Situacoes situacoes) {
		this.situacoes = situacoes;
	}
	
	@Column(name = "DATA_REGISTRO", length = 7)
	public Date getDataRegistro() {
		return this.dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}
}
