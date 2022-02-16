import { Component, Injector, Input, Output, EventEmitter } from '@angular/core';
import { BaseResourceFormComponent } from 'src/app/shared/components/base-resource-form/base-resource-form.component';
import { MessageService} from 'primeng/api';
import { SelectItem } from 'primeng/api';
import { Validators, FormGroup, FormControlName } from '@angular/forms';
import { DialogService, DynamicDialogRef, TreeModule } from 'primeng';
import * as moment from 'moment';

import { environment } from 'src/environments/environment';

import { Documentos } from './../../../../shared/models/documentos';
import { DocumentosService } from '../documentos.service';
import { TiposDocumentosService } from 'src/app/modules/tipos-documentos/tipos-documentos.service';

@Component({
  selector: 'app-documentos-modal',
  templateUrl: './documentos-modal.component.html',
  styleUrls: ['./documentos-modal.component.css']
})

export class DocumentosModalComponent extends BaseResourceFormComponent<Documentos> {

  // acho que isso e o gabriel que pos por conta do modal bloqueando tela
  @Output() closeModalEvent = new EventEmitter<boolean>();
  private documentos: Documentos;

  env = environment;
  documentoId = 0;
  tipoDocumento = {value: 0};
  maxDate = new Date();


  isDisabledRg   : boolean = true;
  isDisabledDoc : boolean = true;
  isDisabledCnh : boolean = true;
  isDisabledCert: boolean = true;
  isDisabledTitEleitor: boolean = true;

  tiposDocumentosList: SelectItem[];

  //Move os dados da pessoas para o modal de cadastro de Documentos
  dadosPessoa=(<HTMLSelectElement>document.getElementById('nome')).value +' CNPJ: ' +
       (<HTMLSelectElement>document.getElementById('cnpj')).value

  ptBrLocale;

  constructor(
    protected documentosService: DocumentosService,
    protected injector: Injector,
    public messageService: MessageService,
    public dialogService: DialogService ,
    protected tiposDocumentosService: TiposDocumentosService,
    public ref: DynamicDialogRef,
  ) {
    super(injector, new Documentos(), documentosService, Documentos.fromJson, new MessageService());

    this.loadTipoDocumento();
    this.ptBrLocale = this.loadLocale();
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

      numeroDocumento:[{
        value: null,
        disabled: this.isDisabledDoc},
        [Validators.required, Validators.minLength(5)]
      ],

      dataDocumento: [{value: null,disabled: this.isDisabledDoc}],
      documentoOrigem: [{value: null,disabled: this.isDisabledRg}],
      dataExpedicao: [{value: null,disabled: this.isDisabledDoc}],
      orgaoExpedidor: [{value: null,disabled: this.isDisabledRg}],
      dataPrimeiraCnh: [{value: null,disabled: this.isDisabledCnh}],
      dataValidadeCnh: [{value: null,disabled: this.isDisabledCnh}],
      zona: [{value: null,disabled: this.isDisabledTitEleitor}],
      secao: [{value: null,disabled: this.isDisabledTitEleitor}],
      emissaoCertidao: [{value: null,disabled: this.isDisabledCert}],
      termo: [{value: null,disabled: this.isDisabledCert}],
      folha: [{value: null,disabled: this.isDisabledCert}],
      livro: [{value: null,disabled: this.isDisabledCert}],

      tiposDocumentos:
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
    this.documentosService.deleteDocumento(this.documentoId)
    .then(response => {
      this.buildResourceForm(); /*limpa o formulario/resorceForm*/
      this.ref.close();
    })
  }

  protected updateResource(){
    const replacer = function (key, value) {
      if (this[key] instanceof Date) {
        //console.log('ENTROU RESOLVER DATA ' + moment(this[key]).format('DD/MM/YYYY'))
        return moment(this[key]).format('DD/MM/YYYY');
      }
      return value;
    };

    const resource: Documentos = this.jsonDataToResourceFn(this.resourceForm.value);

    // copia os dados de Documentos(classe) para Documentos(variavel json)
    let documentos = JSON.stringify(resource,replacer);

    // Chama a funcao que faz update
    this.documentosService.updateDocumento(documentos)
    .then(response => {
      this.buildResourceForm(); /*limpa o formulario/resorceForm*/
      this.ref.close();
    })
  }

  protected createResource() {
    const replacer = function (key, value) {
      if (this[key] instanceof Date) {
        //console.log('ENTROU RESOLVER DATA ' + moment(this[key]).format('DD/MM/YYYY'))
        return moment(this[key]).format('DD/MM/YYYY');
      }
      return value;
    };

    const resource: Documentos = this.jsonDataToResourceFn(this.resourceForm.value);
    // copia os dados de Documentos(classe) para Documentos(variavel json)
    let documentos = JSON.stringify(resource,replacer);

    // Chama a funcao que insere
    this.documentosService.createDocumento(documentos)
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

  loadTipoDocumento() {
    this.tiposDocumentosService.listAll()

    .then(tiposDocumentos => {
      this.tiposDocumentosList = tiposDocumentos.map(c =>
        ({ value: c.id, label: c.descricao })
      );
    })
    .catch(erro => erro);
  }

  // No edit retorna os dados aqui.
  ngOnInit(){
    this.documentosService.documentosEditSubscribeId(
      resources => {
        this.tipoDocumento.value = resources.tiposDocumentos.id

        this.onChange(this.tipoDocumento);

        this.documentoId = resources.id
        this.resourceForm.patchValue({
          tiposDocumentos:{
            id: resources.tiposDocumentos.id,
            descricao: resources.tiposDocumentos.descricao
          },

          id: resources.id,
          numeroDocumento: resources.numeroDocumento,
          dataDocumento: resources.dataDocumento,
          documentoOrigem: resources.documentoOrigem,
          dataExpedicao: resources.dataExpedicao,
          orgaoExpedidor: resources.orgaoExpedidor,
          dataPrimeiraCnh: resources.dataPrimeiraCnh,
          dataValidadeCnh: resources.dataValidadeCnh,
          zona: resources.zona,
          secao: resources.secao,
          emissaoCertidao: resources.dtEmisCert,
          termo: resources.termo,
          folha: resources.folha,
          livro: resources.livro,
        })
      }
    )

    if(this.env.currentActionGlobal === "DELETE"){
      (<HTMLSelectElement>document.getElementById('tipoContatoDdw')).disabled = true;
      (<HTMLSelectElement>document.getElementById('contato')).disabled = true;

    }
  }

  // Quando é registro novo executa esta função ao clicar no tipo documento
  // Bloquei ou libera as entradas de acordo com o tipo documento
  onChange(event){

    if (this.env.currentActionGlobal === 'EDIT') {
      this.env.botaoOnOf = true;
    } else {
      this.env.botaoOnOf = false;
    }

    if(event.value === 1){
      //RG
      this.isDisabledDoc  = true;
      this.isDisabledDoc  = !this.isDisabledDoc;
      const stateDoc      = this.isDisabledDoc ? 'disable' : 'enable';

      this.isDisabledRg    = true;
      this.isDisabledRg    = !this.isDisabledRg;
      const stateRg        = this.isDisabledRg ? 'disable' : 'enable';

      this.isDisabledTitEleitor = false;
      this.isDisabledTitEleitor = !this.isDisabledTitEleitor;
      const stateTitEleitor = this.isDisabledTitEleitor ? 'disable' : 'enable';

      this.isDisabledCnh = false;
      this.isDisabledCnh = !this.isDisabledCnh;
      const stateCnh = this.isDisabledCert ? 'disable' : 'enable';

      this.isDisabledCert = false;
      this.isDisabledCert = !this.isDisabledCert;
      const stateCert = this.isDisabledCert ? 'disable' : 'enable';

      this.validaEntradas(stateDoc,stateRg,stateTitEleitor,stateCnh,stateCert)

    } else {
      if(event.value === 3){
        // C.N.H
        this.isDisabledDoc  = true;
        this.isDisabledDoc  = !this.isDisabledDoc;
        const stateDoc      = this.isDisabledDoc ? 'disable' : 'enable';

        this.isDisabledRg    = false;
        this.isDisabledRg    = !this.isDisabledRg;
        const stateRg        = this.isDisabledRg ? 'disable' : 'enable';

        this.isDisabledTitEleitor = false;
        this.isDisabledTitEleitor = !this.isDisabledTitEleitor;
        const stateTitEleitor = this.isDisabledTitEleitor ? 'disable' : 'enable';

        this.isDisabledCnh = true;
        this.isDisabledCnh = !this.isDisabledCnh;
        const stateCnh = this.isDisabledCnh ? 'disable' : 'enable';

        this.isDisabledCert = false;
        this.isDisabledCert = !this.isDisabledCert;
        const stateCert = this.isDisabledCert ? 'disable' : 'enable';

        this.validaEntradas(stateDoc,stateRg,stateTitEleitor,stateCnh,stateCert)

      } else {
        if(event.value === 10){
          // TITULO DE ELEITOR
          this.isDisabledDoc  = true;
          this.isDisabledDoc  = !this.isDisabledDoc;
          const stateDoc      = this.isDisabledDoc ? 'disable' : 'enable';

          this.isDisabledRg    = false;
          this.isDisabledRg    = !this.isDisabledRg;
          const stateRg        = this.isDisabledRg ? 'disable' : 'enable';

          this.isDisabledTitEleitor = true;
          this.isDisabledTitEleitor = !this.isDisabledTitEleitor;
          const stateTitEleitor = this.isDisabledTitEleitor ? 'disable' : 'enable';

          this.isDisabledCnh = false;
          this.isDisabledCnh = !this.isDisabledCnh;
          const stateCnh = this.isDisabledCnh ? 'disable' : 'enable';

          this.isDisabledCert = false;
          this.isDisabledCert = !this.isDisabledCert;
          const stateCert = this.isDisabledCert ? 'disable' : 'enable';

          this.validaEntradas(stateDoc,stateRg,stateTitEleitor,stateCnh,stateCert)

        } else {
          if(event.value === 13){
            // CERTIDÕES
            this.isDisabledDoc  = true;
            this.isDisabledDoc  = !this.isDisabledDoc;
            const stateDoc      = this.isDisabledDoc ? 'disable' : 'enable';

            this.isDisabledRg    = false;
            this.isDisabledRg    = !this.isDisabledRg;
            const stateRg        = this.isDisabledRg ? 'disable' : 'enable';

            this.isDisabledTitEleitor = false;
            this.isDisabledTitEleitor = !this.isDisabledTitEleitor;
            const stateTitEleitor = this.isDisabledTitEleitor ? 'disable' : 'enable';

            this.isDisabledCnh = false;
            this.isDisabledCnh = !this.isDisabledCnh;
            const stateCnh = this.isDisabledCnh ? 'disable' : 'enable';

            this.isDisabledCert = true;
            this.isDisabledCert = !this.isDisabledCert;
            const stateCert = this.isDisabledCert ? 'disable' : 'enable';

            this.validaEntradas(stateDoc,stateRg,stateTitEleitor,stateCnh,stateCert)

          } else {
            // OUTROS TIPOS DE DOCUMENTOS
            this.isDisabledDoc  = true;
            this.isDisabledDoc  = !this.isDisabledDoc;
            const stateDoc      = this.isDisabledDoc ? 'disable' : 'enable';

            this.isDisabledRg    = false;
            this.isDisabledRg    = !this.isDisabledRg;
            const stateRg        = this.isDisabledRg ? 'disable' : 'enable';

            this.isDisabledTitEleitor = false;
            this.isDisabledTitEleitor = !this.isDisabledTitEleitor;
            const stateTitEleitor = this.isDisabledTitEleitor ? 'disable' : 'enable';

            this.isDisabledCnh = false;
            this.isDisabledCnh = !this.isDisabledCnh;
            const stateCnh = this.isDisabledCnh ? 'disable' : 'enable';

            this.isDisabledCert = false;
            this.isDisabledCert = !this.isDisabledCert;
            const stateCert = this.isDisabledCert ? 'disable' : 'enable';

            this.validaEntradas(stateDoc,stateRg,stateTitEleitor,stateCnh,stateCert)
          }
        }
      }
    }
  }

  protected validaEntradas(stateDoc,stateRg,stateTitEleitor,stateCnh,stateCert){
    //console.log(stateDoc," ",stateRg," ",stateTitEleitor," ",stateCnh," ",stateCert);

    Object.keys(this.resourceForm.controls).forEach((controlName) => {

      switch ( controlName ) {
        case "numeroDocumento":
          this.resourceForm.controls[controlName][stateDoc](); break;
        case "dataDocumento":
          this.resourceForm.controls[controlName][stateDoc](); break;
        case "dataExpedicao":
          this.resourceForm.controls[controlName][stateDoc](); break;

        case "documentoOrigem":
          this.resourceForm.controls[controlName][stateRg](); break;
        case "orgaoExpedidor":
          this.resourceForm.controls[controlName][stateRg](); break;

        case "zona":
          this.resourceForm.controls[controlName][stateTitEleitor](); break;
        case "secao":
          this.resourceForm.controls[controlName][stateTitEleitor](); break;

        case "dataPrimeiraCnh":
          this.resourceForm.controls[controlName][stateCnh](); break;
        case "dataValidadeCnh":
          this.resourceForm.controls[controlName][stateCnh](); break;

        case "termo":
          this.resourceForm.controls[controlName][stateCert](); break;
        case "folha":
          this.resourceForm.controls[controlName][stateCert](); break;
        case "livro":
          this.resourceForm.controls[controlName][stateCert](); break;
        case "emissaoCertidao":
          this.resourceForm.controls[controlName][stateCert](); break;
      }
    });
  }


}
