import { DadosPjGeral } from './../../../../shared/models/dados-pj-geral';
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

import { PessoasEmpresasModalComponent } from 'src/app/modules/pessoas-empresas/pessoas-empresas-modal/pessoas-empresas-modal.component';

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
  cpfCnpj = '';

  masks = {
    mask: [
      {
        mask: '00.000-000'
      }
    ]
  };

  tiposVinculosEmpresasList: SelectItem[];

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

    if (this.env.currentActionGlobal == "DELETE" || this.env.currentActionGlobal == "EDIT"){
      this.env.botaoOnOf = true;
    } else {
      this.env.botaoOnOf = false;
    }
  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      id: [null],
      idEmpresa: [(<HTMLSelectElement>document.getElementById('id')).value],
      idPessoa:[null, [Validators.required, Validators.minLength(5)]],

      tiposVinculosEmpresas:
        this.formBuilder.group({
        id: [null, [Validators.required]],
        descricao:[null],
      }),

      idPessoas: this.formBuilder.group({
        dadosPf: this.formBuilder.group({
          cpfCnpj: [null],
          cpf: [null],
        }),
        id: [null, [Validators.required]],
        nome: [null],
      }),

      idEmpresas: this.formBuilder.group({
        dadosPj: this.formBuilder.group({
            cnpj: [(<HTMLSelectElement>document.getElementById('cnpj')).value],
          }),
        id: [(<HTMLSelectElement>document.getElementById('id')).value],
        nome: [(<HTMLSelectElement>document.getElementById('nome')).value],
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
      this.tiposVinculosEmpresasList = tiposVinculosSocios.map(c =>
        ({ value: c.id, label: c.descricao })
      );
    })
    .catch(erro => erro);
  }

  // Modal Pessoas
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
        idPessoas: {
          dadosPf:{
            cpfCnpj: this.cpfCnpj,
            cpf: this.cpfCnpj,
          },
          id: pessoa.id,
          nome: pessoa.nome,
        },
        idPessoa: pessoa.id
      });
    });
  }


  // No edit retorna os dados aqui.
  ngOnInit(){
    this.sociosService.sociosEditSubscribeId(
      resources => {

        if (resources.pessoasPessoas.dadosPjGeral){
          this.cpfCnpj = resources.pessoasPessoas.dadosPjGeral.cnpj.toString();
          this.cpfCnpj = this.cpfCnpj.replace(/^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/, "$1.$2.$3/$4-$5")
        } else {
          this.cpfCnpj = resources.pessoasPessoas.dadosPfGeral.cpf.toString();
          this.cpfCnpj = this.cpfCnpj.replace(/^(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4")
        }

        this.socioId = resources.id;

        this.resourceForm.patchValue({
          id: resources.id,
          participacao: resources.participacao,
          idEmpresa: resources.pessoasEmpresas.id,
          idPessoa: resources.pessoasPessoas.id,

          tiposVinculosEmpresas:{
            id: resources.tiposVinculosEmpresas.id,
            descricao: resources.tiposVinculosEmpresas.descricao
          },

          idPessoas:{
            id: resources.pessoasPessoas.id,
            nome: resources.pessoasPessoas.nome,
            dadosPf:{
              cpfCnpj: this.cpfCnpj,
              cpf: this.cpfCnpj,
            }
          },

          idEmpresas: this.formBuilder.group({
            dadosPj: this.formBuilder.group({
                cnpj: [(<HTMLSelectElement>document.getElementById('cnpj')).value],
              }),
            id: [(<HTMLSelectElement>document.getElementById('id')).value],
            nome: [(<HTMLSelectElement>document.getElementById('nome')).value],
          })
        });
      }
    )

    if(this.env.currentActionGlobal === "DELETE"){
      (<HTMLSelectElement>document.getElementById('tipoVinculoEmpresaDdw')).disabled = true;
      (<HTMLSelectElement>document.getElementById('participacao')).disabled = true;
    }
  }

}

