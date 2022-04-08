import { Component, Injector, Input, OnInit} from '@angular/core';
import { MessageService } from 'primeng/api';
import { BaseResourceFormComponent } from 'src/app/shared/components/base-resource-form/base-resource-form.component';

import { PessoasOrigem } from './../../../../shared/models/pessoas-origem';
import { PessoasConsultaOrigemService } from '../pessoas-consulta-origem.service';

@Component({
  selector: 'app-pessoas-consulta-origem',
  templateUrl: './pessoas-consulta-origem.component.html',
  styleUrls: ['./pessoas-consulta-origem.component.css']
})
export class PessoasConsultaOrigemComponent extends BaseResourceFormComponent<PessoasOrigem> {
  ptBrLocale;

  constructor(
    protected pessoasConsultaOrigemService: PessoasConsultaOrigemService,
    protected injector: Injector,)
  {
    super(injector, new PessoasOrigem(), pessoasConsultaOrigemService, PessoasOrigem.fromJson, new MessageService());
    this.ptBrLocale = this.loadLocale();
  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      id: [null],
    })
  }

}


