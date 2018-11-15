import {Component, Input, OnInit} from '@angular/core';
import {Log} from '../log';
import {Asset} from '../asset';
import {ActivatedRoute} from '@angular/router';
import {AssetService} from '../asset.service';
import {LogService} from '../log.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-asset-detail',
  templateUrl: './asset-detail.component.html',
  styleUrls: ['./asset-detail.component.css']
})
export class AssetDetailComponent implements OnInit {
  @Input() asset: Asset;
  logs: Log[];

  constructor(
    private route: ActivatedRoute,
    private assetService: AssetService,
    private location: Location,
    private logService: LogService
  ) { }

  ngOnInit(): void {
    this.getAsset();
    this.getAssetLogs();
	this.getChance();
  }

  getAsset(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.assetService.getAsset(id)
      .subscribe(asset => this.asset = asset);
  }

  getAssetLogs(): void {
    this.logService.getAssetLogs(this.route.snapshot.paramMap.get('id'))
      .subscribe(logs => this.logs = logs);
  }
  
  getChance() : void {
	  this.logService.isMissing("17");
  }

  goBack(): void {
    this.location.back();
  }
}
