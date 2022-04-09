import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Observable } from 'rxjs';
import {filter, map} from 'rxjs/operators';
import { ClientesComponent } from 'src/app/pages/clientes/clientes.component';
import { ClientesService } from 'src/app/services/clientes.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private router:Router, private clientesService : ClientesService) { }

  public url: string | undefined;
  public nomePesquisa:any;

  ngOnInit(): void {
  }

  pesquisar(){

    const url = this.router.url;

    if(url == '/clientes'){
      //this.clientesComponent.filtrarClientes(this.nomePesquisa);
    }

  }

}
