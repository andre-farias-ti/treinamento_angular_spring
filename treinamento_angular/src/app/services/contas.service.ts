import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ContasService {

  api = environment.api;
  endpoint = 'contas';
  constructor(private http: HttpClient) { }

  listarTodosContas(){
    return this.http.get(`${this.api}/${this.endpoint}/`)
  }
}
