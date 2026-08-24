package com.mycompany.medicaresystem;
// BedManagement.java
public class BedManagement {
    // 4 rows × 5 columns = 20 beds
    private String[][] wardBeds;
    private final int ROWS = 4;
    private final int COLS = 5;

    public BedManagement() {
        wardBeds = new String[ROWS][COLS];
        initializeBeds();
    }

    // Initialize all beds as available
    private void initializeBeds() {
        int bedNum = 1;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                wardBeds[i][j] = "B" + String.format("%02d", bedNum);
                bedNum++;
            }
        }
    }

    // Allocate bed to patient
    public boolean allocateBed(String patientID) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (!wardBeds[i][j].startsWith("OCCUPIED")) {
                    wardBeds[i][j] = "OCCUPIED:" + patientID + ":" + wardBeds[i][j];
                    return true;
                }
            }
        }
        return false; // No beds available
    }

    // Release patient's bed
    public boolean releaseBed(String patientID) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (wardBeds[i][j].startsWith("OCCUPIED:" + patientID)) {
                    String bedCode = wardBeds[i][j].split(":")[2];
                    wardBeds[i][j] = bedCode;
                    return true;
                }
            }
        }
        return false;
    }

    // Display full ward layout
    public void displayWardLayout() {
        System.out.println("\n===== WARD BED LAYOUT =====");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (wardBeds[i][j].startsWith("OCCUPIED")) {
                    System.out.print("[X] " + wardBeds[i][j].split(":")[2] + " ");
                } else {
                    System.out.print("[✓] " + wardBeds[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    // Display available beds
    public void displayAvailableBeds() {
        System.out.println("\n===== AVAILABLE BEDS =====");
        int count = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (!wardBeds[i][j].startsWith("OCCUPIED")) {
                    System.out.print(wardBeds[i][j] + "  ");
                    count++;
                }
            }
        }
        System.out.println("\nTotal Available: " + count);
    }

    // Display occupied beds
    public void displayOccupiedBeds() {
        System.out.println("\n===== OCCUPIED BEDS =====");
        int count = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (wardBeds[i][j].startsWith("OCCUPIED")) {
                    String[] parts = wardBeds[i][j].split(":");
                    System.out.println(parts[2] + " → Patient: " + parts[1]);
                    count++;
                }
            }
        }
        System.out.println("Total Occupied: " + count);
    }

    // Get total counts
    public int getTotalBeds() { return ROWS * COLS; }
    public int getOccupiedBedCount() {
        int count = 0;
        for (String[] row : wardBeds) {
            for (String bed : row) {
                if (bed.startsWith("OCCUPIED")) count++;
            }
        }
        return count;
    }
    public int getAvailableBedCount() { return getTotalBeds() - getOccupiedBedCount(); }
    public double getOccupancyPercentage() {
        return Math.round(((double)getOccupiedBedCount() / getTotalBeds()) * 100 * 10.0) / 10.0;
    }

    // Check if bed is occupied
    public boolean isBedOccupied(String bedCode) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (wardBeds[i][j].contains(bedCode)) {
                    return wardBeds[i][j].startsWith("OCCUPIED");
                }
            }
        }
        return false;
    }
}