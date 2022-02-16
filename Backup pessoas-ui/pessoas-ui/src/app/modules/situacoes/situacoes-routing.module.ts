import { SituacoesCadastroComponent } from './situacoes-cadastro/situacoes-cadastro.component';
import { SituacoesPesquisaComponent } from './situacoes-pesquisa/situacoes-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [

{  path: '',        component: SituacoesPesquisaComponent },
{  path: 'new',    component: SituacoesCadastroComponent },
{  path: ':id/edit', component: SituacoesCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SituacoesRoutingModule { }
