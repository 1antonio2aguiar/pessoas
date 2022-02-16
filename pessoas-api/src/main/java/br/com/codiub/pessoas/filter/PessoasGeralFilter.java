package br.com.codiub.pessoas.filter;

import java.util.Date;
import javax.persistence.Id;
import lombok.Data;

@Data
public class PessoasGeralFilter {
	
	@Id
	private Long id;
	private String nome;
	private String fisicaJuridica;
	private Date dataCadastro;
	private Date dataRegistro;
	private String situacao;
	private TiposPessoasFilter tiposPessoasFilter = new TiposPessoasFilter();
	private SituacoesFilter situacoesFilter = new SituacoesFilter();
	private String cpfCnpj;
	private DadosPfFilter dadosPfFilter = new DadosPfFilter();
	private DadosPjFilter dadosPjFilter = new DadosPjFilter();

}
