import { BaseResourceService } from './../../shared/services/base-resource.service';
import { Injectable, Injector } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpHeaders } from '@angular/common/http';

import { Bairros } from './../../shared/models/bairros';
import { BairrosModalFiltro } from './bairros-modal/bairros-modal-filtro';

@Injectable({
  providedIn: 'root'
})

export class BairrosService extends BaseResourceService<Bairros>{

  header = new HttpHeaders(
    {
      'Content-Type': 'application/json'
  });

  constructor(
    protected injector: Injector
    ) {
    super(environment.apiUrlEnderecos + 'bairros', injector, Bairros.fromJson);
  }

  pesquisar(filtro: BairrosModalFiltro): Promise<any> {
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
        const bairros = response.content;
        const resultado = {
          bairros,
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
