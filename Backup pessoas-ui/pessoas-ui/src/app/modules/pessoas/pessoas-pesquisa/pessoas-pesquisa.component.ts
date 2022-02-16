import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { PessoasService } from './../pessoas.service';
import { Pessoas} from './../../../shared/models/pessoas';
import { Component, ViewChild } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { PessoasFiltro } from './pessoas-filtro';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-pessoas-pesquisa',
  templateUrl: './pessoas-pesquisa.component.html',
  styleUrls: ['./pessoas-pesquisa.component.css']
})

export class PessoasPesquisaComponent extends BaseResourceListComponent<Pessoas> {
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
		private pessoasService: PessoasService,
		public confirmationService: ConfirmationService,
		public messageService: MessageService
	 ) {
    super(pessoasService, confirmationService, messageService);
  }

  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;
    this.pessoasService.pesquisar(this.filtro)
      .then(resultado => {
        this.loading = false;
        this.filtro.totalRegistros = resultado.total;
        this.resources = resultado.pessoas;
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

    if (event.filters.data_registro) {
      this.filtro.params = this.filtro.params.append('data_registro', event.filters.data_registro.value);
    }
    if (event.filters.id) {
      this.filtro.params = this.filtro.params.append('id', event.filters.id.value);
    }
    if (event.filters.nome) {
      this.filtro.params = this.filtro.params.append('nome', event.filters.nome.value);
    }

    if (event.filters.cpfCnpj) {
      this.filtro.params = this.filtro.params.append('dadosPfFilter.cpf', event.filters.cpfCnpj.value);
      //this.filtro.params = this.filtro.params.append('dadosPjFilter.cnpj', event.filters.cpfCnpj.value);
    }

    if (event.filters.nomeMae) {
      this.filtro.params = this.filtro.params.append('dadosPfFilter.mae', event.filters.nomeMae.value);
    }

    if (event.filters.dataNascimento) {
      let temp = event.filters.dataNascimento.value.split("/");
      temp = temp[2] + '/' + temp[1] + '/' + temp[0] /*+ ' 00:00:00'*/;
      //console.log("data: " + temp)
      this.filtro.params = this.filtro.params.append('dataRegistro', temp);
    }

    this.pesquisar(pagina);
  }

  deleteResource(resource: Pessoas) {
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
