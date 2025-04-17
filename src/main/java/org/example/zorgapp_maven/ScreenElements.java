package org.example.zorgapp_maven;

public interface ScreenElements {
    void displayInfo();
    void displayMenu();
    String chooseFromMenu(int choice);
    void editMenu();
    String editObject(int choice);
    <T> T addObject(int idNumber);
}