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

import { TiposRelacionamentosRoutingModule } from './tipos-relacionamentos-routing.module';
import { TiposRelacionamentosCadastroComponent } from './tipos-relacionamentos-cadastro/tipos-relacionamentos-cadastro.component';
import { TiposRelacionamentosPesquisaComponent } from './tipos-relacionamentos-pesquisa/tipos-relacionamentos-pesquisa.component';


@NgModule({
declarations: [TiposRelacionamentosCadastroComponent, TiposRelacionamentosPesquisaComponent],
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
    TiposRelacionamentosRoutingModule
  ]
})
export class TiposRelacionamentosModule { }
