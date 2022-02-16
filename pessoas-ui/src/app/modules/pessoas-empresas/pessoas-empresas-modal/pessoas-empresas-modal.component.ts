import { Pessoas } from './../../../shared/models/pessoas';
import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng';
import { LazyLoadEvent } from 'primeng/api';
import { HttpParams } from '@angular/common/http';
import { BaseResourceListComponent } from 'src/app/shared/components/base-resource-list/base-resource-list.component';

import { EmpresasPessoas } from 'src/app/shared/models/empresas-pessoas';
import { PessoasEmpresasModalFiltro } from './pessoas-empresas-modal-filtro';
import { PessoasEmpresasService } from '../pessoas-empresas-modal.service';

@Component({
  selector: 'app-pessoas-empresas-modal',
  templateUrl: './pessoas-empresas-modal.component.html',
  styleUrls: ['./pessoas-empresas-modal.component.css']
})

export class PessoasEmpresasModalComponent extends BaseResourceListComponent<EmpresasPessoas> {

  filtro = new PessoasEmpresasModalFiltro;
  resources = [];
  loading = true;

  constructor(

    private pessoasEmpresasService: PessoasEmpresasService,
    public confirmationService: ConfirmationService,
    public messageService: MessageService,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig

  ) {
    super(pessoasEmpresasService, confirmationService, messageService);
  }

  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;

    //console.log("ESTA NO PESQUISAR pessoas-empresas-modal.components ")

    this.pessoasEmpresasService.pesquisar(this.filtro)
      .then(resultado => {
        this.loading = false;
        this.filtro.totalRegistros = resultado.total;
        this.resources = resultado.bairros;
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

    this.pesquisar(pagina);

  }

  selecItem(pessoas){
    this.ref.close(pessoas);
  }

}

