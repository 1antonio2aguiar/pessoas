import { TiposPessoasCadastroComponent } from './tipos-pessoas-cadastro/tipos-pessoas-cadastro.component';
import { TiposPessoasPesquisaComponent } from './tipos-pessoas-pesquisa/tipos-pessoas-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [

{  path: '',        component: TiposPessoasPesquisaComponent },
{  path: 'new',    component: TiposPessoasCadastroComponent },
{  path: ':id/edit', component: TiposPessoasCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TiposPessoasRoutingModule { }
