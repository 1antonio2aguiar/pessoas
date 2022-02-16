package br.com.codiub.pessoas.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "DADOS_PJ", schema = "DBO_CC_PESSOAS")
public class DadosPjGeral {
	@Id
	private Long id;
	private String cnpj;
	private String nomeFantasia;
	private String objetoSocial;
	private String microEmpresa;
	private String conjuge;
	private Long tipoEmpresa;

}
