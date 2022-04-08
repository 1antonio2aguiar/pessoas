import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DialogService } from 'primeng';
import { BaseResourceListComponent } from '../../../../shared/components/base-resource-list/base-resource-list.component';

import { Enderecos } from 'src/app/shared/models/enderecos';
import { EnderecosService } from 'src/app/modules/pessoas-juridica/enderecos/enderecos.service';

@Component({
  selector: 'app-pessoas-consulta-enderecos',
  templateUrl: './pessoas-consulta-enderecos.component.html',
  styleUrls: ['./pessoas-consulta-enderecos.component.css']
})
export class PessoasConsultaEnderecosComponent extends BaseResourceListComponent<Enderecos> {

  // Retorna os dados da lista de enderecos aqui.
  ngOnInit(){
    //console.log("AI OOOOO ESTA AQUI PARA RECUPERAR LISTA ")
    this.loadListEnderecos();
  }

  constructor(
		private enderecosService: EnderecosService,
		public confirmationService: ConfirmationService,
		public messageService: MessageService,
    public dialogService: DialogService ,

	 ) {
    super(enderecosService, confirmationService, messageService);
  }

  // Retorna lista de enderecos e rederiza HTML da lista
  loadListEnderecos(){
    //console.log("1 - ATUALZANDO foi no loadListEnderecos " );
    this.enderecosService.enderecosChangeSubscribe(
      resources => {
        //console.log("2  - ATUALZANDO foi no loadListEnderecos " );

        //Move os dados da pessoas para o modal de cadastro de enderecos
        this.resources = resources;

      }
    )
  }
}
