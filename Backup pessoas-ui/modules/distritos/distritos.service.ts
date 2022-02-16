
import { DistritosFiltro } from './distritos-modal/DistritosFiltro';
import { Distritos } from './../../shared/models/distritos';

import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from './../../shared/services/base-resource.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DistritosService extends BaseResourceService<Distritos> {

  constructor(protected injector: Injector) {
    super(environment.apiUrl + 'distritos', injector, Distritos.fromJson);
  }

  pesquisar(filtro: DistritosFiltro): Promise<any> {
    let params = filtro.params;
    params = params
      .append('page', filtro.pagina.toString())
      .append('size', filtro.itensPorPagina.toString());
    return this.http.get<any>(
      this.apiPath,
      { params }
    )
      .toPromise()
      .then(response => {
        const distritos = response.content;
        const resultado = {
          distritos,
          total: response.totalElements
        };
        return resultado;
      });
  }

  listAll(): Promise<any> {
    return this.http.get<any>(this.apiPath + '/')
      .toPromise()
      .then(response => response.content);
  }

}
