package com.example.application.views;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 

import java.util.Scanner;   

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.awt.Desktop;
/**
 * Man hat einen Automaten wo man ein Ticket kaufen kann mit dem gegebenden
 * Preis. Mit der Funktion zahlen zahlt man Geld in Cent ein. Mit "print" kann
 * man eine bestimmte Anzahl von Tickets kaufen.
 * 
 * @author (Vincent Elster) 
 * @version (01)
 */
public class Ticketautomat
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private boolean paid;
    private Double preis;
    private int balance;
    private int x;
    private String destination;
    private static int umsatz;
    
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
    LocalDateTime now = LocalDateTime.now(); 
    
    Ticket hallerstrasse = new Ticket("hallerstrasse", 1000.0);
    Ticket eppendorfer_baum = new Ticket("eppendorfer_baum", 2000.0);
    
    Scanner kb=new Scanner(System.in);

    /**
     * Konstruktor für Objekte der Klasse Ticketautomat
     */
    public Ticketautomat()
    {   
        System.out.println("(1) Hallerstrasse");
    System.out.println("(2) Eppendorfer Baum");
    x = kb.nextInt();
    if(x == 1)
    {
            destination = "Hallerstraße!";
        preis = hallerstrasse.preis;   
        }
        if(x == 2)
        {
            destination = "Eppendorfer Baum!";
            preis = eppendorfer_baum.preis;
        }
        if(x == 1234){
            System.out.println("Gesamt Umsatz: ");
            System.out.println(umsatz);
        }
         
        // Instanzvariable initialisieren
        paid = false;
        balance = 0;
        System.out.println("#################");
        System.out.println(dtf.format(now));  
        System.out.println(destination);
        System.out.println("Ticket Preis: "+ preis);
        System.out.println("Balance: "+ balance);
        System.out.println("#################");
        System.out.println("0: Abbrechen");
        System.out.println("1: Einzahlen");
        x = kb.nextInt();
        if(x == 0){
            cashBack();
            System.out.println("Tschüss!");
            Ticketautomat nt = new Ticketautomat();
        }
        if(x == 1)
        {
            System.out.println("Wie viel möchten Sie einzahlen?");
            x = kb.nextInt();
            einzahlen(x);
        }
    }

    public Ticketautomat(Double price){
       // Instanzvariable initialisieren
       preis = price;
       paid = false;
       balance = 0;
       System.out.println("#################");
       System.out.println("Ticket Preis: "+ preis);
       System.out.println("Balance: "+ balance);
       System.out.println("#################");
    }

    /**
     * Einzahlen
     * @amount ist wieviel Geld man einzahlt.
     * @updatet die balance
     */
    public void einzahlen(int amount)
    {
        if(amount < 0)
        {
            System.out.println("Bitte nur positive Beträge verwenden");
        }
        else
        {
            balance += amount;
            System.out.println("#################");
            System.out.println("Balance: " + balance);
            System.out.println("#################");
            
            System.out.println("0: Abbrechen");
            System.out.println("1: Ticket Kaufen");
            System.out.println("2: Mehr einzahlen");
            x = kb.nextInt();
            
            if(x == 0){
                cashBack();
                System.out.println("Tschüss!");
                Ticketautomat nt = new Ticketautomat();
            }
            if(x == 1){
                System.out.println("Wie viele Tickets wollen Sie kaufen?");
                x = kb.nextInt();
                print(x);
            }
            if(x == 2)
            {
                System.out.println("Wie viel möchten Sie einzahlen?");
                x = kb.nextInt();
                einzahlen(x);
           }
        }
    }
    
    /**
     * Ticket kaufen & drucken
     * @amount wieviele Tickets man kauft.
     * @kauft die möglichen tickets und gibt die balance zurück
     */
    public void print(int amount){
        if(balance >= preis*amount){
            try {
              for(int i = 0; i<amount-1;i++){
              System.out.println("Auf welchen Namen?");
              Scanner name = new Scanner(System.in);
              String ticketName = name.nextLine();
              FileWriter myWriter = new FileWriter("ticket.txt");
              myWriter.write("#########################");
              myWriter.write("\n");
              myWriter.write(dtf.format(now));
              myWriter.write("\n");
              myWriter.write(ticketName);
              myWriter.write("\n");
              myWriter.write("1 Ticket");
              myWriter.write("\n");
              myWriter.write(destination);
              myWriter.write("\n");
              myWriter.write("#########################");
              myWriter.close();
              File file = new File("/Users/vince/development/school/blueJ/Ticketautomat/ticket.txt");
              Desktop.getDesktop().print(file);
            }
            } catch (IOException e) {
              System.out.println("An error occurred.");
              e.printStackTrace();
            }
            balance -= preis*amount;
            System.out.println("#################");
            System.out.println(amount + " Ticket/s gekauft!");
            umsatz += preis*amount;
            System.out.println("Balance: " + balance);
            System.out.println("#################");
            
            System.out.println("0: Verlassen");
            System.out.println("1: Weitere Tickets kaufen");
            x = kb.nextInt();
            if(x == 0){
                cashBack();
                System.out.println("Heidela!");
                Ticketautomat nt = new Ticketautomat();
            }
            if(x == 1){
                Ticketautomat nt = new Ticketautomat();
            }
            if(x == 1234)
        {
            System.out.println("Gesamt Einnahmen!:");
            System.out.println( umsatz);
        }
        }else{
            System.out.println("#################");
            System.out.println("Zu broke! Zahle noch "+ (preis - balance) + " ein!");
            System.out.println("#################");
            System.out.println("0: Abbrechen");
        System.out.println("1: Einzahlen");
        x = kb.nextInt();
        if(x == 0){
            cashBack();
            System.out.println("Tschüss!");
        }
        if(x == 1)
        {
            System.out.println("Wie viel möchten Sie einzahlen?");
            x = kb.nextInt();
            einzahlen(x);
        }
        }

    }

    public void cashBack(){
        System.out.println("#################");
        System.out.println("Cashback: " + balance);
        balance = 0;
        System.out.println("Balance: " + balance);
        System.out.println("Here's your Money money money!");
        System.out.println("#################");
    }
}
