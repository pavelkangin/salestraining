import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScriptDialogueComponent } from './script-dialogue.component';

describe('ScriptDialogueComponent', () => {
  let component: ScriptDialogueComponent;
  let fixture: ComponentFixture<ScriptDialogueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ScriptDialogueComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ScriptDialogueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
