import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { iClientes } from '../interfaces/clientes';
import { ClientesComponent } from '../pages/clientes/clientes.component';

@Injectable({
  providedIn: 'root'
})
export class ClientesService {
  api = environment.api;
  endpoint = 'clientes';
  constructor(private http: HttpClient) { }

  listarTodosClientes(){
    return this.http.get<iClientes[]>(`${this.api}/${this.endpoint}/`)
  }

  getClientes(id: any){
    return this.http.get(`${this.api}/${this.endpoint}/${id}`)
  }

  cadastrar(cliente: iClientes){
    return this.http.post(`${this.api}/${this.endpoint}/`, cliente)

  }
}
