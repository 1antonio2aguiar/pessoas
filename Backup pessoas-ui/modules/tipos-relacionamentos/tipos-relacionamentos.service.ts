 import { Injectable, Injector } from '@angular/core';
 import { TiposRelacionamentos } from './../../shared/models/tipos-relacionamentos';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { TiposRelacionamentosFiltro } from './tipos-relacionamentos-pesquisa/tipos-relacionamentos-filtro';
 import { environment } from 'src/environments/environment';

 @Injectable({
   providedIn: 'root'
 })
 export class TiposRelacionamentosService extends BaseResourceService<TiposRelacionamentos> {

 	constructor(protected injector: Injector) {
 	 super(environment.apiUrl + 'tiposRelacionamentos', injector, TiposRelacionamentos.fromJson);
 	}

 	pesquisar(filtro: TiposRelacionamentosFiltro): Promise<any> {
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
        const tiposRelacionamentos = response.content;
        const resultado = {
          tiposRelacionamentos,
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
