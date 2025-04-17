package org.example.zorgapp_maven;

public class Tablet extends Medication {
    double mg;
    String quantity;

    Tablet(int id, String name, boolean anesthetize, double mg, String quantity) {
        super(id, name, anesthetize, mg, quantity);
        this.mg = mg;
        this.quantity = quantity;

    }

    private String getDosage() {
        return mg + " mg " + quantity;
    }

    @Override
    public void editMenu() {
        System.out.format("%d:  RETURN\n", Instances.RETURN);
        System.out.format("%d:  Name (ex: Ibuprofen)\n", Instances.NAME);
        System.out.format("%d:  Quantity (ex: 2x per day)\n", Instances.DESCRIPTION);
        System.out.format("%d:  Dosage (ex: 250 mg)\n", Instances.TYPE);
        System.out.print("enter #choice: ");
    }

    @Override
    public void displayInfo() {
        System.out.format("%-17s %s \n", "Name:", name);
        System.out.format("%-17s %s \n", "Type:", getClass().getSimpleName());
        System.out.format("%-17s %s \n", "Dosage:", getDosage());
    }
}
