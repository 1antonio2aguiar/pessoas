import { SharedModule } from './../../shared/shared.module';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { IMaskModule } from 'angular-imask';
import { CalendarModule } from 'primeng/calendar';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { KeyFilterModule } from 'primeng/keyfilter';
import { TableModule } from 'primeng/table';
import { PanelModule } from 'primeng/panel';
import { DropdownModule } from 'primeng/dropdown';
import { DialogService } from 'primeng/dynamicdialog';
import { TabViewModule } from 'primeng/tabview';

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TextMaskModule } from 'angular2-text-mask';
import { FormsModule } from '@angular/forms';

import { PessoasConsultaRoutingModule } from './pessoas-consulta-routing.modules';
import { PessoasConsultaPesquisaComponent } from './pessoas-consulta-pesquisa/pessoas-consulta-pesquisa.component';
import { PessoasConsultaTabViewComponent } from './pessoas-consulta-tab-view/pessoas-consulta-tab-view.component';
import { PessoasConsultaDadosComponent } from './pessoas-consulta-dados/pessoas-consulta-dados.component';
import { PessoasConsultaEnderecosComponent } from './enderecos/pessoas-consulta-enderecos/pessoas-consulta-enderecos.component';
import { PessoasConsultaContatosComponent } from './contatos/pessoas-consulta-contatos/pessoas-consulta-contatos.component';
import { PessoasConsultaDocumentosComponent } from './documentos/pessoas-consulta-documentos/pessoas-consulta-documentos.component';
import { PessoasConsultaOrigemComponent } from './origem/pessoas-consulta-origem/pessoas-consulta-origem.component';

@NgModule({
  declarations: [PessoasConsultaPesquisaComponent, PessoasConsultaTabViewComponent,
                PessoasConsultaDadosComponent, PessoasConsultaEnderecosComponent,
                PessoasConsultaContatosComponent, PessoasConsultaDocumentosComponent,
                PessoasConsultaOrigemComponent ],
  imports: [
    SharedModule,
    IMaskModule,
    CalendarModule,
    CardModule,
    InputTextModule,
    KeyFilterModule,
    TableModule,
    PanelModule,
    MessagesModule,
    MessageModule,
    ToastModule,
    CommonModule,
    PessoasConsultaRoutingModule,
    DropdownModule,
    TabViewModule,
    FormsModule,
    TextMaskModule,
  ],providers: [
    DialogService
  ],
})
export class PessoasConsultaModule { }
