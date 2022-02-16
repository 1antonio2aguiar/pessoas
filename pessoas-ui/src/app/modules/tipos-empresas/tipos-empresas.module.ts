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

import { TiposEmpresasCadastroComponent } from './tipos-empresas-cadastro/tipos-empresas-cadastro.component';
import { TiposEmpresasPesquisaComponent } from './tipos-empresas-pesquisa/tipos-empresas-pesquisa.component';
import { TiposEmpresasRoutingModule } from './tipos-empresas-routing.module';

@NgModule({
  declarations: [TiposEmpresasPesquisaComponent, TiposEmpresasCadastroComponent],
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
    TiposEmpresasRoutingModule
  ]
})
export class TiposEmpresasModule { }
