import { ConfirmationService, MessageService } from 'primeng/api';
import { LazyLoadEvent } from 'primeng/api';
import { Component, ViewChild, Input, EventEmitter } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { DialogService, DynamicDialogRef } from 'primeng';
import { BaseResourceListComponent } from 'src/app/shared/components/base-resource-list/base-resource-list.component';

import { Documentos } from 'src/app/shared/models/documentos';
import { DocumentosService } from 'src/app/modules/pessoas-juridica/documentos/documentos.service';

@Component({
  selector: 'app-pessoas-consulta-documentos',
  templateUrl: './pessoas-consulta-documentos.component.html',
  styleUrls: ['./pessoas-consulta-documentos.component.css']
})
export class PessoasConsultaDocumentosComponent extends BaseResourceListComponent<Documentos> {

  // Retorna os dados da lista de documentos aqui.
  ngOnInit(){
    //console.log("AI OOOOO ESTA AQUI PARA RECUPERAR LISTA ")
    this.loadListDocumentos();
  }

  constructor(
		private documentosService: DocumentosService,
		public confirmationService: ConfirmationService,
		public messageService: MessageService,
    public dialogService: DialogService ,

	 ) {
    super(documentosService, confirmationService, messageService);
  }

  // Retorna lista de documentos e rederiza HTML da lista
  loadListDocumentos(){
    this.documentosService.documentosChangeSubscribe(
      resources => {
        this.resources = resources;
      }
    )
  }

}
