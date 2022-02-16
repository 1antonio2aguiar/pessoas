import { PessoasJuridica } from './../../shared/models/pessoas-juridica';
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

import { PessoasJuridicaPesquisaComponent } from './pessoas-juridica-pesquisa/pessoas-juridica-pesquisa.component';
import { PessoasJuridicaRoutingModule } from './pessoas-juridica-routing.module';
import { PessoasJuridicaCadastroComponent } from './pessoas-juridica-cadastro/pessoas-juridica-cadastro.component';
import { PessoasJuridicaTabViewComponent } from './pessoas-juridica-tab-view/pessoas-juridica-tab-view.component';

import { ContatosListComponent } from './contatos/contatos-list/contatos-list.component';
import { ContatosModalComponent } from './contatos/contatos-modal/contatos-modal.component';
import { EnderecosListComponent } from './enderecos/enderecos-list/enderecos-list.component';
import { EnderecosModalComponent } from './enderecos/enderecos-modal/enderecos-modal.component';
import { DocumentosListComponent } from './documentos/documentos-list/documentos-list.component';
import { DocumentosModalComponent } from './documentos/documentos-modal/documentos-modal.component';
import { SociosListComponent } from './socios/socios-list/socios-list.component';
import { SociosModalComponent } from './socios/socios-modal/socios-modal.component';

@NgModule({
  declarations: [PessoasJuridicaPesquisaComponent, PessoasJuridicaCadastroComponent,
    PessoasJuridicaTabViewComponent,ContatosModalComponent,ContatosListComponent,
    EnderecosModalComponent,EnderecosListComponent,DocumentosListComponent,
    DocumentosModalComponent,SociosListComponent,SociosModalComponent
  ],
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
    PessoasJuridicaRoutingModule,
    DropdownModule,
    TabViewModule
  ],
  providers: [
    DialogService
  ],
})
export class PessoasJuridicaModule { }
