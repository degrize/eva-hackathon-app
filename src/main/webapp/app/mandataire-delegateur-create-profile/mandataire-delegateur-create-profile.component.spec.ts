import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MandataireDelegateurCreateProfileComponent } from './mandataire-delegateur-create-profile.component';

describe('MandataireDelegateurCreateProfileComponent', () => {
  let component: MandataireDelegateurCreateProfileComponent;
  let fixture: ComponentFixture<MandataireDelegateurCreateProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MandataireDelegateurCreateProfileComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(MandataireDelegateurCreateProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
