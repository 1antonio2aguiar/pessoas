import { HttpParams } from '@angular/common/http';

export class PessoasEmpresasModalFiltro {
    pagina = 0;
    itensPorPagina = 7;
    totalRegistros = 0;
    params = new HttpParams();
}
