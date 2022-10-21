import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeAnnoncesComponent } from './home-annonces.component';

describe('HomeAnnoncesComponent', () => {
  let component: HomeAnnoncesComponent;
  let fixture: ComponentFixture<HomeAnnoncesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HomeAnnoncesComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(HomeAnnoncesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
