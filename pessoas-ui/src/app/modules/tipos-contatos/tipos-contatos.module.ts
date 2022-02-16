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


import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TiposContatosRoutingModule } from './tipos-contatos-routing.module';
import { TiposContatosCadastroComponent } from './tipos-contatos-cadastro/tipos-contatos-cadastro.component';
import { TiposContatosPesquisaComponent } from './tipos-contatos-pesquisa/tipos-contatos-pesquisa.component';


@NgModule({
declarations: [TiposContatosCadastroComponent, TiposContatosPesquisaComponent],
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
    TiposContatosRoutingModule
  ]
})
export class TiposContatosModule { }
