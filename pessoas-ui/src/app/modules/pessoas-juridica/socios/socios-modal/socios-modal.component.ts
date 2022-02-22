import { Component, Injector, Input, Output, EventEmitter } from '@angular/core';
import { MessageService} from 'primeng/api';
import { BaseResourceFormComponent } from '../../../../shared/components/base-resource-form/base-resource-form.component';
import { SelectItem } from 'primeng/api';
import { Validators, FormGroup } from '@angular/forms';
import { DialogService, DynamicDialogRef } from 'primeng';

import { environment } from 'src/environments/environment';

import { EmpresasPessoas } from 'src/app/shared/models/empresas-pessoas';
import { TiposVinculosEmpresasService } from 'src/app/modules/tipos-vinculos-empresas/tipos-vinculos-empresas.service';
import { SociosService } from '../socios.service';
import { PessoasModalComponent } from './../../../modal/pessoas-modal/pessoas-modal.component';
import { CepsModalComponent } from 'src/app/modules/ceps/ceps-modal/ceps-modal.component';

@Component({
  selector: 'app-socios-modal',
  templateUrl: './socios-modal.component.html',
  styleUrls: ['./socios-modal.component.css'],
  providers: [DialogService],
})

export class SociosModalComponent extends BaseResourceFormComponent<EmpresasPessoas> {

  // acho que isso e o gabriel que pos por conta do modal bloqueando tela
  // Já testei não é isso que faz o modal fechar.
  @Output() closeModalEvent = new EventEmitter<boolean>();
  //private empresasPessoas: EmpresasPessoas;

  env = environment;
  socioId = 0;

  masks = {
    mask: [
      {
        mask: '00.000-000'
      }
    ]
  };

  tiposVinculosSociosList: SelectItem[];

  //Move os dados da pessoas para o modal de cadastro de socios
  dadosPessoa=(<HTMLSelectElement>document.getElementById('nome')).value +' CNPJ: ' +
        (<HTMLSelectElement>document.getElementById('cnpj')).value

  constructor(

    protected sociosService: SociosService,
    protected injector: Injector,
    public messageService: MessageService,
    public dialogService: DialogService ,
    protected tiposVinculosEmpresasService: TiposVinculosEmpresasService,
    public ref: DynamicDialogRef,

  ) {
    super(injector, new EmpresasPessoas(), sociosService, EmpresasPessoas.fromJson, new MessageService());

    this.loadtipoVinculoSocio();
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

      tiposVinculosSocios:
        this.formBuilder.group({
        descricao:[null],
        id: [null, [Validators.required]],
      }),

      pessoas: this.formBuilder.group({
        dadosPessoa: this.formBuilder.group({
          documento: [null],
        }),
        id: [null, [Validators.required, Validators.minLength(10)]],
        nome: [null],
      }),

      participacao: [0],

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

    this.sociosService.deleteSocio(this.socioId)
    .then(response => {
      this.buildResourceForm(); /*limpa o formulario/resorceForm*/
      this.ref.close();
    })
  }

  protected updateResource(){
    const resource: EmpresasPessoas = this.jsonDataToResourceFn(this.resourceForm.value);

    // copia os dados de Socios(classe) para socios(variavel json)
    let socios = JSON.stringify(resource);

    // Chama a funcao que faz update
    this.sociosService.updateSocio(socios)
    .then(response => {
      this.buildResourceForm(); /*limpa o formulario/resorceForm*/
      this.ref.close();
    })
  }

  protected createResource() {
    const resource: EmpresasPessoas = this.jsonDataToResourceFn(this.resourceForm.value);
    // copia os dados de Socios(classe) para socios(variavel json)
    let socios = JSON.stringify(resource);

    // Chama a funcao que insere
    this.sociosService.createSocio(socios)
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

  loadtipoVinculoSocio() {
    this.tiposVinculosEmpresasService.listAll()

    .then(tiposVinculosSocios => {
      this.tiposVinculosSociosList = tiposVinculosSocios.map(c =>
        ({ value: c.id, label: c.descricao })
      );
    })
    .catch(erro => erro);
  }

  // Modal Pessoas
  showPessoas($event) {
    console.log("ESTA NO SHOWPESSOAS SOCIOS-modal.componensts ")

    const ref = this.dialogService.open(PessoasModalComponent, {
      header: 'Selecione a Pessoa',
      width: '70%'
    });



    ref.onClose.subscribe((pessoa) => {
      console.log("ESTA NO SHOWPESSOAS SOCIOS-modal.componensts VOLTOUUUU")

      this.resourceForm.patchValue({
        pessoas: {
          id: pessoa.id,
          nome: pessoa.nome,

          dadosPessoa:{
              documento: pessoa.dadosPf.cpf
          }
        }
      });
    });
  }

  // No edit retorna os dados aqui.
  ngOnInit(){
      this.sociosService.sociosEditSubscribeId(
      resources => {
        this.socioId = resources.id
        this.resourceForm.patchValue({
          tiposVinculosSocios:
          this.formBuilder.group({
          descricao:[null],
          id: [null, [Validators.required]],
        }),
          id: resources.id,
          participacao: resources.participacao
        })
      }
    )

    if(this.env.currentActionGlobal === "DELETE"){
      (<HTMLSelectElement>document.getElementById('tipoVinculoSocioDdw')).disabled = true;
      (<HTMLSelectElement>document.getElementById('socio')).disabled = true;

    }

  }

}

