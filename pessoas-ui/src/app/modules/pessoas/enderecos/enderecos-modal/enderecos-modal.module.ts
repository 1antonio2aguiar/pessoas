import { SharedModule } from './../../../../shared/shared.module';
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


//import { EnderecosModalComponent } from './enderecos-modal.component';

@NgModule({
  declarations: [/*EnderecosModalComponent*/],
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
    DropdownModule,
    TabViewModule
  ],
  providers: [
    DialogService
  ],
})

export class EnderecosModalModule { }
