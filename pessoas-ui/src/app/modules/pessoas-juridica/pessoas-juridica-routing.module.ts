
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PessoasJuridicaPesquisaComponent } from './pessoas-juridica-pesquisa/pessoas-juridica-pesquisa.component';
import { PessoasJuridicaTabViewComponent } from './pessoas-juridica-tab-view/pessoas-juridica-tab-view.component';

const routes: Routes = [
  {  path: '',        component: PessoasJuridicaPesquisaComponent },
  {  path: 'new',    component: PessoasJuridicaTabViewComponent },
  {  path: ':id/edit', component: PessoasJuridicaTabViewComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class PessoasJuridicaRoutingModule { }

