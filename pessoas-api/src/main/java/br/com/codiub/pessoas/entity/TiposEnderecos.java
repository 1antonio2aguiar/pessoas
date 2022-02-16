package br.com.codiub.pessoas.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TIPOS_ENDERECOS", schema = "DBO_CC_PESSOAS")
public class TiposEnderecos {
	
	@Id
	private Long Id;
	private String Descricao;

}
