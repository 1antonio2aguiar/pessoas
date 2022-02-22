import { BaseResourceModel } from './base-resource.model';

export class DadosPjGeral extends BaseResourceModel {
  constructor(

    public id?: number,
    public cnpj?: number,

  ) {
    super();
  }

  static fromJson(jsonData: any): DadosPjGeral {
    return Object.assign(new DadosPjGeral(), jsonData);
  }
}
