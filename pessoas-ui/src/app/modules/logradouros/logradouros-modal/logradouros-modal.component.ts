import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng';
import { LazyLoadEvent } from 'primeng/api';
import { HttpParams } from '@angular/common/http';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';

import { Logradouros } from 'src/app/shared/models/logradouros';
import { LogradourosModalFiltro } from './logradouros-modal-filtro';
import { LogradourosModalService } from '../logradouros-modal.service';

@Component({
  selector: 'app-logradouros-modal',
  templateUrl: './logradouros-modal.component.html',
  styleUrls: ['./logradouros-modal.component.css']
})


export class LogradourosModalComponent extends BaseResourceListComponent<Logradouros> {

  filtro = new LogradourosModalFiltro();
  resources = [];
  loading = true;

  // parte de filtrar bairros do distrito
  idDistrito;

  constructor(
    private logradourosModalService: LogradourosModalService,
    public confirmationService: ConfirmationService,
    public messageService: MessageService,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) {
    super(logradourosModalService, confirmationService, messageService);

    // parte de filtrar bairros do distrito
    this.idDistrito = config.data.idDistrito;
   }

  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;

    // parte de filtrar bairros do distrito
    this.filtro.params = this.filtro.params.append('distritosFilter.id',this.idDistrito);

    this.logradourosModalService.pesquisar(this.filtro)
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

  selecItem(logradouros){
    this.ref.close(logradouros);
  }

}
