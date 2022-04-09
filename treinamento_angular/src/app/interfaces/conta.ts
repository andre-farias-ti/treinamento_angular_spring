import { iClientes } from "./clientes"

export interface Conta {
    agencia: string;
    numero: number;
    cliente: iClientes;
    saldo: number;
}
