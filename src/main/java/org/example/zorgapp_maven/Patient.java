package org.example.zorgapp_maven;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Scanner;

class Patient implements ScreenElements {
    int id;
    String surname;
    String firstName;
    LocalDate dateOfBirth;
    double length;
    double weight;
    double bmi;
    int age;
    ArrayList<Medication> medication;

    public static double calcBMI(double weight, double length) {
        double bmi = weight / Math.pow(length, 2);
        return new BigDecimal(bmi).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

    public static int calcAge(LocalDate dateOfBirth, LocalDate date) {
        return Period.between(dateOfBirth, date).getYears();
    }

    Patient(int id, String surname, String firstName, LocalDate dateOfBirth, double length, double weight, ArrayList<Medication> medicatie) {
        this.id = id;
        this.surname = surname;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.length = length;
        this.weight = weight;
        this.bmi = calcBMI(weight, length);
        this.age = calcAge(dateOfBirth, LocalDate.now());
        this.medication = medicatie;

    }

    String fullName() {
        return String.format("%s %s", firstName, surname);

    }

    @Override
    public String toString() {
        return fullName();
    }

    @Override
    public void displayInfo() {
        System.out.format("%-17s %s \n", "Surname:", surname);
        System.out.format("%-17s %s\n", "firstName:", firstName);
        System.out.format("%-17s %s\n", "Date of birth:", dateOfBirth);
        System.out.format("%-17s %s\n", "Age:", age);
        System.out.format("%-17s %s\n", "Length:", length + " m");
        System.out.format("%-17s %s\n", "Weight:", weight + " kg");
        System.out.format("%-17s %s\n", "BMI:", bmi + " kg/m2");
    }

    @Override
    public void displayMenu() {
        System.out.format("%d: Return: \n", Instances.RETURN);
        System.out.format("%d: Edit: \n", Instances.EDIT);
        System.out.format("%d: View medication: \n", Instances.ADD);
        System.out.format("%d: View BMI Graph:\n", Instances.BMI_GRAPH);
        System.out.format("%d: View Lung Capacity Graph:\n", Instances.LUNGCAPACITY_GRAPH);
        System.out.format("%d: Consultations:\n", Instances.CONSULTATION);
        System.out.print("enter #choice: ");
    }

    @Override
    public String chooseFromMenu(int choice) {
        switch (choice) {
            case Instances.RETURN:
                return "Patient List";

            case Instances.EDIT:
                return "Edit Patient Info";

            case Instances.ADD:
                return "Medication List Menu";

            case Instances.BMI_GRAPH:
                return "BMI Graph";

            case Instances.LUNGCAPACITY_GRAPH:
                return "Lung Capacity Graph";

            case Instances.CONSULTATION:
                return "Consultation List Menu";

            default:
                System.out.println("Please enter a *valid* digit: ");
                break;

        }
        return null;
    }

    @Override
    public void editMenu() {
        System.out.format("%d:  Surname\n", Instances.SURNAME);
        System.out.format("%d:  Firstname\n", Instances.FIRSTNAME);
        System.out.format("%d:  Date of Birth\n", Instances.DATEOFBIRTH);
        System.out.format("%d:  Length\n", Instances.LENGHT);
        System.out.format("%d:  Weight\n", Instances.WEIGHT);
        System.out.format("%d:  RETURN\n", Instances.RETURN);
        System.out.print("enter #choice: ");
    }

    @Override
    public String editObject(int choice) {
        Scanner scanner = new Scanner(System.in);

        switch (choice) {
            case Instances.SURNAME:
                System.out.print("Enter Surname: ");
                surname = scanner.nextLine();
                break;

            case Instances.FIRSTNAME:
                System.out.print("Enter Firstname: ");
                firstName = scanner.nextLine();
                break;

            case Instances.DATEOFBIRTH:
                System.out.print("Date of Birth (YYYY-MM-DD): ");
                dateOfBirth = LocalDate.parse(scanner.next());
                break;

            case Instances.LENGHT:
                System.out.print("Length: ");
                length = scanner.nextDouble();
                break;

            case Instances.WEIGHT:
                System.out.print("Weight: ");
                weight = scanner.nextDouble();
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
        String surname;
        String firstName;
        LocalDate dateOfBirth;
        double length;
        double weight;

        System.out.println("To create a new patient you need to fill: ");
        System.out.print("Enter Surname: ");
        System.out.format("%s %s \n","Surname: ", surname = scanner.nextLine());
        System.out.print("Enter First Name: ");
        System.out.format("%s %s \n","First Name: ", firstName = scanner.next());
        System.out.print("Enter Date of Birth (YY-MM-DD): ");
        System.out.format("%s %s \n","Date of Birth: ", dateOfBirth = LocalDate.parse(scanner.next()));
        System.out.print("Enter Length: ");
        System.out.format("%s %s \n","Length: ", length = scanner.nextDouble());
        System.out.print("Enter Weight: ");
        System.out.format("%s %s \n","Weight: ", weight = scanner.nextDouble());

        Patient newPatient = new Patient(id, surname, firstName, dateOfBirth, length, weight, null);

        return (T) newPatient;
    }
}