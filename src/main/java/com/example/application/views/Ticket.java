package com.example.application.views;

import com.vaadin.flow.data.renderer.Renderer;

/**
 * Beschreiben Sie hier die Klasse Ticket.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Ticket
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    public Double preis;
    public String _name;

    /**
     * Konstruktor für Objekte der Klasse Ticket
     */
    public Ticket(String name,Double price)
    {
        // Instanzvariable initialisieren
        _name = name;
        preis = price;
    }


    public String getFullName() {
        return _name;
    }
}
