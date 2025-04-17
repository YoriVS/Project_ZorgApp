package org.example.zorgapp_maven;

import java.util.Scanner;

public class Medication implements ScreenElements {
    int id;
    String name;
    String description;
    String type;
    boolean anesthetize;
    double dosage;
    String quantity;

    Medication(int id, String name, boolean anesthetize, double dosage, String quantity) {
        this.anesthetize = anesthetize;
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void displayInfo() {
        System.out.format("%-17s %s \n", "Name:", name);

    }

    @Override
    public void displayMenu() {
        System.out.format("%d Edit: \n", Instances.EDIT);
        System.out.format("%d Return: \n", Instances.RETURN);
        System.out.print("enter #choice: ");

    }

    @Override
    public String chooseFromMenu(int choice) {
        switch (choice) {
            case Instances.EDIT:
                return "Edit Medication";

            case Instances.RETURN:
                return "Medication List";

            default:
                System.out.println("Please enter a *valid* digit: ");
                break;

        }
    return null;
    }

    @Override
    public void editMenu() {
        System.out.format("%d:  RETURN\n", Instances.RETURN);
        System.out.format("%d:  Name (ex: Morphine)\n", Instances.NAME);
        System.out.format("%d:  Quantity (ex: as much as needed)\n", Instances.DESCRIPTION);
        System.out.format("%d:  Dosage (ex: 250 ml)\n", Instances.TYPE);
        System.out.print("enter #choice: ");
    }

    @Override
    public String editObject(int choice) {
        Scanner scanner = new Scanner(System.in);

        switch (choice) {
            case Instances.NAME:
                System.out.print("Enter Name: ");
                name = scanner.nextLine();
                break;

            case Instances.DESCRIPTION:
                System.out.print("Enter Quantity: ");
                description = scanner.nextLine();
                break;

            case Instances.TYPE:
                System.out.print("Is it a anesthetize medication? (y/n): ");
                String answer = scanner.nextLine();
                anesthetize = answer.equalsIgnoreCase("y");
                break;

            case Instances.RETURN:
                return "Patient Info";

            default:
                System.out.println("Please enter a *valid* digit: ");
                break;
        }
        return null;
    }

    @Override
    public <T> T addObject(int idNumber) {
        Scanner scanner = new Scanner(System.in);
        String name;
        String description;
        double quantity;
        boolean anesthetize;

        System.out.println("To add a new medication you need to fill: ");
        System.out.print("Enter Name: ");
        System.out.format("%s %s \n", "name: ", name = scanner.nextLine());
        System.out.print("Enter The Quantity: ");
        System.out.format("%s %s \n", "Quantity: ", quantity = scanner.nextDouble());
        System.out.print("Enter The description: ");
        System.out.format("%s %s \n", "Description: ", description = scanner.nextLine());
        System.out.print("Is it a anesthetize medication? (y/n): ");
        String answer = scanner.nextLine();
        anesthetize = answer.equalsIgnoreCase("y");
        System.out.format("%s %s \n", "Anesthetize: ", anesthetize);


        Medication newMedication = new Medication(idNumber, name, anesthetize, quantity, description);

        return (T) newMedication;
    }
}