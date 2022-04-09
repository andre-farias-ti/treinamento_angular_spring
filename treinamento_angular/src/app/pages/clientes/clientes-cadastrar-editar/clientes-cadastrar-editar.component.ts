import { variable } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { iClientes } from 'src/app/interfaces/clientes';
import { ClientesService } from 'src/app/services/clientes.service';

@Component({
  selector: 'app-clientes-cadastrar-editar',
  templateUrl: './clientes-cadastrar-editar.component.html',
  styleUrls: ['./clientes-cadastrar-editar.component.css']
})
export class ClientesCadastrarEditarComponent implements OnInit {

  formCliente: FormGroup = new FormGroup({
    id: new FormControl(null),
    nome: new FormControl('',Validators.required),
    cpf: new FormControl('',Validators.required),
    email: new FormControl('',[Validators.required, Validators.email]),
    observacoes: new FormControl(''),
    ativo: new FormControl(true)
  })
  constructor(private clienteService: ClientesService) { }

  ngOnInit(): void {
  }

  enviar(){
    const cliente: iClientes = this.formCliente.value
    this.clienteService.cadastrar(cliente).subscribe(result => {
      console.log(result);
    });
  }

}
