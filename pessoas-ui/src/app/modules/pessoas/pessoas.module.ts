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

import { PessoasRoutingModule } from './pessoas-routing.module';
import { PessoasCadastroComponent } from './pessoas-cadastro/pessoas-cadastro.component';
import { PessoasPesquisaComponent } from './pessoas-pesquisa/pessoas-pesquisa.component';
import { PessoasCadastroViewComponent } from './pessoas-cadastro-view/pessoas-cadastro-view.component';
import { EnderecosListComponent } from './enderecos/enderecos-list/enderecos-list.component';
import { EnderecosModalComponent } from './enderecos/enderecos-modal/enderecos-modal.component';
import { ContatosListComponent } from './contatos-list/contatos-list.component';
import { ContatosModalComponent } from './contatos-modal/contatos-modal.component'
import { DocumentosListComponent } from './documentos/documentos-list/documentos-list.component';
import { DocumentosModalComponent } from './documentos/documentos-modal/documentos-modal.component';
import { EmpresasListComponent } from './empresas/empresas-list/empresas-list.component';
import { EmpresasModalComponent } from './empresas/empresas-modal/empresas-modal.component';


@NgModule({
declarations: [PessoasCadastroComponent, PessoasPesquisaComponent, PessoasCadastroViewComponent,
              EnderecosListComponent,EnderecosModalComponent, ContatosListComponent, ContatosModalComponent,
              DocumentosListComponent, DocumentosModalComponent, EmpresasListComponent, EmpresasModalComponent
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
    PessoasRoutingModule,
    DropdownModule,
    TabViewModule
  ],
  providers: [
    DialogService
  ],
})
export class PessoasModule { }
