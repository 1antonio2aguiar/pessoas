package br.com.codiub.pessoas.Input;

import org.hibernate.validator.constraints.Length;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class ProfissoesInput {
	private Long id;
	
	@NotNull
	@Length(min = 3)
	private String nome;
	
	public String getNome() {
		return nome == null ? null :nome.toUpperCase();
	}
	
}