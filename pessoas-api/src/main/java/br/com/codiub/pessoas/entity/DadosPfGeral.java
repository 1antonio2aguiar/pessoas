package br.com.codiub.pessoas.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "DADOS_PF", schema = "DBO_CC_PESSOAS")
public class DadosPfGeral {
	
	@Id
	private Long id;
	private String cpf;
	private String nomeSocial;
	private Long raca;
	private Long cor;
	private String recebeBf;
	private Long cartaoSus;
	private String sexo;
	private Long localNascimento;
	private String mae;
	private String pai;
	private Long estadoCivil;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ETNIA")
	private Etnias etnias = new Etnias();
	
}
