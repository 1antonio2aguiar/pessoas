import { MessageService } from 'primeng/api';
import { TiposContatos } from './../../../shared/models/tipos-contatos';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { TiposContatosService } from '../tipos-contatos.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-tipos-contatos-cadastro',
  templateUrl: './tipos-contatos-cadastro.component.html',
  styleUrls: ['./tipos-contatos-cadastro.component.css']
})
export class TiposContatosCadastroComponent extends BaseResourceFormComponent<TiposContatos> {


  constructor(
    protected tiposContatosService: TiposContatosService
  , protected injector: Injector) {

    super(injector, new TiposContatos(), tiposContatosService, TiposContatos.fromJson, new MessageService());

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
		descricao: [null, [Validators.required, Validators.minLength(3)]],
		id: [null],
    });
  }

  protected creationPageTitle(): string {
    return 'Cadastro de Novo Tipo Contato';
  }

  protected editionPageTitle(): string {
    const tiposContatosName = this.resource.descricao || '';
    return 'Editando Tipo Contato: ' + tiposContatosName;
  }

}
