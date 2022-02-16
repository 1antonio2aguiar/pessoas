 import { Injectable, Injector } from '@angular/core';
 import { Situacoes } from './../../shared/models/situacoes';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { SituacoesFiltro } from './situacoes-pesquisa/situacoes-filtro';
 import { environment } from 'src/environments/environment';

 @Injectable({
   providedIn: 'root'
 })
 export class SituacoesService extends BaseResourceService<Situacoes> {

 	constructor(protected injector: Injector) {
 	 super(environment.apiUrl + 'situacoes', injector, Situacoes.fromJson);
 	}

 	pesquisar(filtro: SituacoesFiltro): Promise<any> {
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
        const situacoes = response.content;
        const resultado = {
          situacoes,
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
