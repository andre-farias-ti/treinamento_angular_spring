import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientesCadastrarEditarComponent } from './clientes-cadastrar-editar.component';

describe('ClientesCadastrarEditarComponent', () => {
  let component: ClientesCadastrarEditarComponent;
  let fixture: ComponentFixture<ClientesCadastrarEditarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientesCadastrarEditarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientesCadastrarEditarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
