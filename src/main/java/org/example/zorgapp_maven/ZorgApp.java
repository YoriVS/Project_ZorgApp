package org.example.zorgapp_maven;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class ZorgApp {
    public static void main(String[] args) {
        JsonHandler jsonhandler = new JsonHandler();
        Administration administration = new Administration();
        List<Role> roleList = new ArrayList<>(jsonhandler.getRoles());
        var scanner = new Scanner(System.in);

        while (true) {
            try {
                if (administration.currentUser == null) {
                    System.out.format("===== Menu %s\n", "=".repeat(76));
                    administration.currentUser = administration.scherm.menu(roleList);

                } else {
                    administration.gotToPage(administration.scherm.currentScherm);

                }

            } catch (InputMismatchException i) {
                System.out.println("Please enter a *valid* input!");
                administration.gotToPage(administration.scherm.currentScherm);

            }

            catch (Exception e) {
                administration.gotToPage(administration.scherm.currentScherm);

            }
        }
    }
}