import { BaseResourceModel } from './base-resource.model';

export class EmpresasPessoas extends BaseResourceModel {

  constructor(

    public id?: number,
    public vinculo?: number,
    public participacao?: number,
    public idEmpresa?: number,
    public idPessoa?: number,

  ) {
    super();
  }

  static fromJson(jsonData: any): EmpresasPessoas {
    return Object.assign(new EmpresasPessoas(), jsonData);
  }
}
