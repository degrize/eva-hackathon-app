import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnoncesDemandesComponent } from './annonces-demandes.component';

describe('AnnoncesDemandesComponent', () => {
  let component: AnnoncesDemandesComponent;
  let fixture: ComponentFixture<AnnoncesDemandesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AnnoncesDemandesComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AnnoncesDemandesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
