import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Proizvod } from 'src/app/models/proizvod';
import { StavkaRacuna } from 'src/app/models/stavkaRacuna';
import { ProizvodService } from 'src/app/services/proizvod.service';
import { StavkaRacunaService } from 'src/app/services/stavka-racuna.service';

@Component({
  selector: 'app-stavka-racuna-dialog',
  templateUrl: './stavka-racuna-dialog.component.html',
  styleUrls: ['./stavka-racuna-dialog.component.css']
})
export class StavkaRacunaDialogComponent implements OnInit {

  proizvodi: Proizvod[];
  public flag: number;

  constructor(public snackBar: MatSnackBar,
              public dialogRef: MatDialogRef<StavkaRacunaDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: StavkaRacuna,
              public stavkaRacunaService: StavkaRacunaService,
              public proizvodService: ProizvodService) { }

  ngOnInit(): void {
    this.proizvodService.getAllProizvodi()
    .subscribe(proizvodi => {
      this.proizvodi = proizvodi;
    }),
    (error: Error) => {
      console.log(error.name + ' ' + error.message)
    }
  }

  // postojeci red odgovara selektovanom redu
  compareTo(a,b) {
    return a.id === b.id;
  }

  public add(): void {
    this.stavkaRacunaService.addStavkaRacuna(this.data).subscribe(() => {
      this.snackBar.open('Stavka računa uspešno dodata.' , 'OK', {
        duration: 2500
      });
    }),
    (error: Error) => {
      console.log(error.name);
      this.snackBar.open('Došlo je do greške prilikom dodavanja stavke računa. ' , 'Zatvori', {
        duration: 2500
      });
    }
    
  }
  public update(): void {
    this.stavkaRacunaService.updateStavkaRacuna(this.data).subscribe(() => {
      this.snackBar.open('Stavka računa uspešno izmenjena: ' + this.data.id, 'OK', {
        duration: 2500
      });
    }),
    (error: Error) => {
      console.log(error.name);
      this.snackBar.open('Došlo je do greške prilikom izmene stavke računa. ' , 'Zatvori', {
        duration: 2500
      });
    }
    
  }
  public delete(): void {
    this.stavkaRacunaService.deleteStavkaRacuna(this.data.id).subscribe(() => {
      this.snackBar.open('Stavka računa uspešno obrisana: ' + this.data.id, 'OK', {
        duration: 2500
      });
    }),
    (error: Error) => {
      console.log(error.name);
      this.snackBar.open('Došlo je do greške prilikom brisanja stavke računa. ' , 'Zatvori', {
        duration: 2500
      });
    }
    
  }
  public cancel(): void {
    this.dialogRef.close();
    this.snackBar.open('Odustali ste. ' , 'Zatvori', {
      duration: 1500
    });
  }
}
