package org.example.zorgapp_maven;

import java.time.LocalDate;
import java.util.ArrayList;

class Administration {
    Patient currentPatient;
    User currentUser;
    Medication currentMedication;
    Consultation currentConsultation;
    Scherm scherm = new Scherm();
    ArrayList<Role>  roleList = new ArrayList<>();
    ArrayList<Patient> patientList = new ArrayList<>();
    ArrayList<Consultation> consultationsList = new ArrayList<>();
    ArrayList<Medication> medicationList = new ArrayList<>();
    JsonHandler jsonHandler = new JsonHandler();
    Patient defaultPatient = new Patient(999, "Default", "Default", LocalDate.parse("2006-06-24"), 1.80, 70, null);
    Medication defaultMedication = new Medication(999, "Default", false, 0, null);

    public void gotToPage(String page){
        System.out.format("===== %s %s\n", page, "=".repeat(80 - page.length()));

        switch (page) {
            case "Menu":
                currentUser = scherm.menu(roleList);
                break;

            case "Patient List Menu":
                scherm.printListMenu("Menu", "Patient List", "Add Patient");
                System.out.println(scherm.currentScherm);
                break;

            case "Patient List":
                currentPatient = scherm.printList(jsonHandler.getPatientList(), "Patient List Menu", "Patient Info");
                break;

            case "Patient Info":
                scherm.displayInfo(currentPatient);
                break;

            case "Edit Patient Info":
                scherm.editData(currentPatient);
                jsonHandler.editPatient(currentPatient);
                break;

            case "Add Patient":
                Patient newPatient = scherm.addItem(defaultPatient, patientList.size(), "Patient List Menu");
                jsonHandler.createPatient(newPatient);
                break;

            case "Medication List Menu":
                if (!currentUser.role.medication) {
                    System.out.println("You don't have permission to view a medication!");
                    scherm.currentScherm = "Patient Info";
                    break;
                }
                scherm.printListMenu("Patient Info", "Medication List", "Add Medication");
                break;

            case "Medication List":
                currentMedication = scherm.printList(getUserMedicationAccess(currentPatient, currentUser), "Medication List Menu", "View Medication");
                break;

            case "View Medication":
                scherm.displayInfo(currentMedication);
                break;

            case "Add New Medication":
                Medication newMedication = scherm.addItem(defaultMedication, currentPatient.medication.size(), "Medication List Menu");
                currentPatient.medication.add(newMedication);
                jsonHandler.createMedication(currentPatient, newMedication);
                break;

            case "Add Medication Menu":
                scherm.printListMenu("Medication List", "Add Medication", "Add New Medication");
                break;

            case "Add Medication":
                Medication newMedication2 = scherm.printList(medicationList, "Add Medication Menu", "Patient List");
                System.out.println(newMedication2.description);
                jsonHandler.createMedication(currentPatient, newMedication2);
                break;

            case "Edit Medication":
                if (!currentUser.role.edit) {
                    System.out.println("You don't have permission to view a medication!");
                    scherm.currentScherm = "View Medication";
                    break;
                }
                scherm.editData(currentMedication);
                jsonHandler.editMedication(currentPatient, currentMedication);
                loadJsonToArrays();
                break;

            case "BMI Graph":
                if (getPatientConsultationList(currentPatient, consultationsList).isEmpty()) {
                    System.out.println("No data to make the graph!");
                    scherm.currentScherm = "Patient Info";
                    break;
                }
                ArrayList<Double> bmiValues = getBMIList(getPatientConsultationList(currentPatient, consultationsList));
                scherm.printGraph(bmiValues, getHighestValue(bmiValues), getLowestValue(bmiValues), getDateOfConsultation(getPatientConsultationList(currentPatient, consultationsList)));
                break;

            case "Consultation List":
                currentConsultation = scherm.printList(getPatientConsultationList(currentPatient, consultationsList), "Consultation List Menu", "View Consultation");
                break;

            case "Consultation List Menu":
                scherm.printListMenu("Patient Info", "Consultation List", "Add Consultation");
                break;

            case "View Consultation":
                scherm.displayInfo(currentConsultation);
                break;

            case "Add Consultation":
                jsonHandler.addConsultation(scherm.addConsultation(currentUser, currentPatient));
                loadJsonToArrays();
                break;

            case "Lung Capacity Graph":
                if (!currentUser.role.lungCapacity) {
                    System.out.println("You don't have permission to view the lung capacity!");
                    scherm.currentScherm = "Patient Info";
                    break;
                } else if (getPatientConsultationList(currentPatient, consultationsList).isEmpty()) {
                    System.out.println("No data to make the graph!");
                    scherm.currentScherm = "Patient Info";
                    break;
                }
                ArrayList<Double> lungCapacityList = getLungCapacityList(getPatientConsultationList(currentPatient, consultationsList));
                scherm.printGraph(lungCapacityList, getHighestValue(lungCapacityList), getLowestValue(lungCapacityList), getDateOfConsultation(getPatientConsultationList(currentPatient, consultationsList)));
                break;


        }
    }

    private ArrayList<Double> getBMIList(ArrayList<Consultation> consultationsList) {
        ArrayList<Double> bmiValues =  new ArrayList<>();
        for (Consultation consultation : consultationsList) {
            if (bmiValues.size() >= 5) {
                break;
            }
            bmiValues.add(Patient.calcBMI(consultation.weight, consultation.length));
        }

        return bmiValues;
    }

    private ArrayList<Double> getLungCapacityList(ArrayList<Consultation> consultationsList) {
        ArrayList<Double> lungCapacity =  new ArrayList<>();
        for (Consultation consultation : consultationsList) {
            if (lungCapacity.size() >= 5) {
                break;
            }
            lungCapacity.add(consultation.lungCapacity);
        }

        return lungCapacity;
    }

    private double getHighestValue(ArrayList<Double> list) {
        double highestBmi = list.getFirst();

        for (Double bmi : list) {
            if (bmi > highestBmi) {
                highestBmi = bmi;
            }
        }
        return highestBmi;
    }

    private double getLowestValue(ArrayList<Double> list) {
        double lowestBmi = list.getFirst();

        for (Double bmi : list) {
            if (bmi < lowestBmi) {
                lowestBmi = bmi;
            }
        }
        return lowestBmi;
    }

    private ArrayList<Medication> getUserMedicationAccess(Patient patient, User currentUser) {
        ArrayList<Medication> medicationList = new ArrayList<>();

        for (int i = 0; i < patient.medication.size(); i++) {
            if (currentUser.role.verdovende) {
                medicationList.add(patient.medication.get(i));
            }
            if (!currentUser.role.verdovende && !patient.medication.get(i).anesthetize) {
                medicationList.add(patient.medication.get(i));
            }
        }

        return medicationList;
    }

    private ArrayList<Consultation> getPatientConsultationList(Patient patient, ArrayList<Consultation> consultations) {
        ArrayList<Consultation> consultationList = new ArrayList<>();

        for (Consultation consultation : consultations) {
            if (consultation.id == patient.id) {
                consultationList.add(consultation);
            }
        }

        return consultationList;
    }

    private ArrayList<LocalDate> getDateOfConsultation(ArrayList<Consultation> consultations) {
        ArrayList<LocalDate> dateList = new ArrayList<>();
        for (Consultation consultation : consultations) {
            if (dateList.size() >= 5) {
                break;
            }
            dateList.add(consultation.date);
        }
        return dateList;
    }

    private void loadJsonToArrays() {
        roleList.clear();
        patientList.clear();
        consultationsList.clear();
        medicationList.clear();
        roleList.addAll(jsonHandler.getRoles());
        patientList.addAll(jsonHandler.getPatientList());
        consultationsList.addAll(jsonHandler.getConsultations());
        medicationList.addAll(jsonHandler.getMedicationFromJSONFile());
    }

    Administration() {
        loadJsonToArrays();

    }
}