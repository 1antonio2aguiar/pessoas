 import { Injectable, Injector } from '@angular/core';
 import { TiposVinculosEmpresas } from './../../shared/models/tipos-vinculos-empresas';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { TiposVinculosEmpresasFiltro } from './tipos-vinculos-empresas-pesquisa/tipos-vinculos-empresas-filtro';
 import { environment } from 'src/environments/environment';

 @Injectable({
   providedIn: 'root'
 })
 export class TiposVinculosEmpresasService extends BaseResourceService<TiposVinculosEmpresas> {

 	constructor(protected injector: Injector) {
 	 super(environment.apiUrl + 'tiposVinculosEmpresas', injector, TiposVinculosEmpresas.fromJson);
 	}

 	pesquisar(filtro: TiposVinculosEmpresasFiltro): Promise<any> {
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
        const tiposVinculosEmpresas = response.content;
        const resultado = {
          tiposVinculosEmpresas,
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
