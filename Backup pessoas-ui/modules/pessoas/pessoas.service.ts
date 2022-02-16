 import { Injectable, Injector } from '@angular/core';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { environment } from 'src/environments/environment';
 import { HttpHeaders } from '@angular/common/http';

 import { PessoasFiltro } from './pessoas-pesquisa/pessoas-filtro';
 import { Pessoas } from './../../shared/models/pessoas';


 @Injectable({
  providedIn: 'root'
})

export class PessoasService extends BaseResourceService<Pessoas> {

  header = new HttpHeaders(
    {
      'Content-Type': 'application/json'
  });

  constructor(protected injector: Injector) {
   super(environment.apiUrl + 'pessoasGeral', injector, Pessoas.fromJson);
  }

  pesquisar(filtro: PessoasFiltro): Promise<any> {
   let params = filtro.params;
    params = params
               .append('page', filtro.pagina.toString())
               .append('size', filtro.itensPorPagina.toString());
     return this.http.get<any>(
       this.apiPath,
         {params}
     )
     .toPromise()
     .then(response => {
       const pessoas = response.content;
       const resultado = {
         pessoas,
         total: response.totalElements
       };
       return resultado;
     });
 }

 listAll(): Promise<any> {
    return this.http.get<any>( this.apiPath + '/' )
      .toPromise()
      .then(response => response.content);
 }

  createPessoa(resource): Promise<any> {
    return this.http.post('http://localhost:8080/pessoas/completo', resource, { headers: this.header })
    .toPromise()
    .then(response => response);
    console.log('VOLTOU !!')

  }

  /*updatePessoa(resource): Promise<any> {
    return this.http.put(this.apiPath+'/'+JSON.parse(resource).id, resource, { headers: this.header })
    .toPromise()
    .then(response => response);
  }*/

}
