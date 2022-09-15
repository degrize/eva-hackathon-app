import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MesEchangesComponent } from './mes-echanges.component';

describe('MesEchangesComponent', () => {
  let component: MesEchangesComponent;
  let fixture: ComponentFixture<MesEchangesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MesEchangesComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(MesEchangesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
