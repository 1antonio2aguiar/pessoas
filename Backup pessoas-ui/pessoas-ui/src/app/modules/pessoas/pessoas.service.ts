 import { Injectable, Injector } from '@angular/core';
 import { Pessoas } from './../../shared/models/pessoas';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { PessoasFiltro } from './pessoas-pesquisa/pessoas-filtro';
 import { environment } from 'src/environments/environment';

 @Injectable({
   providedIn: 'root'
 })
 export class PessoasService extends BaseResourceService<Pessoas> {

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

}
