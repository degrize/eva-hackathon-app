import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FenStartLayoutComponent } from './fen-start-layout.component';

describe('FenStartLayoutComponent', () => {
  let component: FenStartLayoutComponent;
  let fixture: ComponentFixture<FenStartLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FenStartLayoutComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(FenStartLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
