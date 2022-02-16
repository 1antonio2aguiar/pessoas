import { Component, OnInit } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { HttpParams } from '@angular/common/http';

import { PessoasJuridica } from 'src/app/shared/models/pessoas-juridica';
import { PessoasJuridicaFiltro } from './pessoas-juridica-filtro';
import { PessoasJuridicaService } from './../pessoas-juridica.service';

@Component({
  selector: 'app-pessoas-juridica-pesquisa',
  templateUrl: './pessoas-juridica-pesquisa.component.html',
  styleUrls: ['./pessoas-juridica-pesquisa.component.css']
})
export class PessoasJuridicaPesquisaComponent extends BaseResourceListComponent<PessoasJuridica> {

  filtro = new PessoasJuridicaFiltro();
  resources = [];
  loading = true;

  constructor(
		private pessoasJuridicaService: PessoasJuridicaService,
		public confirmationService: ConfirmationService,
		public messageService: MessageService
	 ) {
    super(pessoasJuridicaService, confirmationService, messageService, );
  }

  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;
    this.pessoasJuridicaService.pesquisar(this.filtro)
      .then(resultado => {
        this.loading = false;
        this.filtro.totalRegistros = resultado.total;
        this.resources = resultado.pessoasJuridica;
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
      this.filtro.params = this.filtro.params.append('dadosPjFilter.cnpj', event.filters.cpfCnpj.value);
    }

    if (event.filters.nomeFantasia) {
      this.filtro.params = this.filtro.params.append('dadosPjFilter.nomeFantasia', event.filters.nomeFantasia.value);
    }

    this.pesquisar(pagina);
  }

  deleteResource(resource: PessoasJuridica) {
    this.confirmationService.confirm({
      accept: () => {
        this.delete(resource, this.deleteSucess, this.deleteFail);
      },
      reject: () => {

      }
    });
  }

  deleteSucess(messageService: MessageService) {
    console.log('deletado');
    messageService.add({ severity: 'success', summary: 'Successo', detail: 'Deletado Com Sucesso!' });
    this.pesquisar(0);
  }

  deleteFail(error: any, messageService: MessageService) {
    console.log('error');
    console.log(error.error[0].mensagemUsuario);
    messageService.add({ severity: 'error', summary: 'Erro', detail: error.error[0].mensagemUsuario });
    this.pesquisar(0);
  }

}
