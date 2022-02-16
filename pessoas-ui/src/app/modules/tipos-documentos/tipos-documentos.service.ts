 import { Injectable, Injector } from '@angular/core';
 import { TiposDocumentos } from './../../shared/models/tipos-documentos';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { TiposDocumentosFiltro } from './tipos-documentos-pesquisa/tipos-documentos-filtro';
 import { environment } from 'src/environments/environment';

 @Injectable({
   providedIn: 'root'
 })

 export class TiposDocumentosService extends BaseResourceService<TiposDocumentos> {

 	constructor(protected injector: Injector) {
 	 super(environment.apiUrl + 'tiposDocumentos', injector, TiposDocumentos.fromJson);
 	}

 	pesquisar(filtro: TiposDocumentosFiltro): Promise<any> {
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
        const tiposDocumentos = response.content;
        const resultado = {
          tiposDocumentos,
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
