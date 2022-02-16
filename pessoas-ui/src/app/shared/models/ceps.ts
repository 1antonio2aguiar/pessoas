import { BaseResourceModel } from "./base-resource.model";

//import { Pessoas } from './pessoas';

export class Ceps extends BaseResourceModel {

  constructor(

    public id?: number,
    public cep?: number,
    public numeroIni?: number,
    public numeroFin?: number,
    public identificacao?: number,

  ) {
    super();
  }

  static fromJson(jsonData: any): Ceps {
  return Object.assign(new Ceps(), jsonData)};

  /*static fromJson(jsonData: any): Ceps {
    const ceps = {
      ...jsonData,
      pessoasId: jsonData["pessoas"]["id"],
    };
    return Object.assign(new Ceps(), ceps);
  }*/

  /*static toJson(obj: any): Ceps{
    delete obj['pessoas'];
    return Object.assign(new Ceps(), obj);
  }*/

}
