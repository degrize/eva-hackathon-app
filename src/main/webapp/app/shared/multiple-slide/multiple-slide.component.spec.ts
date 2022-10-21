import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MultipleSlideComponent } from './multiple-slide.component';

describe('MultipleSlideComponent', () => {
  let component: MultipleSlideComponent;
  let fixture: ComponentFixture<MultipleSlideComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MultipleSlideComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(MultipleSlideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
