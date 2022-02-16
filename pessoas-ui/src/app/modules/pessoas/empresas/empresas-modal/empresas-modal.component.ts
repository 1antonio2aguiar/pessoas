import { Component, Injector, Input, Output, EventEmitter } from '@angular/core';
import { BaseResourceFormComponent } from 'src/app/shared/components/base-resource-form/base-resource-form.component';
import { MessageService} from 'primeng/api';
import { SelectItem } from 'primeng/api';
import { Validators, FormGroup } from '@angular/forms';
import { DialogService, DynamicDialogRef } from 'primeng';

import { environment } from 'src/environments/environment';

import { EmpresasPessoas } from 'src/app/shared/models/empresas-pessoas';
import { TiposVinculosEmpresasService } from 'src/app/modules/tipos-vinculos-empresas/tipos-vinculos-empresas.service';
import { EmpresasService } from '../empresas.service';
import { PessoasEmpresasModalComponent } from 'src/app/modules/pessoas-empresas/pessoas-empresas-modal/pessoas-empresas-modal.component';

@Component({
  selector: 'app-empresas-modal',
  templateUrl: './empresas-modal.component.html',
  styleUrls: ['./empresas-modal.component.css'],
  providers: [DialogService],
})

export class EmpresasModalComponent extends BaseResourceFormComponent<EmpresasPessoas> {

  // acho que isso e o gabriel que pos por conta do modal bloqueando tela
  @Output() closeModalEvent = new EventEmitter<boolean>();
  private empresas: EmpresasPessoas;

  env = environment;
  empresaId = 0;
  cpfCnpj = '';

  tiposVinculosList: SelectItem[];

  //Move os dados da pessoas para o modal de cadastro de contatos
  dadosPessoa=(<HTMLSelectElement>document.getElementById('nome')).value +' CPF: ' +
        (<HTMLSelectElement>document.getElementById('cpf')).value


  constructor(
    protected empresasService: EmpresasService,
    protected injector: Injector,
    public messageService: MessageService,
    public dialogService: DialogService ,
    protected tiposVinculosEmpresasService: TiposVinculosEmpresasService,
    public ref: DynamicDialogRef,
  ) {
    super(injector, new EmpresasPessoas(), empresasService, EmpresasPessoas.fromJson,new MessageService());

    this.loadTipoVinculo();
    this.buildResourceForm(); /*limpa o formulario/resourceForm */

    if (this.env.currentActionGlobal != "DELETE"){
      this.env.botaoOnOf = false;
    } else {
      this.env.botaoOnOf = true;
    }

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      id: [null],
      pessoa: [(<HTMLSelectElement>document.getElementById('id')).value],
      empresa:[null, [Validators.required, Validators.minLength(5)]],

      tiposVinculos:
        this.formBuilder.group({
        id: [null, [Validators.required]],
        descricao:[null],
      }),

      pessoas: this.formBuilder.group({
        dadosPessoa: this.formBuilder.group({
          cpfCnpj: [null],
        }),
        id: [null, [Validators.required]],
        nome: [null],
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

    this.empresasService.deleteEmpresa(this.empresaId)
    .then(response => {
      this.buildResourceForm(); /*limpa o formulario/resorceForm*/
      this.ref.close();
    })
  }

  protected updateResource(){
    const resource: EmpresasPessoas = this.jsonDataToResourceFn(this.resourceForm.value);

    // copia os dados de EmpresasPessoas(classe) para empresas(variavel json)
    let empresas = JSON.stringify(resource);

    // Chama a funcao que faz update
    this.empresasService.updateEmpresa(empresas)
    .then(response => {
      this.buildResourceForm(); /*limpa o formulario/resorceForm*/
      this.ref.close();
    })
  }

  protected createResource() {
    const resource: EmpresasPessoas = this.jsonDataToResourceFn(this.resourceForm.value);
    // copia os dados de EmpresasPessoas(classe) para empresas(variavel json)
    let empresas = JSON.stringify(resource);

    // Chama a funcao que insere
    this.empresasService.createEmpresa(empresas)
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

  loadTipoVinculo() {
    this.tiposVinculosEmpresasService.listAll()

    .then(tiposVinculosEmpresas => {

      this.tiposVinculosList = tiposVinculosEmpresas.map(c =>
        ({ value: c.id, label: c.descricao })
      );
    })
    .catch(erro => erro);
  }

  // Modal Pessoas   CepsModalComponent
  showPessoas($event) {
    const ref = this.dialogService.open(PessoasEmpresasModalComponent, {
      header: 'Selecione a Pessoa',
      width: '70%'
    });

    ref.onClose.subscribe((pessoa) => {
      if (pessoa.fisicaJuridica ==  'J'){
        this.cpfCnpj = pessoa.dadosPjGeral.cnpj
        this.cpfCnpj = this.cpfCnpj.replace(/^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/, "$1.$2.$3/$4-$5")
      } else {
        this.cpfCnpj = pessoa.dadosPfGeral.cpf
        this.cpfCnpj = this.cpfCnpj.replace(/^(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4")
      }

      this.resourceForm.patchValue({
        pessoas: {
          id: pessoa.id,
          nome: pessoa.nome,

          dadosPessoa:{
              cpfCnpj: this.cpfCnpj
          }
        },
        empresa: pessoa.id,
      });
    });
  }

  // No edit retorna os dados aqui.
  ngOnInit(){
    this.empresasService.empresasEditSubscribeId(
      resources => {
        this.empresaId = resources.id
        this.resourceForm.patchValue({
          tiposVinculosEmpresas:{
            id: resources.tiposVinculosEmpresas.id,
            descricao: resources.tiposVinculosEmpresas.descricao
          },
          id: resources.id,
          nome: resources.idEmpresa
        })
      }
    )

    if(this.env.currentActionGlobal === "DELETE"){
      (<HTMLSelectElement>document.getElementById('tipoContatoDdw')).disabled = true;
      (<HTMLSelectElement>document.getElementById('contato')).disabled = true;

    }
  }

}


