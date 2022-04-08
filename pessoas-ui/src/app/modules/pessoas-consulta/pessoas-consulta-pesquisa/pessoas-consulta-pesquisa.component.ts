import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { HttpParams } from '@angular/common/http';

import { PessoasConsultaService } from '../pessoas-consulta.service';
import { PessoasFiltro } from '../../pessoas/pessoas-pesquisa/pessoas-filtro';
import { Pessoas } from 'src/app/shared/models/pessoas';

@Component({
  selector: 'app-pessoas-consulta-pesquisa',
  templateUrl: './pessoas-consulta-pesquisa.component.html',
  styleUrls: ['./pessoas-consulta-pesquisa.component.css']
})

export class PessoasConsultaPesquisaComponent extends BaseResourceListComponent<Pessoas> {

  filtro = new PessoasFiltro();
  resources = [];
  loading = true;

  masks = {
    mask: [
      {
        mask: '00/00/0000'
      }
    ]
  };

  constructor(
    private pessoasConsultaService: PessoasConsultaService,
		public confirmationService: ConfirmationService,
		public messageService: MessageService
  ) {
    super(pessoasConsultaService, confirmationService, messageService, );
  }

   pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;
    this.pessoasConsultaService.pesquisar(this.filtro)
      .then(resultado => {
        this.loading = false;
        this.filtro.totalRegistros = resultado.total;
        this.resources = resultado.pessoas;

        //console.log('Res: ', this.resources )
      })
		 .catch(erro => {
		        	erro = 'Erro';
			    	this.loading = false;
			}
		);
  }

  aoMudarPagina(event: LazyLoadEvent) {
    const pagina = event.first / event.rows;
    this.filtro.params = new HttpParams();

    if (event.filters.id) {
      this.filtro.params = this.filtro.params.append('id', event.filters.id.value);
    }

    if (event.filters.nome) {
      this.filtro.params = this.filtro.params.append('nome', event.filters.nome.value);
    }

    if (event.filters.cpfCnpj) {
      this.filtro.params = this.filtro.params.append('cpfCnpj', event.filters.cpfCnpj.value);
    }

    if (event.filters.dataNascimento) {
      let temp = event.filters.dataNascimento.value.split("/");
      temp = temp[2] + '/' + temp[1] + '/' + temp[0] /*+ ' 00:00:00'*/;
      //console.log("data: " + temp)
      this.filtro.params = this.filtro.params.append('dataRegistro', temp);
    }

    this.pesquisar(pagina);
  }

  /*editarPessoa(pessoaId) {

    this.pessoasConsultaService.buscaPessoa(pessoaId);
  }*/

}
