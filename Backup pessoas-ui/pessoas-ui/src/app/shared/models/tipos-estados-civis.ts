import { BaseResourceModel } from './base-resource.model';

export class TiposEstadosCivis extends BaseResourceModel {
constructor(
public descricao?: string,
public id?: number,
public sigla?: string,
) {
super();
}
static fromJson(jsonData: any): TiposEstadosCivis {
    return Object.assign(new TiposEstadosCivis(), jsonData);
}
}
