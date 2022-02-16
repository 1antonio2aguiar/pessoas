import { TiposLogradouros } from './tipos-logradouros';
import { BaseResourceModel } from "./base-resource.model";

import { Distritos } from "./distritos";

export class Logradouros extends BaseResourceModel {

  constructor(
    public id?: number,
    public nome?: string,

    public distritosId?: number,
    public distritos?: Distritos,

    public tiposLogradouros?: TiposLogradouros,

  ) {
    super();
  }

  static fromJson(jsonData: any): Logradouros {
    const logradouros = {
      ...jsonData,
      distritosId: jsonData["distritos"]["id"],
    };
    return Object.assign(new Logradouros(), logradouros);
  }

  static toJson(obj: any): Logradouros{
    delete obj['distritos'];
    return Object.assign(new Logradouros(), obj);
  }

}
