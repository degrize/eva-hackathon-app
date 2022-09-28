import { Component, OnInit } from '@angular/core';
import { IAide } from './aide.model';
import { AideFormGroup, AideFormService } from './aide-form.service';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { finalize } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { AideService } from './aide.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'jhi-aide',
  templateUrl: './aide.component.html',
  styleUrls: ['./aide.component.scss'],
})
export class AideComponent implements OnInit {
  isSaving = false;
  aide: IAide | null = null;

  editForm: AideFormGroup = this.aideFormService.createAideFormGroup();
  constructor(protected aideService: AideService, protected aideFormService: AideFormService, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {}

  save(): void {
    this.isSaving = true;
    const aide = this.aideFormService.getAide(this.editForm);
    console.log('AIDE FORM');
    console.log(aide);
    if (aide.nom === null || aide.nom === '') {
      aide.nom = 'Anonyme';
    }
    this.subscribeToSaveResponse(this.aideService.create(aide));
  }

  startNotificationMailSend(): void {
    const Toast = Swal.mixin({
      toast: true,
      position: 'top-end',
      showConfirmButton: false,
      timer: 6000,
      timerProgressBar: true,
      didOpen: toast => {
        toast.addEventListener('mouseenter', Swal.stopTimer);
        toast.addEventListener('mouseleave', Swal.resumeTimer);
      },
    });

    Toast.fire({
      icon: 'success',
      title: 'Votre requete a bien été enregistrée, nous vous repondrons bientôt',
    });
  }

  startErrorNotificationMailNoSend(): void {
    const Toast = Swal.mixin({
      toast: true,
      position: 'top-end',
      showConfirmButton: false,
      timer: 6000,
      timerProgressBar: true,
      didOpen: toast => {
        toast.addEventListener('mouseenter', Swal.stopTimer);
        toast.addEventListener('mouseleave', Swal.resumeTimer);
      },
    });

    Toast.fire({
      icon: 'error',
      title: "le mail n'a pas été envoyé",
    });
  }

  public clearForm(): void {
    let inputMessage = document.getElementsByTagName('textarea');
    for (let i = 0; i < inputMessage.length; i++) {
      inputMessage[i].value = '';
    }

    let inputNomAndEmail = document.getElementsByTagName('input');
    for (let i = 0; i < inputNomAndEmail.length; i++) {
      inputNomAndEmail[i].value = '';
    }

    this.isSaving = false;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAide>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.clearForm();
    this.startNotificationMailSend();
  }

  protected onSaveError(): void {
    this.startErrorNotificationMailNoSend();
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(aide: IAide): void {
    this.aide = aide;
    this.aideFormService.resetForm(this.editForm, aide);
  }
}
