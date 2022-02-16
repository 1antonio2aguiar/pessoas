import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from './../../shared/services/base-resource.service';
import { HttpHeaders } from '@angular/common/http';

import { PessoasJuridica } from 'src/app/shared/models/pessoas-juridica';
import { PessoasJuridicaFiltro } from './pessoas-juridica-pesquisa/pessoas-juridica-filtro';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})

export class PessoasJuridicaService extends BaseResourceService<PessoasJuridica>{

  header = new HttpHeaders(
    {
      'Content-Type': 'application/json'
  });

  constructor(protected injector: Injector) {

    super(environment.apiUrl + 'pessoasJuridica', injector, PessoasJuridica.fromJson);

  }

  pesquisar(filtro: PessoasJuridicaFiltro): Promise<any> {
    let params = filtro.params;
    params = params
    .append('page', filtro.pagina.toString())
    .append('size', filtro.itensPorPagina.toString())
    .append('fisicaJuridica', 'J');

    //console.log("vamos ver se aqui que passa!!", params)

     return this.http.get<any>(
       this.apiPath,
         {params}
     )
     .toPromise()
     .then(response => {
       const pessoasJuridica = response.content;
       const resultado = {
         pessoasJuridica,
         total: response.totalElements
       };
       return resultado;
     });
  }

  listAll(): Promise<any> {
    return this.http.get<any>( this.apiPath + '/')
      .toPromise()
      .then(response => response.content);
  }

  createPessoaJuridica(resource): Promise<any> {
    return this.http.post(this.apiPath+'/', resource, { headers: this.header })
    .toPromise()
    .then(response => response);
  }

  updatePessoaJuridica(resource): Promise<any> {
    return this.http.put(this.apiPath+'/'+JSON.parse(resource).id, resource, { headers: this.header })
    .toPromise()
    .then(response => response);
  }

}
