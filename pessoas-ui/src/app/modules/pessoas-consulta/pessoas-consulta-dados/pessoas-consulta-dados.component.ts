import { Component, Injector, Input} from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { SelectItem } from 'primeng/api';
import { DialogService } from 'primeng';
import { MessageService } from 'primeng/api';
import {FormControl} from '@angular/forms'

import { environment } from 'src/environments/environment';

import { Pessoas } from 'src/app/shared/models/pessoas';
import { PessoasConsultaService } from '../pessoas-consulta.service';

import PessoasOutput from 'src/app/shared/models/pessoas-outputs';

@Component({
  selector: 'app-pessoas-consulta-dados',
  templateUrl: './pessoas-consulta-dados.component.html',
  styleUrls: ['./pessoas-consulta-dados.component.css'],

  template: '<input [textMask]="{mask: mask}" [(ngModel)]="myModel" type="text"/>'
})

export class PessoasConsultaDadosComponent extends BaseResourceFormComponent<Pessoas> {
  public myModel: string
  public modelWithValue: string
  public formControlInput: FormControl = new FormControl()
  public maskCpf: Array<string | RegExp>
  public maskCnpj: Array<string | RegExp>

  @Input() pessoaId: number;
  env = environment;
  ptBrLocale;

  //isCpfCnpjLen = 3

  //Move os dados da pessoas para o modal de cadastro de enderecos
  dadosPessoa=""//(<HTMLSelectElement>document.getElementById('nome')).value +' CPF: ' +
  //(<HTMLSelectElement>document.getElementById('cpf')).value

  constructor(
    protected pessoasConsultaService: PessoasConsultaService,
    protected injector: Injector,

  ) {
    super(injector, new Pessoas(), pessoasConsultaService, Pessoas.fromJson, new MessageService());
    this.ptBrLocale = this.loadLocale();

    this.maskCpf = [/[1-9]/, /\d/, /\d/,'.',/\d/, /\d/, /\d/,'.', /\d/, /\d/, /\d/,'-', /\d/, /\d/]

    //29.981.449/0001-60

    this.maskCnpj = [/[1-14]/, /\d/, /\d/,'.',/\d/, /\d/, /\d/,'.', /\d/, /\d/, /\d/,/\d/,'/',/\d/,/\d/,/\d/,'-', /\d/, /\d/]
    this.myModel = ''
    this.modelWithValue = ''
    this.formControlInput.setValue('')

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({

      id: [null],
      nome: [null],
      fisicaJuridica: [],
      dataCadastro: [null],
      observacao: [null],
      usuario: [null],
      dataAlteracao:[null],
      dataRegistro:[null],
      mesEnvioSicom:[null],
      anoEnvioSicom:[null],
      cpfCnpj: [null],

      tiposPessoas:
        this.formBuilder.group({
        descricao:[null],
      }),

      situacoes:
        this.formBuilder.group({
          descricao: [null]
      }),

      dadosPf: this.formBuilder.group({
        cpf: [null],
        nomeSocial: [null],
        sexo:[null],
        raca:[null],
        cor:[null],
        recebeBf:[null],
        mae:[null],
        pai:[null],
        cartaoSus:[null],

        distritos:
        this.formBuilder.group({
          nome: [null]
        }),

        tiposEstadosCivis:
        this.formBuilder.group({
          descricao: [null]
        }),

        etnias:
        this.formBuilder.group({
          descricao: [null]
        }),

      }),

    });

  }
}

