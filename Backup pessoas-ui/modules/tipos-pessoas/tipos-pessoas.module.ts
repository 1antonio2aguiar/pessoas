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

import { TiposPessoasRoutingModule } from './tipos-pessoas-routing.module';
import { TiposPessoasCadastroComponent } from './tipos-pessoas-cadastro/tipos-pessoas-cadastro.component';
import { TiposPessoasPesquisaComponent } from './tipos-pessoas-pesquisa/tipos-pessoas-pesquisa.component';


@NgModule({
declarations: [TiposPessoasCadastroComponent, TiposPessoasPesquisaComponent],
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
    TiposPessoasRoutingModule
  ]
})
export class TiposPessoasModule { }
