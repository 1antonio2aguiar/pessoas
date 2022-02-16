import { BaseResourceModel } from "./base-resource.model";

import { Estados } from './estados';

export class Cidades extends BaseResourceModel {

  constructor(
    public id?: number,
    public nome?: string,
    public sigla?: string,
    public codigo_ibge?: number,
    public codigo_inep?: number,
    public codigo_sicom?: number,
    public dataAlteracao?: Date,
    public usuario?: string,

    public estadosId?: number,
    public estado?: Estados,
  ) {
    super();
  }

  static fromJson(jsonData: any): Cidades {
    const cidades = {
      ...jsonData,
      estadosId: jsonData["estados"]["id"],
    };
    return Object.assign(new Cidades(), cidades);
  }

  static toJson(obj: any): Cidades{
    delete obj['estados'];
    return Object.assign(new Cidades(), obj);
  }
}
