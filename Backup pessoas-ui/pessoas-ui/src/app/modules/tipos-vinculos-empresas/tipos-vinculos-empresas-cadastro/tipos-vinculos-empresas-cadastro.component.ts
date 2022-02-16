import { MessageService } from 'primeng/api';
import { TiposVinculosEmpresas } from './../../../shared/models/tipos-vinculos-empresas';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { TiposVinculosEmpresasService } from '../tipos-vinculos-empresas.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-tipos-vinculos-empresas-cadastro',
  templateUrl: './tipos-vinculos-empresas-cadastro.component.html',
  styleUrls: ['./tipos-vinculos-empresas-cadastro.component.css']
})
export class TiposVinculosEmpresasCadastroComponent extends BaseResourceFormComponent<TiposVinculosEmpresas> {


  constructor(
    protected tiposVinculosEmpresasService: TiposVinculosEmpresasService
  , protected injector: Injector) {

    super(injector, new TiposVinculosEmpresas(), tiposVinculosEmpresasService, TiposVinculosEmpresas.fromJson, new MessageService());

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
		descricao: [null, [Validators.required, Validators.minLength(4)]],
		id: [null],
    });
  }

  protected creationPageTitle(): string {
    return 'Cadastro de Novo Tipo Vinculo Empresas';
  }

  protected editionPageTitle(): string {
    const tiposVinculosEmpresasName = this.resource.descricao || '';
    return 'Editando Tipo Vinculo Empresas: ' + tiposVinculosEmpresasName;
  }

}
