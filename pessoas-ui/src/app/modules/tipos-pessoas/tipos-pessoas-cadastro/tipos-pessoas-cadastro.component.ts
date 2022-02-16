import { MessageService } from 'primeng/api';
import { TiposPessoas } from './../../../shared/models/tipos-pessoas';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { TiposPessoasService } from '../tipos-pessoas.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-tipos-pessoas-cadastro',
  templateUrl: './tipos-pessoas-cadastro.component.html',
  styleUrls: ['./tipos-pessoas-cadastro.component.css']
})
export class TiposPessoasCadastroComponent extends BaseResourceFormComponent<TiposPessoas> {


  constructor(
    protected tiposPessoasService: TiposPessoasService
  , protected injector: Injector) {

    super(injector, new TiposPessoas(), tiposPessoasService, TiposPessoas.fromJson, new MessageService());

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      id: [null],
		  descricao: [null, [Validators.required, Validators.minLength(5)]],
    });
  }

  protected creationPageTitle(): string {
    return 'Cadastro: Novo Tipo de Pessoa';
  }

  protected editionPageTitle(): string {
    const tiposPessoasName = this.resource.descricao || '';
    return 'Editando Tipo Pessoa: ' + tiposPessoasName;
  }

}
