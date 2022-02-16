import { DialogService } from 'primeng';
import { Component, Injector, Input } from '@angular/core';
import { BaseResourceFormComponent } from '../../../shared/components/base-resource-form/base-resource-form.component';
import { MessageService } from 'primeng/api';
import { ChangeDetectorRef, AfterContentChecked} from '@angular/core';

import { environment } from 'src/environments/environment';

import { PessoasJuridicaService } from '../pessoas-juridica.service';
import { PessoasJuridica } from './../../../shared/models/pessoas-juridica';
import { EnderecosService } from '../enderecos/enderecos.service';
import { ContatosService } from '../contatos/contatos.service';
import { DocumentosService } from '../../pessoas/documentos/documentos.service';
import { SociosService } from '../socios/socios.service';

@Component({
  selector: 'app-pessoas-juridica-tab-view',
  templateUrl: './pessoas-juridica-tab-view.component.html',
  styleUrls: ['./pessoas-juridica-tab-view.component.css']
})

export class PessoasJuridicaTabViewComponent extends BaseResourceFormComponent<PessoasJuridica> {

  ptBrLocale;
  selectedTab
  @Input() pessoaId: string;
  env = environment;

  constructor(
    protected pessoasJuridicaService: PessoasJuridicaService,
    protected enderecosService: EnderecosService,
    protected contatosService: ContatosService,
    protected documentosService: DocumentosService,
    protected sociosService: SociosService,
    protected injector: Injector,
    public dialogService: DialogService,
    public messageService: MessageService,

    // Isso aqui e para corrigir o erro: ExpressionChangedAfterItHasBeenCheckedError
    private cdref: ChangeDetectorRef,
  ) {
    super(injector, new PessoasJuridica(), pessoasJuridicaService, PessoasJuridica.fromJson, new MessageService());
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

  // Ao clicar nas abas dispara este evento
  onTabChange(event) {
    this.pessoaId = (<HTMLSelectElement>document.getElementById('id')).value;

    if (event.index == 1) {
      //console.log("CLICOU NA ABA ENDERECOS ")
      this.enderecosService.listAll(this.pessoaId);
      //console.log("VOLTOU ")
    }

    if (event.index == 2) {
      //console.log("CLICOU NA ABA CONTATOS ", this.env.tabPanelOnOff)
      this.contatosService.listAll(this.pessoaId);
    }

    if (event.index == 3) {
      //console.log("CLICOU NA ABA DOCUMENTOS ", this.env.tabPanelOnOff)
      this.documentosService.listAll(this.pessoaId);
    }

    if (event.index == 4) {
      //console.log("CLICOU NA ABA SÓCIOS ", this.env.tabPanelOnOff)
      this.sociosService.listAll(this.pessoaId);
    }



  }

}
