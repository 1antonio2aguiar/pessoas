
import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from './../../shared/services/base-resource.service';
import { TiposEmpresas } from './../../shared/models/tipos-empresas';
import { TiposEmpresasFiltro } from './tipos-empresas-pesquisa/tipos-empresas-filtro';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})

export class TiposEmpresasService extends BaseResourceService<TiposEmpresas> {

  constructor(protected injector: Injector) {
   super(environment.apiUrl + 'tiposEmpresas', injector, TiposEmpresas.fromJson);
  }

  pesquisar(filtro: TiposEmpresasFiltro): Promise<any> {
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
       const tiposEmpresas = response.content;
       const resultado = {
         tiposEmpresas,
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
