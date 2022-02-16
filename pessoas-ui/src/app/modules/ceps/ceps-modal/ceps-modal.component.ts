import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng';
import { LazyLoadEvent } from 'primeng/api';
import { HttpParams } from '@angular/common/http';
import { BaseResourceListComponent } from 'src/app/shared/components/base-resource-list/base-resource-list.component';

import { Ceps } from 'src/app/shared/models/ceps';
import { CepsModalFiltro } from './ceps-modal-filtro';
import { CepsModalService } from './../ceps-modal.service';

@Component({
  selector: 'app-ceps-modal',
  templateUrl: './ceps-modal.component.html',
  styleUrls: ['./ceps-modal.component.css'],
})

export class CepsModalComponent extends BaseResourceListComponent<Ceps> {

  filtro = new CepsModalFiltro;
  resources = [];
  loading = true;

  constructor(

    private cepsModalService: CepsModalService,
    public confirmationService: ConfirmationService,
    public messageService: MessageService,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig

  ) {
    super(cepsModalService, confirmationService, messageService);
  }

  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;

    //console.log("ESTA NO PESQUISAR ceps-modal.components ")

    this.cepsModalService.pesquisar(this.filtro)
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

    if (event.filters.cep) {
      this.filtro.params = this.filtro.params.append('cep', event.filters.cep.value);
    }

    if (event.filters.logradouro) {
      this.filtro.params = this.filtro.params.append('logradourosFilter.nome', event.filters.logradouro.value);
    }

    if (event.filters.bairro) {
      this.filtro.params = this.filtro.params.append('bairrosFilter.nome', event.filters.bairro.value);
    }

    if (event.filters.cidade) {
      this.filtro.params = this.filtro.params.append('bairrosFilter.distritosFilter.cidadesFilter.nome', event.filters.cidade.value);
    }

    this.pesquisar(pagina);
  }

  selecItem(ceps){
    this.ref.close(ceps);
  }

}
