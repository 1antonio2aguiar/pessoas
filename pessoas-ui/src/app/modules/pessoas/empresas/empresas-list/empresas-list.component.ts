import { ConfirmationService, MessageService } from 'primeng/api';
import { Component } from '@angular/core';
import { DialogService } from 'primeng';
import { BaseResourceListComponent } from '../../../../shared/components/base-resource-list/base-resource-list.component';

import { environment } from 'src/environments/environment';

import { EmpresasService } from '../empresas.service';
import { EmpresasPessoas } from 'src/app/shared/models/empresas-pessoas';
import { EmpresasModalComponent } from '../empresas-modal/empresas-modal.component';

@Component({
  selector: 'app-empresas-list',
  templateUrl: './empresas-list.component.html',
  styleUrls: ['./empresas-list.component.css']
})
export class EmpresasListComponent extends BaseResourceListComponent<EmpresasPessoas> {

  modalVisible = false;
  env = environment;

  // Retorna os dados da lista de empresas aqui.
  ngOnInit(){
    //console.log("AI OOOOO ESTA AQUI PARA RECUPERAR LISTA ")
    this.loadListEmpresas();
  }

  constructor(
		private empresasService: EmpresasService,
		public confirmationService: ConfirmationService,
		public messageService: MessageService,
    public dialogService: DialogService ,

	 ) {
    super(empresasService, confirmationService, messageService);
  }

  // Abre Modal empresas - nova empresa
  showEmpresasModalNew($event) {

    this.env.currentActionGlobal = "NEW";
    this.modalVisible = true;
    const ref = this.dialogService.open(EmpresasModalComponent, {
      header: 'Informe as empresas ' , width: '70%'
    });

    ref.onClose.subscribe( () => {
      //ATUALIZA A LISTA DE ENDEREÇOS
      //console.log("FECHANDO O MODAL ")
      this.empresasService.listAll((<HTMLSelectElement>document.getElementById('id')).value)
      //console.log("RECUPEROU ALISTA ")
    });
  }

  // Abre Modal empresa - edita empresa é o mesmo HTML, mas aqui vem com parametro id da empresa
  showEmpresasModalEdit(empresaId) {

    this.env.currentActionGlobal = "EDIT";
    this.modalVisible = true;
    const ref = this.dialogService.open(EmpresasModalComponent, {
      header: 'Editando empresas ' , width: '70%'
    });

    this.empresasService.buscaEmpresa(empresaId);

    ref.onClose.subscribe( () => {
      //ATUALIZA A LISTA DE ENDEREÇOSEMPRESAS
      this.empresasService.listAll((<HTMLSelectElement>document.getElementById('id')).value)
    });
  }

  showEmpresasModalDelete(empresaId){
    this.env.currentActionGlobal = "DELETE";
    this.modalVisible = true;
    this.env.botaoOnOf = false;

    const ref = this.dialogService.open(EmpresasModalComponent, {
      header: 'CONFIRMA DELETAR EMPRESA? ' , width: '70%',
      //styleClass:
      contentStyle: {"background-color": "red", "font-size": "3px"},
      //      style: {"background-color": "blue"},

    });

    this.empresasService.buscaEmpresa(empresaId);

    ref.onClose.subscribe( () => {
      //ATUALIZA A LISTA DE EMPRESAS
      this.empresasService.listAll((<HTMLSelectElement>document.getElementById('id')).value)
    });
  }

  // Retorna lista de empresas e rederiza HTML da lista
  loadListEmpresas(){
    this.empresasService.empresasChangeSubscribe(
      resources => {
        //console.log("2  - ATUALZANDO foi no loadListEmpresas " );
        this.resources = resources;

      }
    )
  }

}
