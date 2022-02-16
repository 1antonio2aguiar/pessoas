import { TiposRelacionamentosCadastroComponent } from './tipos-relacionamentos-cadastro/tipos-relacionamentos-cadastro.component';
import { TiposRelacionamentosPesquisaComponent } from './tipos-relacionamentos-pesquisa/tipos-relacionamentos-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [

{  path: '',        component: TiposRelacionamentosPesquisaComponent },
{  path: 'new',    component: TiposRelacionamentosCadastroComponent },
{  path: ':id/edit', component: TiposRelacionamentosCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TiposRelacionamentosRoutingModule { }
