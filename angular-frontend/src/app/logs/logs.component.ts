import { Component, OnInit } from '@angular/core';
import {LogService} from '../log.service';
import {Log} from '../log';

@Component({
  selector: 'app-logs',
  templateUrl: './logs.component.html',
  styleUrls: ['./logs.component.css']
})
export class LogsComponent implements OnInit {
  logs: Log[];
  constructor(private logService: LogService) { }

  ngOnInit() {
    this.getLogs();
	  this.isMissing();
  }

  getLogs(): void {
    // console.info('getting logs');
    this.logService.getLogs()
      .subscribe(logs => this.logs = logs);
  }

  getAssetLogs(): void {
    this.logService.getLogs()
      .subscribe(logs => this.logs = logs);
  }
  
  isMissing(): void {
	   this.logService.isMissing("17").subscribe();
  }

}
