 import { Injectable, Injector } from '@angular/core';
 import { TiposEstadosCivis } from './../../shared/models/tipos-estados-civis';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { TiposEstadosCivisFiltro } from './tipos-estados-civis-pesquisa/tipos-estados-civis-filtro';
 import { environment } from 'src/environments/environment';

 @Injectable({
   providedIn: 'root'
 })
 export class TiposEstadosCivisService extends BaseResourceService<TiposEstadosCivis> {

 	constructor(protected injector: Injector) {
 	 super(environment.apiUrl + 'tiposEstadosCivis', injector, TiposEstadosCivis.fromJson);
 	}

 	pesquisar(filtro: TiposEstadosCivisFiltro): Promise<any> {
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
        const tiposEstadosCivis = response.content;
        const resultado = {
          tiposEstadosCivis,
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
