import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

// Angular Material import
import { MaterialModule } from './material/material.module'

import { HomeComponent } from './components/core/home/home.component';
import { AuthorComponent } from './components/core/author/author.component';
import { AboutComponent } from './components/core/about/about.component';

import { ProizvodComponent } from './components/proizvod/proizvod.component';
import { ProizvodjacComponent } from './components/proizvodjac/proizvodjac.component';
import { RacunComponent } from './components/racun/racun.component';
import { StavkaRacunaComponent } from './components/stavka-racuna/stavka-racuna.component';

// Dialogs Import 
import { ProizvodjacDialogComponent } from './components/dialogs/proizvodjac-dialog/proizvodjac-dialog.component';
import { RacunDialogComponent } from './components/dialogs/racun-dialog/racun-dialog.component';
import { ProizvodDialogComponent } from './components/dialogs/proizvod-dialog/proizvod-dialog.component';
import { StavkaRacunaDialogComponent } from './components/dialogs/stavka-racuna-dialog/stavka-racuna-dialog.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AuthorComponent,
    AboutComponent,
    ProizvodComponent,
    ProizvodjacComponent,
    RacunComponent,
    StavkaRacunaComponent,
    ProizvodjacDialogComponent,
    RacunDialogComponent,
    ProizvodDialogComponent,
    StavkaRacunaDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
