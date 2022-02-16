import { Injectable, Injector } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { BaseResourceService } from './../../shared/services/base-resource.service';

import { Ceps } from 'src/app/shared/models/ceps';
import { CepsModalFiltro } from './ceps-modal/ceps-modal-filtro';

@Injectable({
  providedIn: 'root'
})

export class CepsModalService extends BaseResourceService<Ceps>{

  header = new HttpHeaders(
    {
      'Content-Type': 'application/json'
  });

  constructor(
    protected injector: Injector
  ) {
    super(environment.apiUrlEnderecos + 'ceps', injector, Ceps.fromJson);
  }

  pesquisar(filtro: CepsModalFiltro): Promise<any> {

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
