import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PaginaNaoEncontradaComponent } from './core/pagina-nao-encontrada.component';

const routes: Routes = [
	{path: 'pessoas',  loadChildren: () => import('./modules/pessoas/pessoas.module').then(m => m.PessoasModule)},
	{path: 'situacoes',  loadChildren: () => import('./modules/situacoes/situacoes.module').then(m => m.SituacoesModule)},
	{path: 'profissoes',  loadChildren: () => import('./modules/profissoes/profissoes.module').then(m => m.ProfissoesModule)},
	{path: 'etnias',  loadChildren: () => import('./modules/etnias/etnias.module').then(m => m.EtniasModule)},
	{path: 'tipos-vinculos-empresas',  loadChildren: () => import('./modules/tipos-vinculos-empresas/tipos-vinculos-empresas.module').then(m => m.TiposVinculosEmpresasModule)},
	{path: 'tipos-relacionamentos',  loadChildren: () => import('./modules/tipos-relacionamentos/tipos-relacionamentos.module').then(m => m.TiposRelacionamentosModule)},
	{path: 'tipos-estados-civis',  loadChildren: () => import('./modules/tipos-estados-civis/tipos-estados-civis.module').then(m => m.TiposEstadosCivisModule)},
	{path: 'tipos-documentos',  loadChildren: () => import('./modules/tipos-documentos/tipos-documentos.module').then(m => m.TiposDocumentosModule)},
	{path: 'tipos-contatos',  loadChildren: () => import('./modules/tipos-contatos/tipos-contatos.module').then(m => m.TiposContatosModule)},
	{path: 'tipos-pessoas',  loadChildren: () => import('./modules/tipos-pessoas/tipos-pessoas.module').then(m => m.TiposPessoasModule)},
  { path: 'home', loadChildren: () => import('./modules/home/home.module').then(m => m.HomeModule) },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', loadChildren: () => import('./seguranca/seguranca.module').then(m => m.SegurancaModule) },
  { path: 'troca-senha', loadChildren: () => import('./seguranca/seguranca.module').then(m => m.SegurancaModule) },
  { path: 'pagina-nao-encontrada', component: PaginaNaoEncontradaComponent },
  { path: '**', redirectTo: 'pagina-nao-encontrada' },

  {path: 'distritos',  loadChildren: () => import('./modules/distritos/distritos-modal/distritos-modal.module').then(m => m.DistritosModule)},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
