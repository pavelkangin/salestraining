import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScriptPreviewComponent } from './script-preview.component';

describe('ScriptPreviewComponent', () => {
  let component: ScriptPreviewComponent;
  let fixture: ComponentFixture<ScriptPreviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ScriptPreviewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ScriptPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
