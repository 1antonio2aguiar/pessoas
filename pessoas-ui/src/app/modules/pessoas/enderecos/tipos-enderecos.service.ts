import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from './../../../shared/services/base-resource.service';
import { environment } from 'src/environments/environment';
import { HttpHeaders } from '@angular/common/http';

import { TiposEnderecos } from './../../../shared/models/tipos-enderecos';

@Injectable({
  providedIn: 'root'
})

export class TiposEnderecosService extends BaseResourceService<TiposEnderecos>{

  header = new HttpHeaders(
    {
      'Content-Type': 'application/json'
  });

  constructor(protected injector: Injector) {
    super(environment.apiUrl + 'tiposEnderecos', injector, TiposEnderecos.fromJson);
  }

  listAll(): Promise<any> {
    return this.http.get<any>( this.apiPath + '/' )
      .toPromise()
      .then(response => response.content);
  }
}
