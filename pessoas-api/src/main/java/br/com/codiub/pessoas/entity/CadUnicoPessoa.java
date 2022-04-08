package br.com.codiub.pessoas.entity;
// Generated 3 de mai. de 2021 14:41:28 by Hibernate Tools 4.3.5.Final

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CAD_UNICO_PESSOA", schema = "DBO_CC_PESSOAS")
public class CadUnicoPessoa implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "SEQ_CAD_UNICO_PESSOA")
	@SequenceGenerator(name = "SEQ_CAD_UNICO_PESSOA", sequenceName = "SEQ_CAD_UNICO_PESSOA", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false)
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
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_ORIGEM")
	private PessoasOrigem pessoasOrigem = new PessoasOrigem();

}
