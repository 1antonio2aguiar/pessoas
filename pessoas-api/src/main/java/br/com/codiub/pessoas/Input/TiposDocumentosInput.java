package br.com.codiub.pessoas.Input;

import org.hibernate.validator.constraints.Length;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class TiposDocumentosInput {
	private Long id;
	
	@NotNull
	@Length(min = 3)
	private String descricao;
	
	public String getDescricao() {
		return descricao == null ? null :descricao.toUpperCase();
	}
	
}

