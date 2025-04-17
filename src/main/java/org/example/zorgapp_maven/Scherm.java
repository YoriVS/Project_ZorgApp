package org.example.zorgapp_maven;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

class Scherm {
    private static final Scanner scanner = new Scanner(System.in);
    String currentScherm;

    User menu(List<Role> roles) {
        currentScherm = "Menu";
        int choice ;

        while (true) {
            System.out.format("%d: STOP\n", Instances.STOP);
            System.out.format("%d: Fysio\n", Instances.FYSIO);
            System.out.format("%d: Huisharts\n", Instances.HUISHART);
            System.out.format("%d: Tandarts\n", Instances.TANDARTS);
            System.out.format("%d: Apothekers\n", Instances.APOTHEKERS);
            System.out.print("enter #choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("Please enter a *valid* input!");
                scanner.nextLine();
                break;
            }

            switch (choice) {
                case Instances.STOP:
                    System.out.println("Bye!");
                    System.exit(0);

                case Instances.FYSIO:
                    System.out.print("Enter your Name: ");
                    currentScherm = "Patient List Menu";
                    return new User(1, scanner.next(), roles.getFirst());

                case Instances.HUISHART:
                    System.out.print("Enter your Name: ");
                    currentScherm = "Patient List Menu";
                    return new User(1, scanner.next(), roles.get(1));

                case Instances.TANDARTS:
                    System.out.print("Enter your Name: ");
                    currentScherm = "Patient List Menu";
                    return new User(1, scanner.next(), roles.get(2));

                case Instances.APOTHEKERS:
                    System.out.print("Enter your Name: ");
                    currentScherm = "Patient List Menu";
                    return new User(1, scanner.next(), roles.get(3));

                default:
                    System.out.println("Enter a *valid* number!");

            }
        }
        return null;
    }

    Consultation addConsultation(User currentUser, Patient currentPatient) {
        currentScherm = "Create Consultation";
        int id = currentPatient.id;
        String user = currentUser.getUserName();
        String patient = currentPatient.fullName();
        LocalDate date = LocalDate.now();
        double length = currentPatient.length;
        double weight = currentPatient.weight;
        String type;
        String description;
        double lungCapacity = 6;
        scanner.nextLine();

        System.out.println("To create a new consultation you need to fill: ");
        System.out.print("Enter the  type of consultation: ");
        System.out.format("%s %s \n","Type: ", type = scanner.nextLine());
        System.out.print("Enter the  description: ");
        System.out.format("%s %s \n","Description: ", description = scanner.nextLine());


        currentScherm = "Patient List";

        return new Consultation(id, date, user, patient, type, description, length,  weight, lungCapacity);
    }

    void printGraph(ArrayList<Double> values, double highestValue, double lowestValue, ArrayList<LocalDate> dateOfConsultation) {
        for (double i = highestValue + 0.2; i >= lowestValue - 0.2; i -= 0.1) {
            System.out.printf("%2.1f | ", i);

            for (Double bmiValue : values) {
                if (bmiValue == new BigDecimal(i).setScale(1, RoundingMode.HALF_UP).doubleValue()) {
                    System.out.print("   *        ");
                } else {
                    System.out.print("            ");
                }
            }
            System.out.println();
        }

        System.out.println("Days" + "-".repeat(dateOfConsultation.size() * 12));
        System.out.print("     ");

        for (LocalDate localDate : dateOfConsultation) {
            System.out.print(localDate + "  ");
        }

        System.out.println();

        currentScherm = "Patient Info";
    }

    void editData(ScreenElements item) {
        int choice;
        boolean nextCycle = true;

        while (nextCycle) {
            item.editMenu();

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                currentScherm = item.editObject(choice);
                scanner.nextLine();
            } else {
                System.out.println("Please enter a *valid* input!");
                scanner.nextLine();
                break;
            }
            if (currentScherm != null) {
                nextCycle = false;
            }
        }
    }

    void displayInfo(ScreenElements item) {
        boolean nextCycle = true;

        while (nextCycle) {
            item.displayInfo();
            item.displayMenu();

            if (scanner.hasNextInt()) {
                currentScherm = item.chooseFromMenu(scanner.nextInt());
                if (currentScherm != null) {
                    nextCycle = false;

                }

            } else {
                System.out.println("Please enter a *valid* input!");
                scanner.nextLine();
                break;
            }
        }

    }

    void printListMenu(String returnPage, String nextPage, String addPage){
        boolean nexCycle = true;

        System.out.format("%d: Return\n", Instances.RETURN);
        System.out.format("%d: Choose from list\n", Instances.CHOOSEFROMLIST);
        System.out.format("%d: Add\n", Instances.ADDTOLIST);
        System.out.print("enter #choice: ");

        while (nexCycle) {
            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("Please enter a *valid* input!");
                scanner.nextLine();
                break;
            }

            switch (choice) {
                case Instances.RETURN:
                    currentScherm = returnPage;
                    nexCycle = false;
                    break;

                case Instances.CHOOSEFROMLIST:
                    currentScherm = nextPage;
                    nexCycle = false;
                    break;

                case Instances.ADDTOLIST:
                    currentScherm = addPage;
                    nexCycle = false;
                    break;

                default:
                    System.out.println("Please enter a *valid* digit");
                    break;
            }

        }

    }

    <T> T printList(List<T> list, String returnPage, String nextPage) {
        int choice;
        int maxPage = (int) Math.ceil((double) list.size() / 5);
        int page = 0;

        System.out.format("%d: Return\n", Instances.RETURN);

        while (true) {
            for (int i = 1; i <= 5; i++) {
                if ((i - 1 + page * 5) < list.size()) {
                    System.out.format("%d: %s\n", i, list.get((i - 1 + page * 5)).toString());

                }else {
                    System.out.println("...");
                }
            }

            System.out.format(" <--- %s --->\n", ("[" + (page + 1) + "/" + maxPage + "]"));
            System.out.format("   %d:        %d:\n", Instances.PREVIOUS, Instances.NEXT);
            System.out.format("%d: Search\n", Instances.SEARCH);
            System.out.print("enter #choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("Please enter a *valid* input!");
                scanner.nextLine();
                break;
            }

            switch (choice) {
                case Instances.RETURN:
                    currentScherm = returnPage;
                    return null;

                case 1:
                    currentScherm = nextPage;
                    return list.get((page * 5));

                case 2:
                    currentScherm = nextPage;
                    return list.get(1 + (page * 5));

                case 3:
                    currentScherm = nextPage;
                    return list.get(2 + (page * 5));

                case 4:
                    currentScherm = nextPage;
                    return list.get(3 + (page * 5));

                case 5:
                    currentScherm = nextPage;
                    return list.get(4 + (page * 5));

                case Instances.SEARCH:
                    System.out.print("Please enter a search term: ");
                    currentScherm = nextPage;
                    scanner.nextLine();
                    return searchInList(scanner.nextLine(), list);

                case Instances.PREVIOUS:
                    if (page == 0) {
                        System.out.println("Already on first page");
                    } else {
                        System.out.format("===== %s %s\n", currentScherm, "=".repeat(80 - currentScherm.length()));
                        page--;
                    }
                    break;

                case Instances.NEXT:
                    if (page + 1 == maxPage) {
                        System.out.println("Already on last page");
                    } else {
                        System.out.format("===== %s %s\n", currentScherm, "=".repeat(80 - currentScherm.length()));
                        page++;
                    }
                    break;

                default:
                    System.out.println("Please enter a *valid* digit");
                    break;
            }
        }
        return null;
    }

    <T> T addItem(ScreenElements item, int idNumber, String nextScreen) {
        currentScherm = nextScreen;
        return item.addObject(idNumber);
    }

    <T> T searchInList(String searchedInList, List<T> list) {

        for (T searchedItem : list) {
            if (Objects.equals(searchedItem.toString(), searchedInList)) {
                return searchedItem;

            }
        }
        return null;
    }
}