package org.example.zorgapp_maven;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class JsonHandler {
    private final JSONParser parser = new JSONParser();

    public void createPatient(Patient newPatient) {
        JSONArray patientList = loadJsonArray("patients.json");

        JSONObject patient = new JSONObject();
        patient.put("id", newPatient.id);
        patient.put("surname", newPatient.surname);
        patient.put("name", newPatient.firstName);
        patient.put("dob", newPatient.dateOfBirth.toString());
        patient.put("length", newPatient.length);
        patient.put("weight", newPatient.weight);

        JSONArray medicationsArray = new JSONArray();
        for (Medication med : newPatient.medication) {
            medicationsArray.add(medicationToJson(med));
        }
        patient.put("medication", medicationsArray);

        patientList.add(patient);
        saveJsonArray("src/patients.json", patientList);
    }

    public void createMedication(Patient patient, Medication newMedication) {
        JSONArray patientList = loadJsonArray("src/patients.json");

        for (Object obj : patientList) {
            JSONObject jsonPatient = (JSONObject) obj;

            if (((Long) jsonPatient.get("id")).intValue() == patient.id) {
                JSONArray medications = (JSONArray) jsonPatient.get("medication");
                medications.add(medicationToJson(newMedication));
                jsonPatient.put("medication", medications);
                break;
            }
        }

        saveJsonArray("src/patients.json", patientList);
    }

    public void editPatient(Patient editPatient) {
        JSONArray patientList = loadJsonArray("src/patients.json");

        for (Object obj : patientList) {
            JSONObject patient = (JSONObject) obj;
            if (((Long) patient.get("id")).intValue() == editPatient.id) {
                patient.put("surname", editPatient.surname);
                patient.put("name", editPatient.firstName);
                patient.put("dob", editPatient.dateOfBirth.toString());
                patient.put("length", editPatient.length);
                patient.put("weight", editPatient.weight);

                JSONArray medications = new JSONArray();
                for (Medication med : editPatient.medication) {
                    medications.add(medicationToJson(med));
                }
                patient.put("medication", medications);
                break;
            }
        }

        saveJsonArray("src/patients.json", patientList);
    }

    public void editMedication(Patient currentPatient, Medication editMedication) {
        JSONArray patientList = loadJsonArray("src/patients.json");

        for (Object obj : patientList) {
            JSONObject patient = (JSONObject) obj;

            if (((Long) patient.get("id")).intValue() == currentPatient.id) {
                JSONArray meds = (JSONArray) patient.get("medication");

                for (Object medObj : meds) {
                    JSONObject med = (JSONObject) medObj;
                    if (((Long) med.get("id")).intValue() == editMedication.id) {
                        med.put("name", editMedication.name);
                        med.put("anesthetize", editMedication.anesthetize);
                        med.put("quantity", editMedication.quantity);
                        med.put("description", editMedication.description);
                        med.put("class", editMedication.type);
                        med.put("dose", editMedication.dosage);
                        break;
                    }
                }
                break;
            }
        }

        saveJsonArray("src/patients.json", patientList);
    }

    public ArrayList<Patient> getPatientList() {
        ArrayList<Patient> patients = new ArrayList<>();
        JSONArray patientList = loadJsonArray("src/patients.json");

        for (Object obj : patientList) {
            JSONObject patient = (JSONObject) obj;
            int id = ((Long) patient.get("id")).intValue();
            String surname = (String) patient.get("surname");
            String name = (String) patient.get("name");
            LocalDate dob = LocalDate.parse((String) patient.get("dob"));
            double length = getDoubleValue(patient.get("length"));
            double weight = getDoubleValue(patient.get("weight"));

            JSONArray meds = (JSONArray) patient.get("medication");
            ArrayList<Medication> medicationList = getMedications(meds);

            patients.add(new Patient(id, surname, name, dob, length, weight, medicationList));
        }

        return patients;
    }

    public ArrayList<Medication> getMedications(JSONArray medArray) {
        ArrayList<Medication> meds = new ArrayList<>();
        for (Object obj : medArray) {
            JSONObject med = (JSONObject) obj;
            int id = ((Long) med.get("id")).intValue();
            String name = (String) med.get("name");
            String type = (String) med.get("class");
            String quantity = (String) med.get("quantity");
            double dose = getDoubleValue(med.get("dose"));
            boolean anesthetize = (boolean) med.get("anesthetize");

            meds.add(createMedicationType(type, id, name, anesthetize, dose, quantity));
        }
        return meds;
    }

    private JSONObject medicationToJson(Medication med) {
        JSONObject json = new JSONObject();
        json.put("id", med.id);
        json.put("name", med.name);
        json.put("dose", med.dosage);
        json.put("quantity", med.quantity);
        json.put("anesthetize", med.anesthetize);

        if (med instanceof Tablet) {
            json.put("class", "tablet");
        } else if (med instanceof Injectable) {
            json.put("class", "injectable");
        } else if (med instanceof Inhalation) {
            json.put("class", "inhalation");
        } else if (med instanceof Topical) {
            json.put("class", "topical");
        } else if (med instanceof Drinkable) {
            json.put("class", "drinkable");
        } else {
            json.put("class", "other"); // fallback
        }

        return json;
    }

    private <T> T createMedicationType(String type, int id, String name, boolean anesthetize, double dose, String quantity) {
        return switch (type) {
            case "tablet" -> (T) new Tablet(id, name, anesthetize, dose, quantity);
            case "injectable" -> (T) new Injectable(id, name, anesthetize, dose, quantity);
            case "inhalation" -> (T) new Inhalation(id, name, anesthetize, dose, quantity);
            case "topical" -> (T) new Topical(id, name, anesthetize, dose, quantity);
            case "drinkable" -> (T) new Drinkable(id, name, anesthetize, dose, quantity);
            default -> (T) new Other(id, name, anesthetize, dose, quantity);
        };
    }

    public ArrayList<Role> getRoles() {
        ArrayList<Role> roles = new ArrayList<>();
        JSONArray roleArray = loadJsonArray("src/roles.json");

        for (Object obj : roleArray) {
            JSONObject role = (JSONObject) obj;
            roles.add(new Role(
                    (String) role.get("name"),
                    (Boolean) role.get("medication"),
                    (Boolean) role.get("verdovende"),
                    (Boolean) role.get("edit"),
                    (Boolean) role.get("lungCapacity")
            ));
        }

        return roles;
    }

    public ArrayList<Consultation> getConsultations() {
        ArrayList<Consultation> consultations = new ArrayList<>();
        JSONArray array = loadJsonArray("src/consultation.json");

        for (Object obj : array) {
            JSONObject c = (JSONObject) obj;
            consultations.add(new Consultation(
                    ((Long) c.get("id")).intValue(),
                    LocalDate.parse((String) c.get("date")),
                    (String) c.get("user"),
                    (String) c.get("patient"),
                    (String) c.get("type"),
                    (String) c.get("description"),
                    getDoubleValue(c.get("length")),
                    getDoubleValue(c.get("weight")),
                    getDoubleValue(c.get("lungCapacity"))
            ));
        }

        return consultations;
    }

    public void addConsultation(Consultation consultation) {
        JSONArray consultations = loadJsonArray("src/consultation.json");

        JSONObject obj = new JSONObject();
        obj.put("id", consultation.id);
        obj.put("date", consultation.date.toString());
        obj.put("user", consultation.user);
        obj.put("patient", consultation.patient);
        obj.put("type", consultation.type);
        obj.put("description", consultation.description);
        obj.put("length", consultation.length);
        obj.put("weight", consultation.weight);
        obj.put("lungCapacity", consultation.lungCapacity);

        consultations.add(obj);
        saveJsonArray("src/consultation.json", consultations);
    }

    public ArrayList<Medication> getMedicationFromJSONFile() {
        ArrayList<Medication> meds = new ArrayList<>();
        JSONArray medArray = loadJsonArray("src/medication.json");

        for (Object obj : medArray) {
            JSONObject med = (JSONObject) obj;
            meds.add(createMedicationType(
                    (String) med.get("class"),
                    ((Long) med.get("id")).intValue(),
                    (String) med.get("name"),
                    (Boolean) med.get("anesthetize"),
                    getDoubleValue(med.get("dose")),
                    (String) med.get("quantity")
            ));
        }

        return meds;
    }

    private JSONArray loadJsonArray(String path) {
        try (FileReader reader = new FileReader(path)) {
            return (JSONArray) parser.parse(reader);
        } catch (Exception e) {
            System.out.println("not working");
            return new JSONArray();
        }
    }

    private void saveJsonArray(String path, JSONArray array) {
        try (FileWriter file = new FileWriter(path)) {
            file.write(array.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double getDoubleValue(Object value) {
        return value instanceof Long ? ((Long) value).doubleValue() : (double) value;
    }
}
