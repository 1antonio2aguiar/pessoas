import { BaseResourceModel } from './base-resource.model';

export class Profissoes extends BaseResourceModel {
constructor(
public id?: number,
public nome?: string,
) {
super();
}
static fromJson(jsonData: any): Profissoes {
    return Object.assign(new Profissoes(), jsonData);
}
}
