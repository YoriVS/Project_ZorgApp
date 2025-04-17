package org.example.zorgapp_maven;

import java.time.LocalDate;

public class Consultation implements ScreenElements {
    int id;
    LocalDate date;
    String user;
    String patient;
    String description;
    String type;
    double length;
    double weight;
    double lungCapacity;

    Consultation(int id, LocalDate date, String user, String patient, String type, String description, double length, double weight, double longCapacity) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.patient = patient;
        this.description = description;
        this.type = type;
        this.length = length;
        this.weight = weight;
        this.lungCapacity = longCapacity;

    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public void displayInfo() {
        System.out.format("%-17s %s\n", "User :", user);
        System.out.format("%-17s %s\n", "Patient :", patient);
        System.out.format("%-17s %s\n", "Date :", date);
        System.out.format("%-17s %s\n", "Length :", length);
        System.out.format("%-17s %s\n", "weight :", weight);
        System.out.format("%-17s %s\n", "Bmi :", Patient.calcBMI(weight, length));
        System.out.format("%-17s %s\n", "Type :", type);
        System.out.format("%-17s %s\n", "Description :", description);
    }

    @Override
    public void displayMenu() {
        System.out.format("%d: Return: ", Instances.RETURN);
    }

    @Override
    public String chooseFromMenu(int choice) {
        if (choice == Instances.RETURN) {
            return "Consultation List";
        }
    return null;
    }

    @Override
    public void editMenu() {

    }

    @Override
    public String editObject(int choice) {
        return "";
    }

    @Override
    public <T> T addObject(int idNumber) {
        return null;
    }
}
