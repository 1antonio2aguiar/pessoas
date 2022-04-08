import { PessoasOrigem } from './../../../shared/models/pessoas-origem';
import { HttpHeaders } from '@angular/common/http';
import { Injectable, Injector, EventEmitter } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { BaseResourceService } from 'src/app/shared/services/base-resource.service';

@Injectable({
  providedIn: 'root'
})

export class PessoasConsultaOrigemService extends BaseResourceService<PessoasOrigem>{

  header = new HttpHeaders(
    {
      'Content-Type': 'application/json'
  });

  constructor(protected injector: Injector,
  private router: Router)
  {
    super(environment.apiUrl + 'cadUnicoPessoa', injector, PessoasOrigem.fromJson);
  }


  findByPessoasCdUnico(id): Promise<any> {
    return this.http.get<any>( this.apiPath + '/findByPessoasCdUnico'+id)
      .toPromise()
      .then(response => response.content
    );
  }
}
