package br.com.codiub.pessoas.filter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class EtniasFilter {
  @Size(max = 200)
  private String descricao;

  @NotNull
  @Size(min = 0, max = 22)
  private Long id;
}
