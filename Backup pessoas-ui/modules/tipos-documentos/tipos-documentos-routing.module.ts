import { TiposDocumentosCadastroComponent } from './tipos-documentos-cadastro/tipos-documentos-cadastro.component';
import { TiposDocumentosPesquisaComponent } from './tipos-documentos-pesquisa/tipos-documentos-pesquisa.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [

{  path: '',        component: TiposDocumentosPesquisaComponent },
{  path: 'new',    component: TiposDocumentosCadastroComponent },
{  path: ':id/edit', component: TiposDocumentosCadastroComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TiposDocumentosRoutingModule { }
