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

import { TiposVinculosEmpresasRoutingModule } from './tipos-vinculos-empresas-routing.module';
import { TiposVinculosEmpresasCadastroComponent } from './tipos-vinculos-empresas-cadastro/tipos-vinculos-empresas-cadastro.component';
import { TiposVinculosEmpresasPesquisaComponent } from './tipos-vinculos-empresas-pesquisa/tipos-vinculos-empresas-pesquisa.component';


@NgModule({
declarations: [TiposVinculosEmpresasCadastroComponent, TiposVinculosEmpresasPesquisaComponent],
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
    TiposVinculosEmpresasRoutingModule
  ]
})
export class TiposVinculosEmpresasModule { }
