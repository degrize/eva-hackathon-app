import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TypeEchangesComponent } from './type-echanges.component';

describe('TypeEchangesComponent', () => {
  let component: TypeEchangesComponent;
  let fixture: ComponentFixture<TypeEchangesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TypeEchangesComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(TypeEchangesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
