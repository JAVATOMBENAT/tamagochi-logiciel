package com.example.tamagochi;

public class Consomable {
    private String nom;
    private double prix;

    public Consomable(String nom, double prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix ;
    }

    public String toString() {
        return this.nom + "prix :  " + this.prix + "€";
    }
}
