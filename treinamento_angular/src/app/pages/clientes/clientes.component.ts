import { Component, OnInit } from '@angular/core';
import { ClientesService } from 'src/app/services/clientes.service';
import { ActivatedRoute } from '@angular/router';
import { iClientes } from 'src/app/interfaces/clientes';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css']
})
export class ClientesComponent implements OnInit {

  constructor(private clienteService:ClientesService, private activatedRoute:ActivatedRoute ) { }
  idCliente: any = this.activatedRoute.snapshot.params['id'];
  clientes: iClientes[] = [];
  ngOnInit(): void {
    if(this.idCliente != undefined){
      this.getCliente(this.idCliente);
    }else{
      this.listarTodosClientes();
    }
  }

  listarTodosClientes(){
    this.clienteService.listarTodosClientes().subscribe((result: iClientes[]) => {
      this.clientes = result
      console.log(this.clientes);
    });
  }

  getCliente(idCliente: any){
    this.clienteService.getClientes(idCliente).subscribe((result: any) =>{
      this.clientes.push(result);
      console.log(this.clientes);
    });
  }

  filtrarClientes(nome:String){

    if(nome){
      nome = nome.toLocaleUpperCase();
    }

    this.clientes = this.clientes.filter((c: any)  => c.nome.toUpperCase().indexOf(nome) >= 0)

  }

  confirmar(id:null){
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire(
          'Deleted!',
          'Your file has been deleted.',
          'success'
        )
      }
    })
  }
}
