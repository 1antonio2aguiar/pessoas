import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedModule } from './../../../shared/shared.module';
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

import { BairrosModalComponent } from './../bairros-modal/bairros-modal.component';
import { BairrosModalRoutingModule } from '../bairros-modal-routing/bairros-modal-routing.module';


@NgModule({
  declarations: [BairrosModalComponent],
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
    BairrosModalRoutingModule
  ]
})
export class BairrosModalModule { }
