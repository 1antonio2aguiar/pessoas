import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TiposEmpresasPesquisaComponent } from './tipos-empresas-pesquisa/tipos-empresas-pesquisa.component';
import { TiposEmpresasCadastroComponent } from './tipos-empresas-cadastro/tipos-empresas-cadastro.component';
const routes: Routes = [
  {  path: '',        component: TiposEmpresasPesquisaComponent },
  {  path: 'new',    component: TiposEmpresasCadastroComponent },
  {  path: ':id/edit', component: TiposEmpresasCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class TiposEmpresasRoutingModule { }
