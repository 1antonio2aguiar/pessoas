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

import { TiposDocumentosRoutingModule } from './tipos-documentos-routing.module';
import { TiposDocumentosCadastroComponent } from './tipos-documentos-cadastro/tipos-documentos-cadastro.component';
import { TiposDocumentosPesquisaComponent } from './tipos-documentos-pesquisa/tipos-documentos-pesquisa.component';


@NgModule({
declarations: [TiposDocumentosCadastroComponent, TiposDocumentosPesquisaComponent],
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
    TiposDocumentosRoutingModule
  ]
})
export class TiposDocumentosModule { }
