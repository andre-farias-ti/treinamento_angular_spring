import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ContasService } from 'src/app/services/contas.service';

@Component({
  selector: 'app-contas',
  templateUrl: './contas.component.html',
  styleUrls: ['./contas.component.css']
})
export class ContasComponent implements OnInit {

  constructor(private contasService:ContasService, private router: Router) { }
  contas: any = [];
  ngOnInit(): void {
    this.listarTodosContas();
  }

  listarTodosContas(){
    this.contasService.listarTodosContas().subscribe((result: any) =>{
      this.contas = result
      console.log(this.contas);
    });
  }

  goCliente(idCliente: any){

    this.router.navigate([`/clientes/visualizar/${idCliente}`]);

  }

}
