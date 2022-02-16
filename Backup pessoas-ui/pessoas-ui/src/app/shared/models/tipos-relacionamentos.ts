import { BaseResourceModel } from './base-resource.model';

export class TiposRelacionamentos extends BaseResourceModel {
constructor(
public descricao?: string,
public id?: number,
) {
super();
}
static fromJson(jsonData: any): TiposRelacionamentos {
    return Object.assign(new TiposRelacionamentos(), jsonData);
}
}
