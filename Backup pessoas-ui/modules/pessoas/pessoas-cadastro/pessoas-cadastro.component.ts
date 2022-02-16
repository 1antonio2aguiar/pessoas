import { SelectItem } from 'primeng/api';
import { DialogService } from 'primeng';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { Validators } from '@angular/forms';

import { MessageService } from 'primeng/api';

import { PessoasService } from '../pessoas.service';
import { Pessoas } from './../../../shared/models/pessoas';
import { TiposPessoasService } from './../../tipos-pessoas/tipos-pessoas.service';
import { SituacoesService } from './../../situacoes/situacoes.service';
import { TiposEstadosCivisService } from './../../tipos-estados-civis/tipos-estados-civis.service';
import { EtniasService } from './../../etnias/etnias.service';
import { DistritosModalComponent } from './../../distritos/distritos-modal/distritos-modal.component';

import * as moment from 'moment';

@Component({
  selector: 'app-pessoas-cadastro',
  templateUrl: './pessoas-cadastro.component.html',
  styleUrls: ['./pessoas-cadastro.component.css']
})

export class PessoasCadastroComponent extends BaseResourceFormComponent<Pessoas> {

  tipoPessoaList: SelectItem[];
  situacaoList: SelectItem[];
  estadosCivisList: SelectItem[];
  etniaList: SelectItem[];

  masks = {
    mask: [
      {
        mask: '000.000.000-00'
      }
    ]
  };

  sexo = [
    { value: 'M', selected: false, label: 'MASCULINO' },
    { value: 'F', selected: false, label: 'FEMININO' }
  ];

  recebeBf = [
    { value: 'N', selected: true, label: 'NÃO' },
    { value: 'S', selected: false, label: 'SIM' }
  ];

  raca = [
    {value: 1, selected: false, label: "BRANCA"},
    {value: 2, selected: false, label: "NEGRO"},
    {value: 3, selected: false,   label: "PARDA"},
    {value: 4, selected: false,   label: "AMARELO"},
    {value: 5, selected: false,  label: "INDÍGENA"},
    {value: 99, selected: false,   label: "OUTROS"}
  ];

  cor = [
    {value: 1, selected: false, label: "BRANCA"},
    {value: 2, selected: false, label: "NEGRO"},
    {value: 3, selected: false,   label: "PARDA"},
    {value: 4, selected: false,   label: "AMARELO"},
    {value: 5, selected: false,  label: "INDÍGENA"},
    {value: 99, selected: false,   label: "OUTROS"}
  ]

  ptBrLocale;

  constructor(

    protected pessoasService: PessoasService,
    protected tiposPessoasService: TiposPessoasService,
    protected situacoesService: SituacoesService,
    protected estadosCivisService: TiposEstadosCivisService,
    protected etniasService: EtniasService,
    protected injector: Injector,
    public dialogService: DialogService,/*
    public messageService: MessageService,*/

  ) {

    super(injector, new Pessoas(), pessoasService, Pessoas.fromJson, new MessageService());

    this.loadTipoPessoa();
    this.loadSituacao();
    this.loadEstadoCivil();
    this.loadEtnias();
    this.ptBrLocale = this.loadLocale();

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      id: [null],
		  dataCadastro: [null],
		  dataRegistro: [null],
		  fisicaJuridica: ["F"],
		  nome: [null, [Validators.required, Validators.minLength(5)]],
		  observacao: [null],

      situacoes:
        this.formBuilder.group({
          descricao:["ATIVO"],
          id: [1]
      }),

      tiposPessoas:
        this.formBuilder.group({
          descricao:[null],
          id: [null],
      }),

      dadosPf: this.formBuilder.group({
        cpf: [null],
        sexo: [null],

        tiposEstadosCivis:
        this.formBuilder.group({
          descricao:[null],
          id:[null],
        }),

        raca: [null],
        cor: [null],

        etnias:
          this.formBuilder.group({
            descricao:[null],
            id: [null],
        }),

        recebeBf: ["N"],
        localNascimento: [null],

        distritos: this.formBuilder.group({
          id: [null],
          nome: [null]
        }),
        mae: [null],
        pai: [null],
        nomeSocial: [null],
        cartaoSus:[],


      }),
      usuario: JSON.parse(sessionStorage.getItem("usuario")).nome,

    });

    /*this.resourceForm.patchValue({
      usuario: JSON.parse(sessionStorage.getItem("usuario")).nome,
    });*/
  }

  protected creationPageTitle(): string {
    return 'Novo Cadastro de Pessoa Física';
  }

  protected editionPageTitle(): string {
    return 'Editando Pessoas: ' ;
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

  loadSituacao() {
    this.situacoesService.listAll()
    .then(situacoes => {
      this.situacaoList = situacoes.map(c =>
        ({ label: c.descricao, value: c.id })
      );
    })
    .catch(erro => erro);
  }

  loadEstadoCivil() {
    this.estadosCivisService.listAll()
    .then(estadosCivis => {
      this.estadosCivisList = estadosCivis.map(ec =>
        ({ label: ec.descricao, value: ec.id})
      );
    })
    .catch(erro => erro);
  }

  loadEtnias() {
    this.etniasService.listAll()
    .then(etnias => {
      this.etniaList = etnias.map(et =>
        ({ label: et.descricao, value: et.id })
      );
    })
    .catch(erro => erro);
  }

  // Modal local nascimento
  showLocalNascimento($event) {

    const ref = this.dialogService.open(DistritosModalComponent, {
      header: 'Selecione o Local de Nascimento',
      width: '70%'
    });

    ref.onClose.subscribe((distritos) => {
      this.resourceForm.patchValue(
        {
          dadosPf: {
            distritos: {
              id: distritos.id,
              nome: distritos.cidades.nome + ' - ' +
              distritos.nome + ' - ' +
              distritos.cidades.estados.sigla
            },
            localNascimento: distritos.id
          }
        }
      );
    });
  }

  protected createResource() {

    const replacer = function (key, value) {

      if (this[key] instanceof Date) {
        return moment(this[key]).format('DD/MM/YYYY');
      }

      return value;
    }

    const resource: Pessoas = this.jsonDataToResourceFn(this.resourceForm.value);
    // copia os dados de Logradouros para logradouros
    let pessoas = JSON.stringify(resource/*,replacer*/);
    //console.log('PESSOAS ' + pessoas);

    // Chama a funcao que grava
    this.pessoasService.createPessoa(pessoas)
      .then(response => {
        console.log('VAI GAROTO ' + pessoas);

        this.messageService.add({severity:'success', summary: 'Sucesso', detail: 'Pessoa inserida com sucesso!'});

        //this.buildResourceForm(); -- limpa o formulario

        // redireciona para lista
        const baseComponentPath = this.route.snapshot.parent.url[0].path;
        this.router.navigateByUrl(baseComponentPath, { skipLocationChange: true }).then(() => {
          console.log(this.router);
          return this.router.navigate(["/" + baseComponentPath]);
        });

      }).
      catch(error => {
        console.log(error);
        this.messageService.add({severity:'error', summary: 'Erro', detail:error.error[0].mensagemUsuario});
    });
  }

}
