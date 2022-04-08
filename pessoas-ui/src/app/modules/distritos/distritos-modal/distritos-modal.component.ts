import { DistritosFiltro } from './DistritosFiltro';
import { Distritos } from './../../../shared/models/distritos';
import { DistritosService } from './../../distritos/distritos.service';

import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { BaseResourceListComponent } from '../../../shared/components/base-resource-list/base-resource-list.component';
import { HttpParams } from '@angular/common/http';
import { DynamicDialogRef } from 'primeng';

@Component({
  selector: 'app-distritos-modal',
  templateUrl: './distritos-modal.component.html'
})

export class DistritosModalComponent extends BaseResourceListComponent<Distritos> {

  filtro = new DistritosFiltro();
  resources = [];
  loading = true;

  constructor(
    private distritosService: DistritosService,
    public confirmationService: ConfirmationService,
    public messageService: MessageService,
    public ref: DynamicDialogRef
  ) {
    super(distritosService, confirmationService, messageService);
  }

  pesquisar(pagina = 0) {
    this.filtro.pagina = pagina;
    this.distritosService.pesquisar(this.filtro)
      .then(resultado => {
        //console.log(resultado);

        this.loading = false;
        this.filtro.totalRegistros = resultado.total;
        this.resources = resultado.distritos;
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
      this.filtro.params = this.filtro.params.append('cidadesFilter.id', event.filters.id.value);
    }

    if (event.filters.distrito) {
      this.filtro.params = this.filtro.params.append('nome', event.filters.distrito.value);
    }

    if (event.filters.cidade) {
      this.filtro.params = this.filtro.params.append('cidadesFilter.nome', event.filters.cidade.value);
    }

    this.pesquisar(pagina);

  }

  selecItem(distritos){
    this.ref.close(distritos);
  }

}
