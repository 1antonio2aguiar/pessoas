import { BaseResourceModel } from './base-resource.model';

export class PessoasOrigem extends BaseResourceModel {
  constructor(public dataRegistro?: Date,
    public fisicaJuridica?: string,
    public id?: number,
    public nome?: string,
    public observacao?: string,
    public situacao?: string,
    public tipoPessoa?: number,
    public usuario?: string,)
    {
      super()
    }

    static fromJson(jsonData: any): PessoasOrigem {

      return Object.assign(new PessoasOrigem(), jsonData);

    }
}
