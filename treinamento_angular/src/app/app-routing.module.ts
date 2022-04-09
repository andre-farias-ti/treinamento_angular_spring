import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientesCadastrarEditarComponent } from './pages/clientes/clientes-cadastrar-editar/clientes-cadastrar-editar.component';
import { ClientesComponent } from './pages/clientes/clientes.component';
import { ContasComponent } from './pages/contas/contas.component';

const routes: Routes = [
  {
    path: 'clientes',
    component: ClientesComponent
  },
  {
    path: 'contas',
    component: ContasComponent
  },
  {
    path: 'clientes/visualizar/:id',
    component: ClientesComponent
  },
  {
    path: 'clientes/cadastrar',
    component: ClientesCadastrarEditarComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
