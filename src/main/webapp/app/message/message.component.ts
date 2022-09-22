import { Component, OnInit } from '@angular/core';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss'],
})
export class MessageComponent implements OnInit {
  files: File[] = [];

  closeResult: string | undefined;

  constructor(private modalService: NgbModal) {}

  open(content: any, type: string, modalDimension: string) {
    if (modalDimension === 'sm' && type === 'modal_mini') {
      this.modalService.open(content, { windowClass: 'modal-mini', size: 'sm', centered: true }).result.then(
        result => {
          this.closeResult = 'Closed with: $result';
        },
        reason => {
          this.closeResult = 'Dismissed $this.getDismissReason(reason)';
        }
      );
    } else if (modalDimension === '' && type === 'Notification') {
      this.modalService.open(content, { windowClass: 'modal-danger', centered: true }).result.then(
        result => {
          this.closeResult = 'Closed with: $result';
        },
        reason => {
          this.closeResult = 'Dismissed $this.getDismissReason(reason)';
        }
      );
    } else {
      this.modalService.open(content, { centered: true }).result.then(
        result => {
          this.closeResult = 'Closed with: $result';
        },
        reason => {
          this.closeResult = 'Dismissed $this.getDismissReason(reason)';
        }
      );
    }
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return 'with: $reason';
    }
  }

  ngOnInit() {}

  onSelect(event: any) {
    console.log(event);
    this.files.push(...event.addedFiles);
  }

  onRemove(event: File) {
    console.log(event);
    this.files.splice(this.files.indexOf(event), 1);
  }
}
