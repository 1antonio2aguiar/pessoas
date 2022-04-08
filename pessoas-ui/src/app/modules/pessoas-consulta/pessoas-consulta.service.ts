import { Injectable, Injector, EventEmitter } from '@angular/core';
 import { BaseResourceService } from './../../shared/services/base-resource.service';
 import { environment } from 'src/environments/environment';
 import { HttpHeaders } from '@angular/common/http';

 import { Router } from '@angular/router';
 import { HttpClientModule } from '@angular/common/http';

 import { Pessoas } from './../../shared/models/pessoas';
 import { PessoasFiltro } from '../pessoas/pessoas-pesquisa/pessoas-filtro';
 import PessoasOutput from 'src/app/shared/models/pessoas-outputs';

@Injectable({
  providedIn: 'root'
})

export class PessoasConsultaService extends BaseResourceService<Pessoas>{

  //private pessoasEventHendlerId: EventEmitter<Pessoas>;

  header = new HttpHeaders(
    {
      'Content-Type': 'application/json'
  });

  constructor(
    protected injector: Injector,
    private router: Router
  ) {

    //super(environment.apiUrl + 'pessoas', injector, Pessoas.fromJson);
    super(environment.apiUrl + 'pessoasGeral', injector, Pessoas.fromJson);

    // Pessoas por id
    //this.pessoasEventHendlerId = new EventEmitter<Pessoas>();
  }


  pesquisar(filtro: PessoasFiltro): Promise<any> {
    let params = filtro.params;
    params = params
    .append('page', filtro.pagina.toString())
    .append('size', filtro.itensPorPagina.toString())
    //.append('fisicaJuridica', 'F');

    //console.log("vamos ver se aqui que passa!!", params)

     return this.http.get<any>(
       this.apiPath,
         {params}
     )
     .toPromise()
     .then(response => {
       const pessoas = response.content;
       const resultado = {
         pessoas,
         total: response.totalElements
       };

       return resultado;
     });
  }

  listAll(): Promise<any> {
    return this.http.get<any>( this.apiPath + '/')
      .toPromise()
      .then(response => response.content
    );
  }

  /*buscaPessoa(pessoaId){
    console.log("VAMOS VER SE AQUI ELE VEM!! +++", pessoaId)

    this.http.get(this.apiPath + '/'+pessoaId)
            .subscribe(resultado => console.log(resultado));

    //return this.http.get<Pessoas>(this.apiPath + '/'+pessoaId)
    //.toPromise();

  }*/

  /*pessoasEditSubscribeId(callBack:(pessoas: PessoasOutput) => void){
    this.pessoasEventHendlerId.subscribe(callBack);
  }*/

}
