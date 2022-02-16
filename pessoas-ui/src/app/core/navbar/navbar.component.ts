import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { Usuarios } from 'src/app/shared/models/usuarios';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})

export class NavbarComponent implements OnInit {

  constructor(private router: Router) { }

  display: boolean;

  user: Usuarios;

  items: MenuItem[];

  ngOnInit() {

    if (sessionStorage.getItem('usuario') === null) {
      this.router.navigate(['login']);
    } else {
      this.user = JSON.parse(sessionStorage.getItem('usuario'));
    }

    this.items = [
      {
        label: 'Cadastros',
        icon: 'pi pi-calendar-plus',
        items: [
          {label: 'Pessoas Jurídica', routerLink: 'pessoas-juridica', command: (event) => {this.display = false; } },
					{label: 'Pessoas', routerLink: 'pessoas', command: (event) => {this.display = false; } },
					{label: 'Situacoes', routerLink: 'situacoes', command: (event) => {this.display = false; } },
					{label: 'Profissoes', routerLink: 'profissoes', command: (event) => {this.display = false; } },
					{label: 'Etnias', routerLink: 'etnias', command: (event) => {this.display = false; } },
					{label: 'Tipos Vinculos Empresas', routerLink: 'tipos-vinculos-empresas', command: (event) => {this.display = false; } },
					{label: 'Tipos Relacionamentos', routerLink: 'tipos-relacionamentos', command: (event) => {this.display = false; } },
					{label: 'Tipos Estados Civis', routerLink: 'tipos-estados-civis', command: (event) => {this.display = false; } },
					{label: 'Tipos Documentos', routerLink: 'tipos-documentos', command: (event) => {this.display = false; } },
					{label: 'Tipos Contatos', routerLink: 'tipos-contatos', command: (event) => {this.display = false; } },
					{label: 'Tipos Pessoas', routerLink: 'tipos-pessoas', command: (event) => {this.display = false; } },
        ]
      },
      {
        label: 'Atividades',
        icon: 'pi pi-fw pi-sort',
        items: [
          //TELA
        ]
      },
      {
        label: 'Consultas',
        icon: 'pi pi-fw pi-question',
        items: [
          //TELA
        ]
      },
      {
        label: 'Relatorios',
        icon: 'pi pi-fw pi-cog',
        items: [
          //TELA
        ]
      }
      ,
      {
        label: 'Ajuda',
        icon: 'pi pi-fw pi-info',
        items: [
          //TELA
        ]
      }
    ];
  }

  logoff() {
    sessionStorage.removeItem('usuario');
    this.router.navigate(['login']);
  }

}
