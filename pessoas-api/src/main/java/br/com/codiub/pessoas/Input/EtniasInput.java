package br.com.codiub.pessoas.Input;

import org.hibernate.validator.constraints.Length;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class EtniasInput {
	private Long id;
	
	@NotNull
	@Length(min = 3)
	private String descricao;
	private String codCadSus;
	
	public String getDescricao() {
		return descricao == null ? null :descricao.toUpperCase();
	}
	
	public String getCodCadSus() {
		return codCadSus == null ? null :codCadSus.toUpperCase();
	}
}