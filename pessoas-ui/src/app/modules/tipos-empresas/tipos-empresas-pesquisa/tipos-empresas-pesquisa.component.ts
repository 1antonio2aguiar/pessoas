import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { HttpParams } from '@angular/common/http';

import { TiposEmpresas } from 'src/app/shared/models/tipos-empresas';
import { TiposEmpresasService } from '../tipos-empresas.service';
import { TiposEmpresasFiltro } from './tipos-empresas-filtro';

@Component({
  selector: 'app-tipos-empresas-pesquisa',
  templateUrl: './tipos-empresas-pesquisa.component.html',
  styleUrls: ['./tipos-empresas-pesquisa.component.css']
})

export class TiposEmpresasPesquisaComponent extends BaseResourceListComponent<TiposEmpresas> {
  filtro = new TiposEmpresasFiltro();
  resources = [];
  loading = true;

  constructor(
		private tiposEmpresasService: TiposEmpresasService,
		public confirmationService: ConfirmationService,
		public messageService: MessageService
	 ) {
    super(tiposEmpresasService, confirmationService, messageService);
  }

  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;
    this.tiposEmpresasService.pesquisar(this.filtro)
      .then(resultado => {
        this.loading = false;
        this.filtro.totalRegistros = resultado.total;
        this.resources = resultado.tiposDocumentos;
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
    if (event.filters.descricao) {
      this.filtro.params = this.filtro.params.append('descricao', event.filters.descricao.value);
    }
    if (event.filters.id) {
      this.filtro.params = this.filtro.params.append('id', event.filters.id.value);
    }
    this.pesquisar(pagina);
  }

  deleteResource(resource: TiposEmpresas) {
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
