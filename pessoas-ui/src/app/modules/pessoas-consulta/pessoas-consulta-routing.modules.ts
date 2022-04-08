import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PessoasConsultaPesquisaComponent } from './pessoas-consulta-pesquisa/pessoas-consulta-pesquisa.component'
import { PessoasConsultaTabViewComponent } from './pessoas-consulta-tab-view/pessoas-consulta-tab-view.component';

const routes: Routes = [
  {  path: '', component: PessoasConsultaPesquisaComponent },
  {  path: 'new', component: PessoasConsultaTabViewComponent },
  {  path: ':id/edit', component: PessoasConsultaTabViewComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class PessoasConsultaRoutingModule { }
