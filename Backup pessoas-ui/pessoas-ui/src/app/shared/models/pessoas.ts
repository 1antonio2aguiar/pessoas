import { BaseResourceModel } from './base-resource.model';

export class Pessoas extends BaseResourceModel {
constructor(
public dataRegistro?: Date,
public email?: string,
public fisicaJuridica?: string,
public id?: number,
public nome?: string,
public observacao?: string,
public situacao?: string,
public tipoPessoa?: number,
public usuario?: string,
) {
super();
}
static fromJson(jsonData: any): Pessoas {
    return Object.assign(new Pessoas(), jsonData);
}
}
