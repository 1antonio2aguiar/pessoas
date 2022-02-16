import { MessageService } from 'primeng/api';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { Validators } from '@angular/forms';

import { TiposEmpresas } from './../../../shared/models/tipos-empresas';
import { TiposEmpresasService } from '../tipos-empresas.service';

@Component({
  selector: 'app-tipos-empresas-cadastro',
  templateUrl: './tipos-empresas-cadastro.component.html',
  styleUrls: ['./tipos-empresas-cadastro.component.css']
})

export class TiposEmpresasCadastroComponent extends BaseResourceFormComponent<TiposEmpresas> {

  constructor(
    protected tiposEmpresasService: TiposEmpresasService,
    protected injector: Injector) {

    super(injector, new TiposEmpresas(), tiposEmpresasService, TiposEmpresas.fromJson, new MessageService());

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
		  descricao: [null, [Validators.required, Validators.minLength(5)]],
		  id: [null],
    });
  }

  protected creationPageTitle(): string {
    return 'Cadastro de Novo Tipo Empresa';
  }

  protected editionPageTitle(): string {
    const tiposEmpresasName = this.resource.descricao || '';
    return 'Editando Tipo Empresa: ' + tiposEmpresasName;
  }

}
