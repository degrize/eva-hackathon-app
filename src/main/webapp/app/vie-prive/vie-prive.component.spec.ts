import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViePriveComponent } from './vie-prive.component';

describe('ViePriveComponent', () => {
  let component: ViePriveComponent;
  let fixture: ComponentFixture<ViePriveComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViePriveComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ViePriveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
