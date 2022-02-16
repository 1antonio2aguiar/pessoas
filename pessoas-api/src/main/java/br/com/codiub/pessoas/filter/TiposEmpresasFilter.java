package br.com.codiub.pessoas.filter;

import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class TiposEmpresasFilter {
  @Size(max = 50)
  private String descricao;

  @Size(max = 22)
  private Long tipoEstabelecimento;

  @Size(max = 22)
  private Long usuario;
}
