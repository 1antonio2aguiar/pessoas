import { BaseResourceModel } from './base-resource.model';

export class DadosPf extends BaseResourceModel {
  constructor(

    public id?: number,
    public cpf?: number,

  ) {
    super();
  }
  static fromJson(jsonData: any): DadosPf {
    return Object.assign(new DadosPf(), jsonData);
  }
}


