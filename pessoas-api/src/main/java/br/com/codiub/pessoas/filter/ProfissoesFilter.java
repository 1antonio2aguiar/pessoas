package br.com.codiub.pessoas.filter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfissoesFilter {
  @NotNull
  @Size(min = 0, max = 22)
  private Long id;

  @NotNull
  @Size(min = 0, max = 255)
  private String nome;
}
