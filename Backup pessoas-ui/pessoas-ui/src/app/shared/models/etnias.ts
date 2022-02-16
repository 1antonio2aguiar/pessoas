import { BaseResourceModel } from './base-resource.model';

export class Etnias extends BaseResourceModel {
constructor(
public descricao?: string,
public id?: number,
) {
super();
}
static fromJson(jsonData: any): Etnias {
    return Object.assign(new Etnias(), jsonData);
}
}
