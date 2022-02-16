import { TiposEstadosCivisCadastroComponent } from './tipos-estados-civis-cadastro/tipos-estados-civis-cadastro.component';
import { TiposEstadosCivisPesquisaComponent } from './tipos-estados-civis-pesquisa/tipos-estados-civis-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [

{  path: '',        component: TiposEstadosCivisPesquisaComponent },
{  path: 'new',    component: TiposEstadosCivisCadastroComponent },
{  path: ':id/edit', component: TiposEstadosCivisCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TiposEstadosCivisRoutingModule { }
