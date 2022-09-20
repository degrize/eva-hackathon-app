import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MandatairePortfolioComponent } from './mandataire-portfolio.component';

describe('MandatairePortfolioComponent', () => {
  let component: MandatairePortfolioComponent;
  let fixture: ComponentFixture<MandatairePortfolioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MandatairePortfolioComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(MandatairePortfolioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
