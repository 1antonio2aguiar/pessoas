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

import { TiposEstadosCivisRoutingModule } from './tipos-estados-civis-routing.module';
import { TiposEstadosCivisCadastroComponent } from './tipos-estados-civis-cadastro/tipos-estados-civis-cadastro.component';
import { TiposEstadosCivisPesquisaComponent } from './tipos-estados-civis-pesquisa/tipos-estados-civis-pesquisa.component';


@NgModule({
declarations: [TiposEstadosCivisCadastroComponent, TiposEstadosCivisPesquisaComponent],
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
    TiposEstadosCivisRoutingModule
  ]
})
export class TiposEstadosCivisModule { }
