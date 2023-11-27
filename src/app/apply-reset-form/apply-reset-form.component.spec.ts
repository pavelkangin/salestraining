import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplyResetFormComponent } from './apply-reset-form.component';

describe('ApplyResetFormComponent', () => {
  let component: ApplyResetFormComponent;
  let fixture: ComponentFixture<ApplyResetFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ApplyResetFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ApplyResetFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
