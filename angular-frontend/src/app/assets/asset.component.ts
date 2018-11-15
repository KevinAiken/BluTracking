import { Component, OnInit } from '@angular/core';
import {AssetService} from '../asset.service';
import {Asset} from '../asset';

@Component({
  selector: 'app-asset',
  templateUrl: './asset.component.html',
  styleUrls: ['./asset.component.css']
})
export class AssetsComponent implements OnInit {
  assets: Asset[];
  constructor(
    private assetService: AssetService) { }

  ngOnInit() {
    this.getAssets();
  }

  getAssets(): void {
    console.info('getting assets');
    this.assetService.getAssets()
      .subscribe(assets => this.assets = assets);
  }

  assetMissing(date: string): boolean {
    const date1 = new Date(date);
    const date2 = new Date();
    date2.setMinutes(date2.getMinutes() - 20);
    if (date1 < date2) {
      return true;
    } else {
      return false;
    }
  }
}
