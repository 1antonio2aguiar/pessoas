import { MessageService } from 'primeng/api';
import { Etnias } from './../../../shared/models/etnias';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { EtniasService } from '../etnias.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-etnias-cadastro',
  templateUrl: './etnias-cadastro.component.html',
  styleUrls: ['./etnias-cadastro.component.css']
})
export class EtniasCadastroComponent extends BaseResourceFormComponent<Etnias> {


  constructor(
    protected etniasService: EtniasService
  , protected injector: Injector) {

    super(injector, new Etnias(), etniasService, Etnias.fromJson, new MessageService());

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      id: [null],
		  descricao: [null, [Validators.required, Validators.minLength(5)]],
      codCadSus:[null, [Validators.required, Validators.minLength(4)]],
    });
  }

  protected creationPageTitle(): string {
    return 'Cadastro de Nova Etnia';
  }

  protected editionPageTitle(): string {
    const etniasName = this.resource.descricao || '';
    return 'Editando Etnia: ' + etniasName;
  }

}
