import PessoasOutput from './pessoas-outputs';
import { TiposVinculosEmpresas } from './tipos-vinculos-empresas';

export default interface EmpresasPessoasOutput {

  id?: number,
  participacao?: number,

  pessoasEmpresas: PessoasOutput,
  pessoasPessoas: PessoasOutput,

  tiposVinculosEmpresas: TiposVinculosEmpresas,


}
