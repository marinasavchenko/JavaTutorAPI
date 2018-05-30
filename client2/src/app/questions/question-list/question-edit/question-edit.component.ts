import {QuestionService} from '../../question.service';
import {Question} from '../../question.model';
import {Component, OnInit} from '@angular/core';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import {ActivatedRoute, Params, Router} from '@angular/router';

@Component({
  selector: 'app-question-edit',
  templateUrl: './question-edit.component.html',
  styleUrls: ['./question-edit.component.css']
})
export class QuestionEditComponent implements OnInit {
  id: number;
  editMode = false;
  questionForm: FormGroup;

  constructor(private route: ActivatedRoute,
     private questionService: QuestionService,
     private router: Router
   ) {}

  ngOnInit() {
    this.route.params
      .subscribe(
      (params: Params) => {
        this.id = +params['id'];
        this.editMode = params['id'] != null;
        this.initForm(); 
      }
      );
  }
  
  onSubmit() {
    if (this.editMode) {
      this.questionService.updateQuestion(this.id, this.questionForm.value);
    } else {
      this.questionService.addQuestion(this.questionForm.value);
    }
    this.onCancel();
  }
  
   onCancel() {
  this.router.navigate(['../'], {relativeTo: this.route});
  }

  private initForm() {
    let javaQuestion = '';
    let javaAnswer = '';

    if (this.editMode) {
      const question = this.questionService.getQuestion(this.id);
      javaQuestion = question.question;
      javaAnswer = question.answer;
    }

    this.questionForm = new FormGroup({
      'question': new FormControl(javaQuestion, Validators.required),
      'answer': new FormControl(javaAnswer, Validators.required)
    });
  }
  
 

}
