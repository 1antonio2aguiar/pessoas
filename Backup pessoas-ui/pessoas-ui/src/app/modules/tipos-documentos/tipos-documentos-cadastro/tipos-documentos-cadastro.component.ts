import { MessageService } from 'primeng/api';
import { TiposDocumentos } from './../../../shared/models/tipos-documentos';
import { Component, Injector } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { TiposDocumentosService } from '../tipos-documentos.service';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-tipos-documentos-cadastro',
  templateUrl: './tipos-documentos-cadastro.component.html',
  styleUrls: ['./tipos-documentos-cadastro.component.css']
})
export class TiposDocumentosCadastroComponent extends BaseResourceFormComponent<TiposDocumentos> {


  constructor(
    protected tiposDocumentosService: TiposDocumentosService
  , protected injector: Injector) {

    super(injector, new TiposDocumentos(), tiposDocumentosService, TiposDocumentos.fromJson, new MessageService());

  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
		descricao: [null, [Validators.required, Validators.minLength(5)]],
		id: [null],
    });
  }

  protected creationPageTitle(): string {
    return 'Cadastro de Novo Tipo Documento';
  }

  protected editionPageTitle(): string {
    const tiposDocumentosName = this.resource.descricao || '';
    return 'Editando Tipo Documento: ' + tiposDocumentosName;
  }

}
