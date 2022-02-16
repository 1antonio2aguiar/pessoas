import { TiposVinculosEmpresasCadastroComponent } from './tipos-vinculos-empresas-cadastro/tipos-vinculos-empresas-cadastro.component';
import { TiposVinculosEmpresasPesquisaComponent } from './tipos-vinculos-empresas-pesquisa/tipos-vinculos-empresas-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [

{  path: '',        component: TiposVinculosEmpresasPesquisaComponent },
{  path: 'new',    component: TiposVinculosEmpresasCadastroComponent },
{  path: ':id/edit', component: TiposVinculosEmpresasCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TiposVinculosEmpresasRoutingModule { }
