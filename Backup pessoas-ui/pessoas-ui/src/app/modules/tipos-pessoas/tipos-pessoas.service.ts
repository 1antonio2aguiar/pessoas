 import { Injectable, Injector } from '@angular/core';
 import { TiposPessoas } from './../../shared/models/tipos-pessoas';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { TiposPessoasFiltro } from './tipos-pessoas-pesquisa/tipos-pessoas-filtro';
 import { environment } from 'src/environments/environment';

 @Injectable({
   providedIn: 'root'
 })
 export class TiposPessoasService extends BaseResourceService<TiposPessoas> {

 	constructor(protected injector: Injector) {
 	 super(environment.apiUrl + 'tiposPessoas', injector, TiposPessoas.fromJson);
 	}

 	pesquisar(filtro: TiposPessoasFiltro): Promise<any> {
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
        const tiposPessoas = response.content;
        const resultado = {
          tiposPessoas,
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
