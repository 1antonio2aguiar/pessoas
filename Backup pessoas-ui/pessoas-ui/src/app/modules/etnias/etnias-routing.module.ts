import { EtniasCadastroComponent } from './etnias-cadastro/etnias-cadastro.component';
import { EtniasPesquisaComponent } from './etnias-pesquisa/etnias-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [

{  path: '',        component: EtniasPesquisaComponent },
{  path: 'new',    component: EtniasCadastroComponent },
{  path: ':id/edit', component: EtniasCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EtniasRoutingModule { }
