 import { Injectable, Injector } from '@angular/core';
 import { Profissoes } from './../../shared/models/profissoes';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { ProfissoesFiltro } from './profissoes-pesquisa/profissoes-filtro';
 import { environment } from 'src/environments/environment';

 @Injectable({
   providedIn: 'root'
 })
 export class ProfissoesService extends BaseResourceService<Profissoes> {

 	constructor(protected injector: Injector) {
 	 super(environment.apiUrl + 'profissoes', injector, Profissoes.fromJson);
 	}

 	pesquisar(filtro: ProfissoesFiltro): Promise<any> {
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
        const profissoes = response.content;
        const resultado = {
          profissoes,
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
