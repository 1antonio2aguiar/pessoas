import { MessageService } from 'primeng/api';
import { Situacoes } from './../../../shared/models/situacoes';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { SituacoesService } from '../situacoes.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-situacoes-cadastro',
  templateUrl: './situacoes-cadastro.component.html',
  styleUrls: ['./situacoes-cadastro.component.css']
})
export class SituacoesCadastroComponent extends BaseResourceFormComponent<Situacoes> {

  constructor(
    protected situacoesService: SituacoesService
  , protected injector: Injector) {

    super(injector, new Situacoes(), situacoesService, Situacoes.fromJson, new MessageService());

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      descricao: [null, [Validators.required, Validators.minLength(5)]],
      id: [null],
    });
  }

  protected creationPageTitle(): string {
    return 'Cadastro de Situações';
  }

  protected editionPageTitle(): string {
    const situacoesName = this.resource.descricao || '';
    return 'Editando Situação: ' + situacoesName;
  }

}
