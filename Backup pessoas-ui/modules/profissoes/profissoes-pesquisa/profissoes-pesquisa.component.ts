import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { ProfissoesService } from './../profissoes.service';
import { Profissoes} from './../../../shared/models/profissoes';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { ProfissoesFiltro } from './profissoes-filtro';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-profissoes-pesquisa',
  templateUrl: './profissoes-pesquisa.component.html',
  styleUrls: ['./profissoes-pesquisa.component.css']
})
export class ProfissoesPesquisaComponent extends BaseResourceListComponent<Profissoes> {
  filtro = new ProfissoesFiltro();
  resources = [];
  loading = true;
  constructor(
		private profissoesService: ProfissoesService,
		public confirmationService: ConfirmationService,
		public messageService: MessageService
	 ) {
    super(profissoesService, confirmationService, messageService);
  }
  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;
    this.profissoesService.pesquisar(this.filtro)
      .then(resultado => {
        this.loading = false;
        this.filtro.totalRegistros = resultado.total;
        this.resources = resultado.profissoes;
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
    this.pesquisar(pagina);
  }
deleteResource(resource: Profissoes) {
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