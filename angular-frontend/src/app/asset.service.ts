import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {Asset} from './asset';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json',
    'Cache-Control': 'no-cache'
  })
};

@Injectable({
  providedIn: 'root'
})
export class AssetService {
  private assetUrl = 'http://localhost:8080/devices';  // URL to web api

  constructor(private http: HttpClient) { }
  /** GET assets from the server */
  getAssets (): Observable<Asset[]> {
    return this.http.get<Asset[]>(this.assetUrl)
      .pipe(
        tap(assets => this.log('fetched assets')),
        catchError(this.handleError('getAssets', []))
      );
  }
  
  getAsset(id: number): Observable<Asset> {
    const url = `${this.assetUrl}/${id}`;
    return this.http.get<Asset>(url).pipe(
      tap(_ => this.log(`fetched asset id=${id}`)),
      catchError(this.handleError<Asset>(`getAsset id=${id}`))
    );
  }

  addAsset(asset: Asset[]): Observable<Asset> {
    return this.http.post<Asset>(this.assetUrl, asset, httpOptions).pipe(
      tap(_ => this.log(`created new asset`)),
      catchError(this.handleError<Asset>(`addAsset`))
    );
  }

  /** Handle failed Http request and let app continue
   * @param operation - name of op that failed
   * @param result - optional result to return as observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
  /** Log a message */
  private log(message: string) {
    console.info(message);
  }
}
