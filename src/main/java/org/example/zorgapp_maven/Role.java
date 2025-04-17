package org.example.zorgapp_maven;

public class Role {
    String name;
    boolean medication;
    boolean verdovende;
    boolean edit;
    boolean lungCapacity;

    Role(String  name, boolean medication, boolean verdovende, boolean edit, boolean lungCapacity) {
        this.name = name;
        this.medication = medication;
        this.verdovende = verdovende;
        this.edit = edit;
        this.lungCapacity = lungCapacity;

    }

}
