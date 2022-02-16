package br.com.codiub.pessoas.filter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class SituacoesFilter {
  @NotNull
  @Size(min = 0, max = 100)
  private String descricao;

}
