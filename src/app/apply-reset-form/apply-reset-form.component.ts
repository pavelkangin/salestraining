import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NzFormModule } from 'ng-zorro-antd/form';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NzInputModule } from 'ng-zorro-antd/input';
import { FormControl, FormGroup, NonNullableFormBuilder, Validators } from '@angular/forms';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';


@Component({
  selector: 'app-apply-reset-form',
  standalone: true,
    imports: [
      CommonModule,
      NzFormModule,
      FormsModule,
      ReactiveFormsModule,
      NzInputModule,
      NzButtonModule,
      NzCheckboxModule

    ],
  templateUrl: './apply-reset-form.component.html',
  styleUrl: './apply-reset-form.component.css'
})
export class ApplyResetFormComponent {
  validateForm: FormGroup<{
    repeat: FormControl<string>;
    password: FormControl<string>;
  }> = this.fb.group({
    repeat: ['', [Validators.required]],
    password: ['', [Validators.required]]
  });

  submitForm(): void {
    if (this.validateForm.valid) {
      console.log('submit', this.validateForm.value);
    } else {
      Object.values(this.validateForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
  }

  constructor(private fb: NonNullableFormBuilder) {}
}
