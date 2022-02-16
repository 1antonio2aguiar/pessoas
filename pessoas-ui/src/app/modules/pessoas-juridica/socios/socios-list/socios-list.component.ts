import { ConfirmationService, MessageService } from 'primeng/api';
import { Component } from '@angular/core';
import { DialogService } from 'primeng';
import { BaseResourceListComponent } from '../../../../shared/components/base-resource-list/base-resource-list.component';

import { environment } from 'src/environments/environment';

import { EmpresasPessoas } from 'src/app/shared/models/empresas-pessoas';
import { SociosService } from '../socios.service';
import { SociosModalComponent } from '../socios-modal/socios-modal.component';

@Component({
  selector: 'app-socios-list',
  templateUrl: './socios-list.component.html',
  styleUrls: ['./socios-list.component.css']
})

export class SociosListComponent extends BaseResourceListComponent<EmpresasPessoas> {

  modalVisible = false;
  env = environment;

  // Retorna os dados da lista de socios aqui.
  ngOnInit(){
    //console.log("AI OOOOO ESTA AQUI PARA RECUPERAR LISTA ")
    this.loadListSocios();
  }

  constructor(
		private sociosService: SociosService,
		public confirmationService: ConfirmationService,
		public messageService: MessageService,
    public dialogService: DialogService ,

	 ) {
    super(sociosService, confirmationService, messageService);
  }

  // Abre Modal socios - novo socio
  showSociosModalNew($event) {
    this.env.currentActionGlobal = "NEW";
    this.modalVisible = true;
    const ref = this.dialogService.open(SociosModalComponent, {
      header: 'Informe os Sócios ' , width: '70%'
    });

    ref.onClose.subscribe( () => {
      //ATUALIZA A LISTA DE SOCIOS
      this.sociosService.listAll((<HTMLSelectElement>document.getElementById('id')).value)
    });
  }

  // Abre Modal socios - edita socio é o mesmo HTML, mas aqui vem com parametro id do socio
  showsociosModalEdit(id) {
    this.env.currentActionGlobal = "EDIT";
    this.modalVisible = true;
    const ref = this.dialogService.open(SociosModalComponent, {
      header: 'Editando Sócios ' , width: '70%'
    });

    this.sociosService.buscaSocio(id);

    ref.onClose.subscribe( () => {
      //ATUALIZA A LISTA DE SOCIOS
      this.sociosService.listAll((<HTMLSelectElement>document.getElementById('id')).value)
    });
  }

  showSociosModalDelete(id){
    this.env.currentActionGlobal = "DELETE";
    this.modalVisible = true;
    this.env.botaoOnOf = false;

    const ref = this.dialogService.open(SociosModalComponent, {
      header: 'CONFIRMA DELETAR SÓCIO? ' , width: '70%',
      //styleClass:
      contentStyle: {"background-color": "red", "font-size": "3px"},
      //      style: {"background-color": "blue"},

    });

    this.sociosService.buscaSocio(id);

    ref.onClose.subscribe( () => {
      //ATUALIZA A LISTA DE SOCIOS
      this.sociosService.listAll((<HTMLSelectElement>document.getElementById('id')).value)
    });

  }

  // Retorna lista de socios e rederiza HTML da lista
  loadListSocios(){
    //console.log("1 - ATUALZANDO foi no loadListSocios " );
    this.sociosService.sociosChangeSubscribe(

      resources => {
        if(resources === null){
          console.log("2  - ATUALZANDO foi no loadListSocios ", resources );
          this.resources = resources;
        } else {
          console.log("Não há Sócios para esta empresa! ");
        }
      }
    )
  }
}
