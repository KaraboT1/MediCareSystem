package com.mycompany.medicaresystem;

// HospitalSystem.java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class HospitalSystem {
    private ArrayList<Patient> patients;
    private BedManagement bedManager;
    private Scanner scanner;

    public HospitalSystem() {
        patients = new ArrayList<>();
        bedManager = new BedManagement();
        scanner = new Scanner(System.in);
    }

    // ===== PATIENT MANAGEMENT =====
    public void registerPatient() {
        System.out.println("\n===== REGISTER NEW PATIENT =====");
        
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine().trim();
        
        // Check for duplicate ID
        if (searchPatientByID(id) != null) {
            System.out.println("Error: Patient ID " + id + " already exists!");
            return;
        }
        
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine().trim();
        
        int age = 0;
        try {
            System.out.print("Enter Age: ");
            age = Integer.parseInt(scanner.nextLine().trim());
            if (age < 0 || age > 150) throw new IllegalArgumentException();
        } catch (Exception e) {
            System.out.println("Invalid age! Setting to 0.");
        }
        
        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine().trim();
        System.out.print("Enter Medical Condition: ");
        String condition = scanner.nextLine().trim();
        
        System.out.println("\nSelect Patient Category:");
        System.out.println("1 - INPATIENT");
        System.out.println("2 - OUTPATIENT");
        System.out.println("3 - EMERGENCY");
        System.out.print("Choice: ");
        int catChoice;
        try {
            catChoice = Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            catChoice = 2; // Default to Outpatient
        }
        
        PatientCategory category;
        switch (catChoice) {
            case 1: category = PatientCategory.INPATIENT; break;
            case 3: category = PatientCategory.EMERGENCY; break;
            default: category = PatientCategory.OUTPATIENT;
        }

        Patient newPatient;
        if (category == PatientCategory.INPATIENT) {
            // Try to allocate bed first
            if (!bedManager.allocateBed(id)) {
                System.out.println("No beds available! Cannot register as Inpatient.");
                System.out.println("Registering as OUTPATIENT instead.");
                category = PatientCategory.OUTPATIENT;
                newPatient = new Patient(id, firstName, lastName, age, gender, condition, category);
            } else {
                // Find allocated bed info
                String bedInfo = "";
                for (int i = 1; i <= 20; i++) {
                    String bedCode = "B" + String.format("%02d", i);
                    if (bedManager.isBedOccupied(bedCode)) {
                        // Simplified - assign bed number
                        bedInfo = bedCode;
                    }
                }
                newPatient = new Inpatient(id, firstName, lastName, age, gender, condition, category, "Ward 1", bedInfo);
            }
        } else {
            newPatient = new Patient(id, firstName, lastName, age, gender, condition, category);
        }

        patients.add(newPatient);
        System.out.println("Patient registered successfully!");
    }

    public Patient searchPatientByID(String patientID) {
        for (Patient p : patients) {
            if (p.getPatientID().equalsIgnoreCase(patientID)) {
                return p;
            }
        }
        return null;
    }

        public void searchPatient() {
        System.out.println("\n===== SEARCH PATIENT =====");
        System.out.print("Enter Patient ID to search: ");
        String id = scanner.nextLine().trim();
        
        Patient found = searchPatientByID(id);
        if (found != null) {
            System.out.println("\n Patient Found:");
            System.out.println(found.displayDetails());
        } else {
            System.out.println("Patient with ID " + id + " not found.");
        }
    }

    public void updatePatient() {
        System.out.println("\n===== UPDATE PATIENT =====");
        System.out.print("Enter Patient ID to update: ");
        String id = scanner.nextLine().trim();
        
        Patient p = searchPatientByID(id);
        if (p == null) {
            System.out.println("Patient not found.");
            return;
        }
        
        System.out.println("\nCurrent Details:");
        System.out.println(p.displayDetails());
        System.out.println("\nLeave blank to keep current value.");
        
        System.out.print("New First Name: ");
        String fn = scanner.nextLine().trim();
        if (!fn.isEmpty()) p.setFirstName(fn);
        
        System.out.print("New Last Name: ");
        String ln = scanner.nextLine().trim();
        if (!ln.isEmpty()) p.setLastName(ln);
        
        System.out.print("New Age: ");
        String ageStr = scanner.nextLine().trim();
        if (!ageStr.isEmpty()) {
            try {
                p.setAge(Integer.parseInt(ageStr));
            } catch (Exception e) {
                System.out.println("Invalid age — not updated.");
            }
        }
        
        System.out.print("New Medical Condition: ");
        String cond = scanner.nextLine().trim();
        if (!cond.isEmpty()) p.setMedicalCondition(cond);
        
        System.out.println("Patient details updated!");
    }

    public void deletePatient() {
        System.out.println("\n===== DELETE PATIENT =====");
        System.out.print("Enter Patient ID to delete: ");
        String id = scanner.nextLine().trim();
        
        Patient p = searchPatientByID(id);
        if (p == null) {
            System.out.println("Patient not found.");
            return;
        }
        
        // Release bed if inpatient
        if (p instanceof Inpatient) {
            bedManager.releaseBed(id);
        }
        
        patients.remove(p);
        System.out.println("Patient deleted successfully!");
    }

    public void displayAllPatients() {
        System.out.println("\n===== ALL REGISTERED PATIENTS (" + patients.size() + ") =====");
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        for (Patient p : patients) {
            System.out.println(p.displayDetails());
        }
    }

    public void sortPatientsBySurname() {
        Collections.sort(patients, Comparator.comparing(Patient::getLastName, String.CASE_INSENSITIVE_ORDER));
        System.out.println("Patients sorted by surname:");
        displayAllPatients();
    }

    public void sortPatientsByID() {
        Collections.sort(patients, Comparator.comparing(Patient::getPatientID, String.CASE_INSENSITIVE_ORDER));
        System.out.println("Patients sorted by Patient ID:");
        displayAllPatients();
    }

    // ===== BED MANAGEMENT =====
    public void allocateBedToPatient() {
        System.out.println("\n===== ALLOCATE BED =====");
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine().trim();
        
        Patient p = searchPatientByID(id);
        if (p == null) {
            System.out.println("Patient not found.");
            return;
        }
        if (p.getCategory() != PatientCategory.INPATIENT) {
            System.out.println("Only INPATIENTS can be allocated beds.");
            return;
        }
        
        if (bedManager.allocateBed(id)) {
            System.out.println("Bed allocated successfully!");
        } else {
            System.out.println("No beds available.");
        }
    }

    public void releasePatientBed() {
        System.out.println("\n===== RELEASE BED =====");
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine().trim();
        
        if (bedManager.releaseBed(id)) {
            System.out.println("Bed released successfully!");
        } else {
            System.out.println("No occupied bed found for this patient.");
        }
    }

    // ===== REPORTS =====
    public void generateReports() {
        System.out.println("\n========== WARD REPORTS ==========");
        System.out.println("1. All Patients Report");
        System.out.println("2. Bed Availability Report");
        System.out.println("3. Occupancy Summary");
        System.out.println("4. Full Ward Layout");
        System.out.print("\nSelect report to generate: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            switch (choice) {
                case 1:
                    displayAllPatients();
                    System.out.println("\nTotal Registered Patients: " + patients.size());
                    break;
                case 2:
                    bedManager.displayAvailableBeds();
                    break;
                case 3:
                    bedManager.displayOccupiedBeds();
                    System.out.println("\n===== OCCUPANCY SUMMARY =====");
                    System.out.println("Total Beds: " + bedManager.getTotalBeds());
                    System.out.println("Available:  " + bedManager.getAvailableBedCount());
                    System.out.println("Occupied:  " + bedManager.getOccupiedBedCount());
                    System.out.printf("Occupancy:  %.1f%%\n", bedManager.getOccupancyPercentage());
                    break;
                case 4:
                    bedManager.displayWardLayout();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    // ===== MAIN MENU =====
    public void displayMainMenu() {
        while (true) {
            System.out.println("\n");
            System.out.println("========================================");
            System.out.println("    MEDICARE HOSPITAL ADMISSION SYSTEM  ");
            System.out.println("========================================");
            System.out.println("PATIENT MANAGEMENT:");
            System.out.println("  1. Register New Patient");
            System.out.println("  2. Search Patient by ID");
            System.out.println("  3. Update Patient Details");
            System.out.println("  4. Delete Patient");
            System.out.println("  5. Display All Patients");
            System.out.println("  6. Sort Patients by Surname");
            System.out.println("  7. Sort Patients by ID");
            System.out.println("\nBED MANAGEMENT:");
            System.out.println("  8. Allocate Bed to Patient");
            System.out.println("  9. Release Patient Bed");
            System.out.println(" 10. Display Ward Layout");
            System.out.println(" 11. Display Available Beds");
            System.out.println(" 12. Display Occupied Beds");
            System.out.println("\nREPORTS:");
            System.out.println(" 13. Generate Ward Reports");
            System.out.println("  0. Exit");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 0:
                        System.out.println("\n Thank you for using MediCare System. Goodbye!");
                        return;
                    case 1: registerPatient(); break;
                    case 2: searchPatient(); break;
                    case 3: updatePatient(); break;
                    case 4: deletePatient(); break;
                    case 5: displayAllPatients(); break;
                    case 6: sortPatientsBySurname(); break;
                    case 7: sortPatientsByID(); break;
                    case 8: allocateBedToPatient(); break;
                    case 9: releasePatientBed(); break;
                    case 10: bedManager.displayWardLayout(); break;
                    case 11: bedManager.displayAvailableBeds(); break;
                    case 12: bedManager.displayOccupiedBeds(); break;
                    case 13: generateReports(); break;
                    default: System.out.println("Invalid choice — try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Getters for testing
    public ArrayList<Patient> getPatients() { return patients; }
    public BedManagement getBedManager() { return bedManager; }
}