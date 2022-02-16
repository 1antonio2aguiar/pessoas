import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { TiposVinculosEmpresasService } from './../tipos-vinculos-empresas.service';
import { TiposVinculosEmpresas} from './../../../shared/models/tipos-vinculos-empresas';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { TiposVinculosEmpresasFiltro } from './tipos-vinculos-empresas-filtro';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-tipos-vinculos-empresas-pesquisa',
  templateUrl: './tipos-vinculos-empresas-pesquisa.component.html',
  styleUrls: ['./tipos-vinculos-empresas-pesquisa.component.css']
})
export class TiposVinculosEmpresasPesquisaComponent extends BaseResourceListComponent<TiposVinculosEmpresas> {
  filtro = new TiposVinculosEmpresasFiltro();
  resources = [];
  loading = true;
  constructor(
		private tiposVinculosEmpresasService: TiposVinculosEmpresasService,
		public confirmationService: ConfirmationService,
		public messageService: MessageService
	 ) {
    super(tiposVinculosEmpresasService, confirmationService, messageService);
  }

  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;
    this.tiposVinculosEmpresasService.pesquisar(this.filtro)
      .then(resultado => {
        this.loading = false;
        this.filtro.totalRegistros = resultado.total;
        this.resources = resultado.tiposVinculosEmpresas;
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
    this.pesquisar(pagina);
  }

  deleteResource(resource: TiposVinculosEmpresas) {
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
