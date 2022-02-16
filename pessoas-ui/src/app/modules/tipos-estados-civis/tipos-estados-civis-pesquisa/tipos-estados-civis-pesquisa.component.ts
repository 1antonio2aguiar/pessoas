import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { TiposEstadosCivisService } from './../tipos-estados-civis.service';
import { TiposEstadosCivis} from './../../../shared/models/tipos-estados-civis';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { TiposEstadosCivisFiltro } from './tipos-estados-civis-filtro';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-tipos-estados-civis-pesquisa',
  templateUrl: './tipos-estados-civis-pesquisa.component.html',
  styleUrls: ['./tipos-estados-civis-pesquisa.component.css']
})
export class TiposEstadosCivisPesquisaComponent extends BaseResourceListComponent<TiposEstadosCivis> {
  filtro = new TiposEstadosCivisFiltro();
  resources = [];
  loading = true;
  constructor(
		private tiposEstadosCivisService: TiposEstadosCivisService,
		public confirmationService: ConfirmationService,
		public messageService: MessageService
	 ) {
    super(tiposEstadosCivisService, confirmationService, messageService);
  }

  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;
    this.tiposEstadosCivisService.pesquisar(this.filtro)
      .then(resultado => {
        this.loading = false;
        this.filtro.totalRegistros = resultado.total;
        this.resources = resultado.tiposEstadosCivis;
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

    if (event.filters.descricao) {
      this.filtro.params = this.filtro.params.append('descricao', event.filters.descricao.value);
    }

    if (event.filters.sigla) {
      this.filtro.params = this.filtro.params.append('sigla', event.filters.sigla.value);
    }
    this.pesquisar(pagina);
  }

  deleteResource(resource: TiposEstadosCivis) {
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
