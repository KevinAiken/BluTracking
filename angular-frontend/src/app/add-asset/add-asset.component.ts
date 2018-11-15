import { Component, OnInit } from '@angular/core';
import {Asset} from '../asset';
import {AssetService} from '../asset.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-add-asset',
  templateUrl: './add-asset.component.html',
  styleUrls: ['./add-asset.component.css']
})
export class AddAssetComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private assetService: AssetService
  ) { }

  ngOnInit() {
  }

  add(name: string, addr: string, type: string): void {
    name = name.trim();
    addr = addr.trim();
    const asset: Asset = {
      name: name,
      btAddr: addr,
      id: null,
      dateEnrolled: null,
	  missing: null
    };
    const dev: Asset[] = [asset];
    this.assetService.addAsset(dev)
      .subscribe(() => location.reload());
  }
}
