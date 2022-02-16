import { BaseResourceModel } from "./base-resource.model";

import { Distritos } from "./distritos";

export class Bairros extends BaseResourceModel {

  constructor(
    public id?: number,
    public nome?: string,

    public distritosId?: number,
    public distritos?: Distritos,

  ) {
    super();
  }

  static fromJson(jsonData: any): Bairros {
    const bairros = {
      ...jsonData,
      distritosId: jsonData["distritos"]["id"],
    };
    return Object.assign(new Bairros(), bairros);
  }

  static toJson(obj: any): Bairros{
    delete obj['distritos'];
    return Object.assign(new Bairros(), obj);
  }

}
