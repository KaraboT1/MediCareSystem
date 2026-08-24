package com.mycompany.medicaresystem;
// Inpatient.java
public class Inpatient extends Patient {
    // Additional attributes for Inpatient
    private String wardNumber;
    private String bedNumber;

    // Constructor using super()
    public Inpatient(String patientID, String firstName, String lastName, int age, String gender,
                     String medicalCondition, PatientCategory category, String wardNumber, String bedNumber) {
        super(patientID, firstName, lastName, age, gender, medicalCondition, category);
        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }

    // Getters and Setters
    public String getWardNumber() { return wardNumber; }
    public void setWardNumber(String wardNumber) { this.wardNumber = wardNumber; }
    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String bedNumber) { this.bedNumber = bedNumber; }

    // Override displayDetails()
    @Override
    public String displayDetails() {
        return super.displayDetails() + String.format(" | Ward: %-4s | Bed: %-3s", wardNumber, bedNumber);
    }
}