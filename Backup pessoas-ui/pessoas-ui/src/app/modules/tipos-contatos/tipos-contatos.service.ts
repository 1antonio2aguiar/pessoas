 import { Injectable, Injector } from '@angular/core';
 import { TiposContatos } from './../../shared/models/tipos-contatos';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { TiposContatosFiltro } from './tipos-contatos-pesquisa/tipos-contatos-filtro';
 import { environment } from 'src/environments/environment';

 @Injectable({
   providedIn: 'root'
 })
 export class TiposContatosService extends BaseResourceService<TiposContatos> {

 	constructor(protected injector: Injector) {
 	 super(environment.apiUrl + 'tiposContatos', injector, TiposContatos.fromJson);
 	}

 	pesquisar(filtro: TiposContatosFiltro): Promise<any> {
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
        const tiposContatos = response.content;
        const resultado = {
          tiposContatos,
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
