 import { Injectable, Injector } from '@angular/core';
 import { Etnias } from './../../shared/models/etnias';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { EtniasFiltro } from './etnias-pesquisa/etnias-filtro';
 import { environment } from 'src/environments/environment';

 @Injectable({
   providedIn: 'root'
 })
 export class EtniasService extends BaseResourceService<Etnias> {

 	constructor(protected injector: Injector) {
 	 super(environment.apiUrl + 'etnias', injector, Etnias.fromJson);
 	}

 	pesquisar(filtro: EtniasFiltro): Promise<any> {
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
        const etnias = response.content;
        const resultado = {
          etnias,
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
