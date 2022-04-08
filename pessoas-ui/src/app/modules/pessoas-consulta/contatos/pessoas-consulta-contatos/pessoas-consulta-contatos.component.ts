import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { Component, ViewChild, Input, EventEmitter } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { DialogService, DynamicDialogRef } from 'primeng';
import { BaseResourceListComponent } from 'src/app/shared/components/base-resource-list/base-resource-list.component';

import { environment } from 'src/environments/environment';

import { Contatos } from 'src/app/shared/models/contatos';
import { ContatosService } from 'src/app/modules/pessoas-juridica/contatos/contatos.service';

@Component({
  selector: 'app-pessoas-consulta-contatos',
  templateUrl: './pessoas-consulta-contatos.component.html',
  styleUrls: ['./pessoas-consulta-contatos.component.css']
})
export class PessoasConsultaContatosComponent extends BaseResourceListComponent<Contatos> {

  // Retorna os dados da lista de contatos aqui.
  ngOnInit(){
    //console.log("AI OOOOO ESTA AQUI PARA RECUPERAR LISTA ")
    this.loadListEnderecos();
  }

  constructor(
		private contatosService: ContatosService,
		public confirmationService: ConfirmationService,
		public messageService: MessageService,
    public dialogService: DialogService ,

	 ) {
    super(contatosService, confirmationService, messageService);
  }

  // Retorna lista de enderecos e rederiza HTML da lista
  loadListEnderecos(){
    this.contatosService.contatosChangeSubscribe(
      resources => {
        this.resources = resources;
      }
    )
  }



}
