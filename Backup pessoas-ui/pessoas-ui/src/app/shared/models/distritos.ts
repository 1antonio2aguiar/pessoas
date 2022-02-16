import { BaseResourceModel } from "./base-resource.model";

import { Cidades } from "./cidades";

export class Distritos extends BaseResourceModel {

  constructor(
    public id?: number,
    public nome?: string,
    public codigo_inep?: number,
    public sigla?: string,
    public usuario?: string,

    public cidadesId?: number,
    public cidade?: Cidades,
  ) {
    super();
  }

  static fromJson(jsonData: any): Distritos {
    const distritos = {
      ...jsonData,
      cidadesId: jsonData["cidades"]["id"],
    };
    return Object.assign(new Distritos(), distritos);
  }

  static toJson(obj: any): Distritos{
    delete obj['cidades'];
    return Object.assign(new Distritos(), obj);
  }

}
