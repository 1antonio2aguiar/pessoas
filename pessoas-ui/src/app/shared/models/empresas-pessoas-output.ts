import { Pessoas } from './pessoas';
import { TiposVinculosEmpresas } from './tipos-vinculos-empresas';

export default interface EmpresasPessoasOutput {

  id?: number,
  participacao?: number,

  idEmpresa: Pessoas,
  idPessoa: Pessoas,

  tiposVinculosEmpresas: TiposVinculosEmpresas,

}
