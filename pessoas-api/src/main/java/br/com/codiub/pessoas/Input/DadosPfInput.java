package br.com.codiub.pessoas.Input;

import javax.persistence.Id;

import br.com.codiub.pessoas.entity.Etnias;
import br.com.codiub.pessoas.entity.TiposEstadosCivis;
import lombok.Data;

@Data
public class DadosPfInput {
	
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
	//private Long estadoCivil;
	
	private String cpfCnpj;
	
	private Etnias etnias;
	private TiposEstadosCivis tiposEstadosCivis = new TiposEstadosCivis();
	private DistritosInput distritos;
	
	public String getNomeSocial() {
		return nomeSocial == null ? null :nomeSocial.toUpperCase();
	}
	
	public String getMae() {
		return mae == null ? null :mae.toUpperCase();
	}
	
	public String getPai() {
		return pai == null ? null :pai.toUpperCase();
	}
}
