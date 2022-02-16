import { MessageService } from 'primeng/api';
import { TiposRelacionamentos } from './../../../shared/models/tipos-relacionamentos';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { TiposRelacionamentosService } from '../tipos-relacionamentos.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-tipos-relacionamentos-cadastro',
  templateUrl: './tipos-relacionamentos-cadastro.component.html',
  styleUrls: ['./tipos-relacionamentos-cadastro.component.css']
})
export class TiposRelacionamentosCadastroComponent extends BaseResourceFormComponent<TiposRelacionamentos> {


  constructor(
    protected tiposRelacionamentosService: TiposRelacionamentosService
  , protected injector: Injector) {

    super(injector, new TiposRelacionamentos(), tiposRelacionamentosService, TiposRelacionamentos.fromJson, new MessageService());

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
		descricao: [null, [Validators.required, Validators.minLength(3)]],
		id: [null],
    });
  }

  protected creationPageTitle(): string {
    return 'Cadastro de Novo Tipo Relacionamento';
  }

  protected editionPageTitle(): string {
    const tiposRelacionamentosName = this.resource.descricao || '';
    return 'Editando Tipo Relacionamento: ' + tiposRelacionamentosName;
  }

}
