package org.example.zorgapp_maven;

public class Drinkable extends Medication {
    double ml;
    String quantity;

    Drinkable(int id, String name, boolean anesthetize, double ml, String quantity) {
        super(id, name, anesthetize, ml, quantity);
        this.ml = ml;
        this.quantity = quantity;

    }

    private String getDosage() {
        return ml + " ml " + quantity;
    }

    @Override
    public void displayInfo() {
        System.out.format("%-17s %s \n", "Name:", name);
        System.out.format("%-17s %s \n", "Type:", getClass().getSimpleName());
        System.out.format("%-17s %s \n", "Dosage:", getDosage());
    }
}
