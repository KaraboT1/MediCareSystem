#MediCare Hospital System

Student Name: Karabo Mamalema Tema
Student Number: ST10513531
Course: PROG6112
Assignment: MediCare Hospital Patient Admission System
Date: 24 August 2026

##Project Overview
This is a Java console application for managing patient admissions, bed allocation, and records at MediCare Hospital. The system follows Object-Oriented Programming principles and provides a menu-driven interface for easy use.

##Features
-Register new patients with unique ID validation
-Search for patients by ID
-Update patient details
-Delete patient records
-Display all patients
-Sort patients by surname or ID
-Three patient categories: INPATIENT, OUTPATIENT, EMERGENCY
-20-bed ward management using a 2D array (4 rows × 5 columns)
-Allocate and release beds
-View ward layout, available beds, and occupied beds
-Generate reports and occupancy statistics
-Input validation and error handling
-JUnit 5 unit tests included

##Project Structure

MediCareSystem/
- src/
    PatientCategory.java
    Patient.java
    Inpatient.java
    BedManagement.java
    HospitalSystem.java
    Main.java
  test/
    HospitalSystemTest.java
    README.md

##How to Run
Open the project in NetBeans or your preferred Java IDE
Ensure all files are in the package: package com.mycompany.medicaresystem;
Open Main.java
Run the file
Follow the menu options displayed

##Main Menu Options

    #MEDICARE HOSPITAL ADMISSION SYSTEM

PATIENT MANAGEMENT:
  1. Register New Patient
  2. Search Patient by ID
  3. Update Patient Details
  4. Delete Patient
  5. Display All Patients
  6. Sort Patients by Surname
  7. Sort Patients by ID

BED MANAGEMENT:
  8. Allocate Bed to Patient
  9. Release Patient Bed
 10. Display Ward Layout
 11. Display Available Beds
 12. Display Occupied Beds

REPORTS:
 13. Generate Ward Reports
  0. Exit

Enter your choice:

##Test Data
ID	First Name	Last Name	Age	Gender	Condition	Category
P001	Karabo Matla	45	Male	Pneumonia	INPATIENT
P002	Aphiwe	Zondo	32	Female	Broken Arm	OUTPATIENT
P003	Joseph Dary	67	Male	Heart Attack	EMERGENCY
P004	Nkazi	Nkosi	28	Female	Flu	INPATIENT

##OOP Principles Applied
Encapsulation — Private attributes with getters and setters
Inheritance — Inpatient class extends Patient class
Polymorphism — Overridden displayDetails() method
Abstraction — PatientCategory enum
Composition — BedManagement class handles ward operations

##Important Notes
Duplicate patient IDs are not allowed
Beds are only allocated to INPATIENTS
Maximum capacity: 20 inpatient beds
When an inpatient is deleted, their bed is automatically released
All user input is validated

This project was created for educational purposes as part of the PROG6112 module assignment. All code follows the assignment specifications and marking criteria.
