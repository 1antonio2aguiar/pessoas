import { Injectable, Injector, EventEmitter } from '@angular/core';
import { BaseResourceService } from './../../../shared/services/base-resource.service';
import { environment } from 'src/environments/environment';
import { HttpHeaders } from '@angular/common/http';

import { EmpresasPessoas } from 'src/app/shared/models/empresas-pessoas';
import EmpresasPessoasOutput from 'src/app/shared/models/empresas-pessoas-output';

import { Enderecos } from './../../../shared/models/enderecos';

@Injectable({
  providedIn: 'root'
})

export class SociosService extends BaseResourceService<EmpresasPessoas>{

  private sociosEventHendler: EventEmitter<EmpresasPessoas[]>;
  private sociosEventHendlerId: EventEmitter<EmpresasPessoas>;

  header = new HttpHeaders(
    {
      'Content-Type': 'application/json'
  });

  constructor(protected injector: Injector) {

    super(environment.apiUrl + 'empresasPessoas', injector, EmpresasPessoas.fromJson);

    // Lista de socios por empresa
    this.sociosEventHendler = new EventEmitter<EmpresasPessoas[]>();

    // Recupera apenas um socio por id
    this.sociosEventHendlerId = new EventEmitter<EmpresasPessoas>();
  }

  // Busca lista de socios da empresa
  listAll(id): Promise<any> {

    //console.log(environment.apiUrl + 'pessoasGeral')

    return this.http.get<EmpresasPessoas[]>(this.apiPath + '/findByIdEmpresaId?codigo='+id)
      .toPromise()
      .then(response => {
      this.sociosEventHendler.emit(response);
    });
  }

  sociosChangeSubscribe(callBack:(socios: EmpresasPessoas[]) => void){
    this.sociosEventHendler.subscribe(callBack);
  }

  // Busca socio por ID
  buscaSocio(id): Promise<any> {
    return this.http.get<EmpresasPessoas>(this.apiPath + '/'+id)
      .toPromise()
      .then(response => {
        this.sociosEventHendlerId.emit(response);
    });
  }

  buscaSocioId(id): Promise<any> {
    //console.log("VAMOS VER SE AQUI ELE VEM!! ", id)
    return this.http.get<EmpresasPessoas>(this.apiPath + '/'+id)
      .toPromise();
  }

  sociosEditSubscribeId(callBack:(socios: EmpresasPessoasOutput) => void){
    this.sociosEventHendlerId.subscribe(callBack);
  }

  createSocio(resource): Promise<any> {
    return this.http.post(this.apiPath+'/novo', resource, { headers: this.header })
    .toPromise()
    .then(response => response);
  }

  updateSocio(resource): Promise<any> {
    return this.http.put(this.apiPath + '/' + JSON.parse(resource).id, resource, { headers: this.header })
    .toPromise()
    .then(response => response);
  }

  deleteSocio(id): Promise<any>{
    return this.http.delete(this.apiPath + '/' + id)
    .toPromise()
    .then(response => response);
  }

}
