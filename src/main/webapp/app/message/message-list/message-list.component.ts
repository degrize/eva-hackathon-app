import { Component, Input, OnInit } from '@angular/core';
import { animate, animateChild, group, query, stagger, state, style, transition, trigger, useAnimation } from '@angular/animations';
import { slideAndFadeAnimation } from '../../shared/animations/slide-and-fade.animation';
import { flashAnimation } from '../../shared/animations/flash.animation';
import { IMessage } from '../../entities/message/message.model';
import { IAnnonce } from '../../entities/annonce/annonce.model';
import { DataUtils } from '../../core/util/data-util.service';

@Component({
  selector: 'jhi-message-list',
  templateUrl: './message-list.component.html',
  styleUrls: ['./message-list.component.scss'],
  animations: [
    trigger('list', [transition(':enter', [query('@listItem', [stagger(50, [animateChild()])])])]),
    trigger('listItem', [
      state(
        'default',
        style({
          transform: 'scale(1)',
          'background-color': 'white',
          'z-index': 1,
        })
      ),
      state(
        'active',
        style({
          transform: 'scale(1.05)',
          'background-color': '#99BE0EFF',
          'z-index': 2,
        })
      ),
      transition('default => active', [animate('100ms ease-in-out')]),
      transition('active => default', [animate('500ms ease-in-out')]),
      //animation pour l'arrivé des messages
      transition('void => *', [
        query('.comment-text, .comment-date', [
          style({
            opacity: 0,
          }),
        ]),
        useAnimation(slideAndFadeAnimation, {
          params: {
            time: '504ms',
            startColor: '#99BE0EFF',
          },
        }),
        group([
          useAnimation(flashAnimation, {
            params: {
              time: '250ms',
              flashColor: 'rgb(182,249,111)',
            },
          }),
          query('.comment-text', [
            animate(
              '250ms',
              style({
                opacity: 1,
              })
            ),
          ]),
          query('.comment-date', [
            animate(
              '500ms',
              style({
                opacity: 1,
              })
            ),
          ]),
        ]),
      ]),
    ]),
  ],
})
export class MessageListComponent implements OnInit {
  @Input() messages?: IMessage[];
  @Input() annonce?: IAnnonce;

  animationStates: { [key: number]: 'default' | 'active' } = {};
  listItemAnimationState: 'default' | 'active' = 'default'; // pour les animatoins

  constructor(protected dataUtils: DataUtils) {}

  ngOnInit(): void {
    if (this.messages) {
      for (let index in this.messages) {
        this.animationStates[index] = 'default';
        console.log(this.messages);
      }
    }
  }

  onListItemMouseEnter(index: number) {
    this.animationStates[index] = 'active';
  }

  onListItemMouseLeave(index: number) {
    this.animationStates[index] = 'default';
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }
}
