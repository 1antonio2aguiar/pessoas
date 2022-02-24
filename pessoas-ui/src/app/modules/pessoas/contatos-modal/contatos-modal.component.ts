import { Component, Injector, Input, Output, EventEmitter } from '@angular/core';
import { BaseResourceFormComponent } from 'src/app/shared/components/base-resource-form/base-resource-form.component';
import { MessageService} from 'primeng/api';
import { SelectItem } from 'primeng/api';
import { Validators, FormGroup } from '@angular/forms';
import { DialogService, DynamicDialogRef } from 'primeng';

import { environment } from 'src/environments/environment';

import { Contatos } from './../../../shared/models/contatos';
import { ContatosService } from '../contatos/contatos.service';
import { TiposContatosService } from '../../tipos-contatos/tipos-contatos.service';

@Component({
  selector: 'app-contatos-modal',
  templateUrl: './contatos-modal.component.html',
  styleUrls: ['./contatos-modal.component.css']
})

export class ContatosModalComponent extends BaseResourceFormComponent<Contatos> {

  // acho que isso e o gabriel que pos por conta do modal bloqueando tela
  @Output() closeModalEvent = new EventEmitter<boolean>();
  private contatos: Contatos;

  env = environment;
  contatoId = 0;

  tiposContatosList: SelectItem[];

  //Move os dados da pessoas para o modal de cadastro de contatos
  dadosPessoa=(<HTMLSelectElement>document.getElementById('nome')).value +' CPF: ' +
        (<HTMLSelectElement>document.getElementById('cpf')).value

  constructor(
    protected contatosService: ContatosService,
    protected injector: Injector,
    public messageService: MessageService,
    public dialogService: DialogService ,
    protected tiposContatosService: TiposContatosService,
    public ref: DynamicDialogRef,
  ) {
    super(injector, new Contatos(), contatosService, Contatos.fromJson, new MessageService());

    this.loadTipoContato();
    this.buildResourceForm(); /*limpa o formulario/resourceForm */

    if (this.env.currentActionGlobal == "DELETE" || this.env.currentActionGlobal == "EDIT"){
      this.env.botaoOnOf = true;
    } else {
      this.env.botaoOnOf = false;
    }

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      id: [null],
      pessoa: [(<HTMLSelectElement>document.getElementById('id')).value],
      contato:[null, [Validators.required, Validators.minLength(5)]],
      tiposContatos:
        this.formBuilder.group({
        descricao:[null],
        id: [null, [Validators.required]],
      }),
      usuario: JSON.parse(sessionStorage.getItem("usuario")).nome,
    })
  }

  submitForm() {
    //this.submittingForm = true;
    if (this.env.currentActionGlobal === 'NEW') {
      // clicou no + (novo)
      this.createResource();
    } else {
      if (this.env.currentActionGlobal === 'EDIT') {
        // clicou no update
        this.updateResource();
      } else {
        // clicou no delete
        this.deleteResource();
      }
    }
  }

  protected deleteResource(){

    this.contatosService.deleteContato(this.contatoId)
    .then(response => {
      this.buildResourceForm(); /*limpa o formulario/resorceForm*/
      this.ref.close();
    })
  }

  protected updateResource(){
    const resource: Contatos = this.jsonDataToResourceFn(this.resourceForm.value);

    // copia os dados de Contatos(classe) para contatos(variavel json)
    let contatos = JSON.stringify(resource);

    // Chama a funcao que faz update
    this.contatosService.updateContato(contatos)
    .then(response => {
      this.buildResourceForm(); /*limpa o formulario/resorceForm*/
      this.ref.close();
    })
  }

  protected createResource() {
    const resource: Contatos = this.jsonDataToResourceFn(this.resourceForm.value);
    // copia os dados de Contatos(classe) para contatos(variavel json)
    let contatos = JSON.stringify(resource);

    // Chama a funcao que insere
    this.contatosService.createContato(contatos)
    .then(response => {
      this.buildResourceForm(); /*limpa o formulario/resorceForm*/
      this.ref.close();
    }).
      catch(error => {
        console.log(error);
        this.messageService.add({severity:'error', summary: 'Erro', detail:error.error[0].mensagemUsuario});
    });
  }

  fecharModal(){
    this.ref.close();
  }

  loadTipoContato() {
    this.tiposContatosService.listAll()

    .then(tiposContatos => {
      this.tiposContatosList = tiposContatos.map(c =>
        ({ value: c.id, label: c.descricao })
      );
    })
    .catch(erro => erro);
  }

  // No edit retorna os dados aqui.
  ngOnInit(){
    this.contatosService.contatosEditSubscribeId(
      resources => {

        this.contatoId = resources.id
        this.resourceForm.patchValue({
          tiposContatos:{
            id: resources.tiposContatos.id,
            descricao: resources.tiposContatos.descricao
          },
          id: resources.id,
          contato: resources.contato
        })
      }
    )

    if(this.env.currentActionGlobal === "DELETE"){
      (<HTMLSelectElement>document.getElementById('tipoContatoDdw')).disabled = true;
      (<HTMLSelectElement>document.getElementById('contato')).disabled = true;

    }
  }

}

