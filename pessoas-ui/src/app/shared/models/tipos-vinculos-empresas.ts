import { BaseResourceModel } from './base-resource.model';

export class TiposVinculosEmpresas extends BaseResourceModel {
constructor(
public descricao?: string,
public id?: number,
) {
super();
}
static fromJson(jsonData: any): TiposVinculosEmpresas {
    return Object.assign(new TiposVinculosEmpresas(), jsonData);
}
}
