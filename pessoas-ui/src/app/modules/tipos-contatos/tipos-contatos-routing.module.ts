import { TiposContatosCadastroComponent } from './tipos-contatos-cadastro/tipos-contatos-cadastro.component';
import { TiposContatosPesquisaComponent } from './tipos-contatos-pesquisa/tipos-contatos-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [

{  path: '',        component: TiposContatosPesquisaComponent },
{  path: 'new',    component: TiposContatosCadastroComponent },
{  path: ':id/edit', component: TiposContatosCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TiposContatosRoutingModule { }
