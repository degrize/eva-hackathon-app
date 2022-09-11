import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegleConditionComponent } from './regle-condition.component';

describe('RegleConditionComponent', () => {
  let component: RegleConditionComponent;
  let fixture: ComponentFixture<RegleConditionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegleConditionComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(RegleConditionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
