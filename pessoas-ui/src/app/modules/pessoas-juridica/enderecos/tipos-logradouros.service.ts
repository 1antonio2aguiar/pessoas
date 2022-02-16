
import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from './../../../shared/services/base-resource.service';
import { environment } from 'src/environments/environment';
import { HttpHeaders } from '@angular/common/http';

import { TiposLogradouros } from './../../../shared/models/tipos-logradouros';

@Injectable({
  providedIn: 'root'
})
export class TiposLogradourosService extends BaseResourceService<TiposLogradouros>{

  header = new HttpHeaders(
    {
      'Content-Type': 'application/json'
  });

  constructor(protected injector: Injector) {
    super(environment.apiUrl + 'tiposLogradouros', injector, TiposLogradouros.fromJson);
  }

  listAll(): Promise<any> {
    return this.http.get<any>(this.apiPath + '/list')
      .toPromise()
      .then(response => response);
  }

}
