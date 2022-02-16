import { Component, OnInit } from '@angular/core';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng';
import { LazyLoadEvent } from 'primeng/api';
import { HttpParams } from '@angular/common/http';

import { Bairros } from './../../../shared/models/bairros';
import { BairrosModalFiltro } from './bairros-modal-filtro';
import { BairrosService } from '../bairros-modal.service';

@Component({
  selector: 'app-bairros-modal',
  templateUrl:'./bairros-modal.component.html'
})

export class BairrosModalComponent extends BaseResourceListComponent<Bairros> {

  filtro = new BairrosModalFiltro();
  resources = [];
  loading = true;

  // parte de filtrar bairros do distrito
  idDistrito;

  constructor(
    private bairrosService: BairrosService,
    public confirmationService: ConfirmationService,
    public messageService: MessageService,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) {

    super(bairrosService, confirmationService, messageService);

    // parte de filtrar bairros do distrito
    this.idDistrito = config.data.idDistrito;

  }

  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;

    // parte de filtrar bairros do distrito
    this.filtro.params = this.filtro.params.append('distritosFilter.id',this.idDistrito);

    this.bairrosService.pesquisar(this.filtro)
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

    this.pesquisar(pagina);
  }

  selecItem(bairros){
    this.ref.close(bairros);
  }

}
