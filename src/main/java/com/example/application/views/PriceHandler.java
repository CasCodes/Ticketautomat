package com.example.application.views;

// price handler class to deal with price comparison
public class PriceHandler {

    // this method returns true if balance is high enough, else false
    public Boolean compare(Double price, Double balance){
        // handle null
        if (price == null || balance == null){
            return false;
        }

        if(balance >= price) {
            System.out.println("price: " + price + "\nbalance: " + balance);
            float cashReturn = cashback(price, balance);
            System.out.println("return: " + cashReturn);
            return true;
        }
        else{
            return false;
        }
    }

    // local function to compute the cashback and 
    public Float cashback(Double price, Double balance){
        float cashback = (float) (balance - price);
        return cashback;
    }
    // TODO add popup window for cashback 
}