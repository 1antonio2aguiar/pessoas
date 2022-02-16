import { SelectItem } from 'primeng/api';
import { DialogService } from 'primeng';
import { MessageService } from 'primeng/api';
import { Component, Injector, Input } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { Validators } from '@angular/forms';

import { environment } from 'src/environments/environment';

import { PessoasService } from '../pessoas.service';
import { Pessoas } from './../../../shared/models/pessoas';
import { TiposPessoasService } from './../../tipos-pessoas/tipos-pessoas.service';
import { SituacoesService } from './../../situacoes/situacoes.service';
import { TiposEstadosCivisService } from './../../tipos-estados-civis/tipos-estados-civis.service';
import { EtniasService } from './../../etnias/etnias.service';
import { DistritosModalComponent } from './../../distritos/distritos-modal/distritos-modal.component';

import * as moment from 'moment';

@Component({
  selector: 'app-pessoas-cadastro-view',
  templateUrl: './pessoas-cadastro-view.component.html',
  styleUrls: ['./pessoas-cadastro-view.component.css']
})

export class PessoasCadastroViewComponent extends BaseResourceFormComponent<Pessoas> {

  @Input() pessoaId: number;
  env = environment;

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
    public dialogService: DialogService ,
    public messageService: MessageService,

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
		nome: [null, [Validators.required, Validators.minLength(5)]],
		fisicaJuridica: ["F"],
		dataCadastro: [null],
		dataRegistro: [null],
		observacao: [null],
		usuario: JSON.parse(sessionStorage.getItem("usuario")).nome,
		dataAlteracao:[null],

		mesEnvioSicom:[null],
		anoEnvioSicom:[null],

		tiposPessoas:
			this.formBuilder.group({
			descricao:[null],
			id: [null],
		}),

		dadosPf: this.formBuilder.group({
			cpf: [null],
			nomeSocial: [null],
			raca: [null],
			etnias:
			  this.formBuilder.group({
        id: [null],
				descricao:[null],
        codCadsus:[null]
			}),
			cor: [null],
			recebeBf: ["N"],
			cartaoSus:[],
			sexo: [null],

			tiposEstadosCivis:
				this.formBuilder.group({
        id:[null],
        sigla:[null],
				descricao:[null],
			}),
			distritos:
        this.formBuilder.group({
        id: [null],
			  nome: [null],
			}),
      localNascimento: [null],
			mae: [null],
			pai: [null],
		}),

		situacoes:
			this.formBuilder.group({
				descricao:["ATIVO"],
				id: [1]
		  }),
    });


    /*this.resourceForm.patchValue({
      usuario: JSON.parse(sessionStorage.getItem("usuario")).nome,
    });*/
  }

  protected creationPageTitle(): string {
    return 'Novo Cadastro de Pessoa Física';
  }

  protected editionPageTitle(): string {
    this.env.tabPanelOnOff = false;
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

  submitForm() {
    //this.submittingForm = true;
    if (this.currentAction === 'new') {
      // clicou no + (novo)
      this.createResource();
    } else {
        // clicou no update
        this.updateResource();
    }
  }

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

    const resource: Pessoas = this.jsonDataToResourceFn(this.resourceForm.value);
    // copia os dados de Pessoas para pessoas
    let pessoas = JSON.stringify(resource,replacer);

    // Chama a funcao que grava/ faz update
    this.pessoasService.updatePessoa(pessoas)
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

  protected createResource() {
    const replacer = function (key, value) {
      if (this[key] instanceof Date) {
        //console.log('ENTROU RESOLVER DATA ' + moment(this[key]).format('DD/MM/YYYY'))
        return moment(this[key]).format('DD/MM/YYYY');
      }
      return value;
    };

    const resource: Pessoas = this.jsonDataToResourceFn(this.resourceForm.value);
    // copia os dados de Pessoas para pessoas
    let pessoas = JSON.stringify(resource,replacer);

    // Chama a funcao que grava
    this.pessoasService.createPessoa(pessoas)
      .then(response => {
        this.messageService.add({severity:'success', summary: 'Sucesso', detail: 'Pessoa inserida com sucesso!'});

        // Move o id para pessoas
        this.resourceForm.patchValue({
          id: response.id
        });
        //this.buildResourceForm(); -- limpa o formulario

        this.env.tabPanelOnOff = false;

        // redireciona para lista
        /*const baseComponentPath = this.route.snapshot.parent.url[0].path;
        this.router.navigateByUrl(baseComponentPath, { skipLocationChange: true }).then(() => {
          console.log(this.router);
          return this.router.navigate(["/" + baseComponentPath]);
        });*/

      }).
      catch(error => {
        console.log(error);
        this.messageService.add({severity:'error', summary: 'Erro', detail:error.error[0].mensagemUsuario});
    });
  }

}

