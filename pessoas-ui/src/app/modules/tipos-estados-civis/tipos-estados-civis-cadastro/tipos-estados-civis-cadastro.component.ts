import { MessageService } from 'primeng/api';
import { TiposEstadosCivis } from './../../../shared/models/tipos-estados-civis';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { TiposEstadosCivisService } from '../tipos-estados-civis.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-tipos-estados-civis-cadastro',
  templateUrl: './tipos-estados-civis-cadastro.component.html',
  styleUrls: ['./tipos-estados-civis-cadastro.component.css']
})
export class TiposEstadosCivisCadastroComponent extends BaseResourceFormComponent<TiposEstadosCivis> {


  constructor(
    protected tiposEstadosCivisService: TiposEstadosCivisService
  , protected injector: Injector) {

    super(injector, new TiposEstadosCivis(), tiposEstadosCivisService, TiposEstadosCivis.fromJson, new MessageService());

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
		descricao: [null, [Validators.required, Validators.minLength(3)]],
		id: [null],
		sigla: [null,[Validators.required,Validators.maxLength(1)]],
    });
  }

  protected creationPageTitle(): string {
    return 'Cadastro de Novo Estado Civil';
  }

  protected editionPageTitle(): string {
    const tiposEstadosCivisName = this.resource.descricao || '';
    return 'Editando Estado Civil: ' + tiposEstadosCivisName;
  }

}
