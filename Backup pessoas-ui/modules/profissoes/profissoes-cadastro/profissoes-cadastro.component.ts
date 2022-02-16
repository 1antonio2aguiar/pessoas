import { MessageService } from 'primeng/api';
import { Profissoes } from './../../../shared/models/profissoes';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { ProfissoesService } from '../profissoes.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-profissoes-cadastro',
  templateUrl: './profissoes-cadastro.component.html',
  styleUrls: ['./profissoes-cadastro.component.css']
})
export class ProfissoesCadastroComponent extends BaseResourceFormComponent<Profissoes> {

  constructor(
    protected profissoesService: ProfissoesService
  , protected injector: Injector) {

    super(injector, new Profissoes(), profissoesService, Profissoes.fromJson, new MessageService());

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
		id: [null],
		nome: [null, [Validators.required, Validators.minLength(5)]],
    });
  }

  protected creationPageTitle(): string {
    return 'Cadastro de Profissões';
  }

  protected editionPageTitle(): string {
    const profissoesName = this.resource.id || '';
    return 'Editando Profissão: ' + profissoesName;
  }

}
