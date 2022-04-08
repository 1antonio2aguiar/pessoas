
import { Component, Injector, Input } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { SelectItem } from 'primeng/api';
import { DialogService } from 'primeng';
import { MessageService } from 'primeng/api';
import { Validators } from '@angular/forms';

import { PessoasJuridica } from './../../../shared/models/pessoas-juridica';
import { PessoasJuridicaService } from '../pessoas-juridica.service';
import { TiposPessoasService } from './../../tipos-pessoas/tipos-pessoas.service';
import { TiposEmpresasService } from '../../tipos-empresas/tipos-empresas.service';
import { SituacoesService } from './../../situacoes/situacoes.service';

import { environment } from 'src/environments/environment';
import * as moment from 'moment';

@Component({
  selector: 'app-pessoas-juridica-cadastro',
  templateUrl: './pessoas-juridica-cadastro.component.html',
  styleUrls: ['./pessoas-juridica-cadastro.component.css']
})

export class PessoasJuridicaCadastroComponent extends BaseResourceFormComponent<PessoasJuridica> {
  @Input() pessoaId: number;
  env = environment;

  tipoPessoaList: SelectItem[];
  tipoEmpresasList: SelectItem[];
  situacaoList: SelectItem[];

  ptBrLocale;

  masks = {
    mask: [
      {
        mask: '00.000.000/0000-00'
      }
    ]
  };

  microEmpresa = [
    { value: 'N', selected: true, label: 'NÃO' },
    { value: 'S', selected: false, label: 'SIM' }
  ];

  constructor(
    protected pessoasJuridicaService: PessoasJuridicaService,
    protected tiposPessoasService: TiposPessoasService,
    protected tiposEmpresasService: TiposEmpresasService,
    protected situacoesService: SituacoesService,
    protected injector: Injector,

  ) {

    super(injector, new PessoasJuridica(), pessoasJuridicaService, PessoasJuridica.fromJson, new MessageService());
    this.loadTipoPessoa();
    this.loadSituacao();
    this.loadTipoEmpresa();
    this.ptBrLocale = this.loadLocale();

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      id: [null],
      nome: [null, [Validators.required, Validators.minLength(5)]],
      fisicaJuridica: ["J"],
      dataCadastro: [null],
      observacao: [],
      usuario: JSON.parse(sessionStorage.getItem("usuario")).nome,
      dataAlteracao:[null],

      mesEnvioSicom:[null],
      anoEnvioSicom:[null],

      tiposPessoas: this.formBuilder.group({
        descricao:[null],
        id: [2],
      }),

      dadosPj: this.formBuilder.group({
        cnpj: [null],
        nomeFantasia: [null],
        objetoSocial: [null],
        microEmpresa: [null],
        conjuge: [null],

        tiposEmpresas: this.formBuilder.group({
          descricao:[null],
          tipoEstabelecimento: [null, [Validators.required]],
        }),
      }),

      situacoes: this.formBuilder.group({
        descricao:[null],
        id: [1]
      }),
    })
  }

  protected creationPageTitle(): string {
    //this.env.tabPanelOnOff = true;
    return 'Novo Cadastro de Pessoa Juridíca';
  }

  protected editionPageTitle(): string {
    this.env.tabPanelOnOff = false;
    return 'Editando Pessoas Juridíca: ' ;
  }

  loadTipoPessoa() {
    this.tiposPessoasService.listAll()
    .then(tiposPessoas => {
      this.tipoPessoaList = tiposPessoas.map(c =>
        ({ label: c.descricao, value: c.id })
      );
    })
    .catch(erro => erro);
  }

  loadTipoEmpresa() {
    this.tiposEmpresasService.listAll()
    .then(tiposEmpresas => {
      this.tipoEmpresasList = tiposEmpresas.map(te =>
        ({
          label: te.descricao, value: te.tipoEstabelecimento
        })
      );
    })
    .catch(erro => erro);
  }

  loadSituacao() {
    this.situacoesService.listAll()
    .then(situacoes => {
      this.situacaoList = situacoes.map(c =>
        ({ label: c.descricao, value: c.id })
      );
    })
    .catch(erro => erro);
  }

  submitForm() {
    this.submittingForm = true;
    if (this.currentAction === 'new') {
      // clicou no + (novo)
      this.createResource();
    } else {
        // clicou no update
        this.updateResource();
    }
  }

  protected createResource() {
    const resource: PessoasJuridica = this.jsonDataToResourceFn(this.resourceForm.value);
    // copia os dados de Pessoas para pessoas
    let pessoasJuridica = JSON.stringify(resource);

    // Chama a funcao que grava
    this.pessoasJuridicaService.createPessoaJuridica(pessoasJuridica)
    .then(response => {
      this.messageService.add({severity:'success', summary: 'Sucesso', detail: 'Pessoa inserida com sucesso!'});

      // Move o id para pessoas
      this.resourceForm.patchValue({ id: response.id });

      this.env.tabPanelOnOff = false;
    }).
    catch(error => {
      console.log(error);
      this.messageService.add({severity:'error', summary: 'Erro', detail:error.error[0].mensagemUsuario});
    });
  };

  protected updateResource() {
    const replacer = function (key, value) {
      if (this[key] instanceof Date) {
        //console.log('ENTROU RESOLVER DATA ' + moment(this[key]).format('DD/MM/YYYY'))
        return moment(this[key]).format('DD/MM/YYYY');
      }
      return value;
    };

    // Move o usuario para pessoas
    this.resourceForm.patchValue({
      usuario: JSON.parse(sessionStorage.getItem("usuario")).nome
    });

    const resource: PessoasJuridica = this.jsonDataToResourceFn(this.resourceForm.value);
    // copia os dados de Pessoas para pessoas
    let pessoasJuridica = JSON.stringify(resource,replacer);

    // Chama a funcao que grava/ faz update
    this.pessoasJuridicaService.updatePessoaJuridica(pessoasJuridica)
      .then(response => {
        this.messageService.add({severity:'success', summary: 'Sucesso', detail: 'Pessoa atualizada com sucesso!'});

        // redireciona para lista
        const baseComponentPath = this.route.snapshot.parent.url[0].path;
        this.router.navigateByUrl(baseComponentPath, { skipLocationChange: true }).then(() => {
          console.log(this.router);
          return this.router.navigate(["/" + baseComponentPath]);
        });
      }).
      catch(error => {
      console.log(error);
      console.log('fail');
      this.messageService.add({severity:'error', summary: 'Erro', detail:error.error[0].mensagemUsuario});
    });

  }

  changeStatus($event){
    if($event == 1){
      this.env.tabPanelOnOff = true;
      this.resourceForm.patchValue({ observacao: this.env.tabPanelOnOff });
      //console.log('TA NO IF = 1 ', this.env);
    } else {
      this.env.tabPanelOnOff = false;
      this.resourceForm.patchValue({ observacao: this.env.tabPanelOnOff });
      //console.log('TA NO ELSE ', this.env);
    }
  }
}
