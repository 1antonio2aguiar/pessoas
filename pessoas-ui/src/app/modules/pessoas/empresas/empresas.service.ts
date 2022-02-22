import { Injectable, Injector, EventEmitter } from '@angular/core';
import { BaseResourceService } from './../../../shared/services/base-resource.service';
import { environment } from 'src/environments/environment';
import { HttpHeaders } from '@angular/common/http';

import { EmpresasPessoas } from 'src/app/shared/models/empresas-pessoas';
import  EmpresasPessoasOutput from 'src/app/shared/models/empresas-pessoas-output';

@Injectable({
  providedIn: 'root'
})

export class EmpresasService extends BaseResourceService<EmpresasPessoas>{

  // Lista de empresas da pessoa o parametro e o id_pessoa
  private empresasEventHendler: EventEmitter<EmpresasPessoas[]>;

  // Uma única empresa busca por id
  private empresasEventHendlerId: EventEmitter<EmpresasPessoas>;

  header = new HttpHeaders(
    {
      'Content-Type': 'application/json'
  });

  constructor(protected injector: Injector) {
    super(environment.apiUrl + 'empresasPessoas', injector, EmpresasPessoas.fromJson);

    // Lista de empresas por pessoa
    this.empresasEventHendler = new EventEmitter<EmpresasPessoas[]>();

    // Empresas por id
    this.empresasEventHendlerId = new EventEmitter<EmpresasPessoas>();

  }

  // Busca lista de empresas da pessoa
  listAll(pessoa): Promise<any> {
    return this.http.get<EmpresasPessoas[]>(this.apiPath + '/findByIdPessoaId?codigo='+pessoa)
      .toPromise()
      .then(response => {
        this.empresasEventHendler.emit(response);
    });
  }

  empresasChangeSubscribe(callBack:(empresas: EmpresasPessoas[]) => void){
    this.empresasEventHendler.subscribe(callBack);
  }

  // EDITAR EMPRESA - Busca empresa por ID
  buscaEmpresa(empresaId): Promise<any> {
    return this.http.get<EmpresasPessoas>(this.apiPath + '/'+empresaId)
      .toPromise()
      .then(response => {
        this.empresasEventHendlerId.emit(response);
    });
  }

  empresasEditSubscribeId(callBack:(empresas: EmpresasPessoasOutput) => void){
    this.empresasEventHendlerId.subscribe(callBack);
  }

  createEmpresa(resource): Promise<any> {
    return this.http.post(this.apiPath+'/novo', resource, { headers: this.header })
    .toPromise()
    .then(response => response);
  }

  updateEmpresa(resource): Promise<any> {
    //console.log("ID : " + JSON.parse(resource).id);
    //console.log("Endereco: " + this.apiPath + '/' + JSON.parse(resource).id);
    return this.http.put(this.apiPath + '/' + JSON.parse(resource).id, resource, { headers: this.header })
    .toPromise()
    .then(response => response);
  }

  deleteEmpresa(id): Promise<any>{
    //console.log("ID : " + id);
    return this.http.delete(this.apiPath + '/' + id)
    .toPromise()
    .then(response => response);
  }

}

