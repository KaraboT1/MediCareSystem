package com.mycompany.medicaresystem;
// Patient.java
public class Patient {
    // encapsulated private attributes
    private String patientID;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String medicalCondition;
    private PatientCategory category;

    // Constructor
    public Patient(String patientID, String firstName, String lastName, int age, String gender, 
                   String medicalCondition, PatientCategory category) {
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.category = category;
    }

    // getters and Setters (Encapsulation)
    public String getPatientID() { return patientID; }
    public void setPatientID(String patientID) { this.patientID = patientID; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getMedicalCondition() { return medicalCondition; }
    public void setMedicalCondition(String medicalCondition) { this.medicalCondition = medicalCondition; }
    public PatientCategory getCategory() { return category; }
    public void setCategory(PatientCategory category) { this.category = category; }

    // Method to display details (to be overridden)
    public String displayDetails() {
        return String.format(
            "Patient ID: %-8s | Name: %s %-10s | Age: %3d | Gender: %-6s | Category: %-10s | Condition: %s",
            patientID, firstName, lastName, age, gender, category, medicalCondition
        );
    }
}