import { ProfissoesCadastroComponent } from './profissoes-cadastro/profissoes-cadastro.component';
import { ProfissoesPesquisaComponent } from './profissoes-pesquisa/profissoes-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [

{  path: '',        component: ProfissoesPesquisaComponent },
{  path: 'new',    component: ProfissoesCadastroComponent },
{  path: ':id/edit', component: ProfissoesCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfissoesRoutingModule { }
