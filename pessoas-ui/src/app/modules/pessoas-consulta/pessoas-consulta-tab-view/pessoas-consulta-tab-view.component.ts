import { DialogService } from 'primeng';
import { Component, Injector, Input } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { MessageService } from 'primeng/api';
import { ChangeDetectorRef, AfterContentChecked} from '@angular/core';

import { environment } from 'src/environments/environment';

import { EnderecosService } from '../../pessoas-juridica/enderecos/enderecos.service';
import { ContatosService } from '../../pessoas-juridica/contatos/contatos.service';
import { DocumentosService } from '../../pessoas-juridica/documentos/documentos.service';
import { PessoasConsultaOrigemService } from '../origem/pessoas-consulta-origem.service';
import { Pessoas } from 'src/app/shared/models/pessoas';

@Component({
  selector: 'app-pessoas-consulta-tab-view',
  templateUrl: './pessoas-consulta-tab-view.component.html',
  styleUrls: ['./pessoas-consulta-tab-view.component.css']
})

export class PessoasConsultaTabViewComponent extends BaseResourceFormComponent<Pessoas> {

  ptBrLocale;
  selectedTab
  @Input() pessoaId: string;
  env = environment;

  constructor(
    protected enderecosService: EnderecosService,
    protected contatosService: ContatosService,
    protected documentosService: DocumentosService,
    protected pessoasConsultaOrigemService: PessoasConsultaOrigemService,
    protected injector: Injector,
    public dialogService: DialogService,
    public messageService: MessageService,

    // Isso aqui e para corrigir o erro: ExpressionChangedAfterItHasBeenCheckedError
    private cdref: ChangeDetectorRef,
  ) {
    super(injector, new Pessoas(), enderecosService, Pessoas.fromJson, new MessageService());
    this.ptBrLocale = this.loadLocale();
  }

  // Isso aqui e para corrigir o erro: ExpressionChangedAfterItHasBeenCheckedError
  ngAfterContentChecked() {
    this.cdref.detectChanges();
  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({

    })
  }

  onTabChange(event) {
    this.pessoaId = (<HTMLSelectElement>document.getElementById('id')).value;

    if (event.index == 1) {
      this.enderecosService.listAll(this.pessoaId);
    }

    if (event.index == 2) {
      this.contatosService.listAll(this.pessoaId);
    }

    if (event.index == 3) {
      this.documentosService.listAll(this.pessoaId);
    }

    if (event.index == 4) {
      this.pessoasConsultaOrigemService.findByPessoasCdUnico(this.pessoaId);
    }
  }


}

