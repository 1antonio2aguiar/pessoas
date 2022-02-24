import { DadosPjGeral } from './dados-pj-geral';
import { DadosPf } from './dados-pf';
import { TiposPessoas } from './tipos-pessoas';


export default interface PessoasOutput {

  id?: number,
  nome?: string,
  fisicaJuridica?: string,
  dataRegistro?: Date,
  observacao?: string,
  situacao?: string,
  usuario?: string,

  tiposPessoas: TiposPessoas,

  dadosPfGeral: DadosPf,
  dadosPjGeral: DadosPjGeral,


}
