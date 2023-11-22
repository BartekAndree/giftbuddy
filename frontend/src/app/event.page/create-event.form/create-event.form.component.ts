import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UUIDValidator } from '../../utils/UUIDValidator';
import { EventService } from '../../services/event/event.service';
import { EventRequestDTO } from '../../interfaces/event';

@Component({
  selector: 'app-create-event-form',
  templateUrl: './create-event.form.component.html',
  styleUrls: ['./create-event.form.component.scss'],
})
export class CreateEventFormComponent {
  eventForm: FormGroup;

  constructor(private eventService: EventService) {
    this.eventForm = this.createFormGroup();
  }

  private createFormGroup(): FormGroup {
    return new FormGroup({
      organizerId: new FormControl('', [Validators.required, UUIDValidator.validUUID]),
      title: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(100)]),
      endDate: new FormControl('', [Validators.required, this.futureDateValidator]),
    });
  }

  submitForm(): void {
    if (this.eventForm.valid) {
      const eventData: EventRequestDTO = this.eventForm.value as EventRequestDTO;
      this.eventService.createEvent(eventData).subscribe({
        next: (response) => {/* Obsługa odpowiedzi */},
        error: (error) => {/* Obsługa błędów */}
      });
    }
  }

  private futureDateValidator(control: FormControl): { [key: string]: any } | null {
    const date = new Date(control.value);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    return date > today ? null : { 'notInFuture': true };
  }
}
