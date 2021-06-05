import { Input, OnChanges, OnDestroy, ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Proizvod } from 'src/app/models/proizvod';
import { Racun } from 'src/app/models/racun';
import { StavkaRacuna } from 'src/app/models/stavkaRacuna';
import { StavkaRacunaService } from 'src/app/services/stavka-racuna.service';
import { StavkaRacunaDialogComponent } from '../dialogs/stavka-racuna-dialog/stavka-racuna-dialog.component';

@Component({
  selector: 'app-stavka-racuna',
  templateUrl: './stavka-racuna.component.html',
  styleUrls: ['./stavka-racuna.component.css']
})
export class StavkaRacunaComponent implements OnInit, OnChanges, OnDestroy {

  displayedColumns = ['id', 'redniBroj', 'kolicina', 'jedinicaMere', 'cena', 'racun', 'proizvod', 'actions']
  dataSource: MatTableDataSource<StavkaRacuna>;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  stavkaRacunaSubscription: Subscription;

  @Input() selektovaniRacun: Racun;

  constructor(private stavkaRacunaService: StavkaRacunaService,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    //console.log('selected racun: ' + this.selektovaniRacun)
    //this.loadData();
  }

  ngOnChanges(): void {
    if(this.selektovaniRacun.id) {
      this.loadData();
    }
  }
  
  ngOnDestroy(): void {
    this.stavkaRacunaSubscription.unsubscribe();
  }

  public loadData() {
    this.stavkaRacunaSubscription = this.stavkaRacunaService.getStavkeZaRacun(this.selektovaniRacun.id)
    .subscribe(data => {
      //console.log('dobijene stavke')
      //console.log(data)
      // pretraga po nazivu ugnježdenog objekta
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.filterPredicate = (data, filter: string) => {
        const accumulator = (currentTerm, key) => {
          return key === 'proizvod' ? currentTerm + data.proizvod.naziv : currentTerm + data[key];
        };
        const dataStr = Object.keys(data).reduce(accumulator, '').toLowerCase();
        const transformedFilter = filter.trim().toLowerCase();
        return dataStr.indexOf(transformedFilter) !== -1;
      };

      // sortiranje po nazivu ugnježdenog objekta
      this.dataSource.sortingDataAccessor = (data, property) => {
        switch (property) {
          case 'proizvod': return data.proizvod.naziv.toLocaleLowerCase();
          default: return data[property];
        }
      };
      this.dataSource.sort = this.sort;
    }),
    (error: Error) => {
      console.log(error.name + ' ' + error.message)
    }
  }

  openDialog(flag: number, id?: number, redniBroj?: number, kolicina?: number, jedinicaMere?: string, cena?: number,
    racun?: Racun, proizvod?: Proizvod) {
      const dialogRef = this.dialog.open(StavkaRacunaDialogComponent, 
        { data: {id, redniBroj, kolicina, jedinicaMere, cena, racun, proizvod}
      });
      dialogRef.componentInstance.flag = flag;
      if(flag === 1) {
        dialogRef.componentInstance.data.racun = this.selektovaniRacun;
      }

      dialogRef.afterClosed().subscribe(result => {
        if(result === 1) {
          this.loadData();
        }
      }
      )
  }
  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }
}
