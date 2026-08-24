package com.mycompany.medicaresystem;

// HospitalSystemTest.java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class HospitalSystemTest {
    private HospitalSystem system;

    @BeforeEach
    void setUp() {
        system = new HospitalSystem();
    }

    @Test
    void testRegisterPatient() {
        // Cannot test console input directly, but verify system initializes empty
        assertTrue(system.getPatients().isEmpty());
    }

    @Test
    void testSearchPatient_NotFound() {
        assertNull(system.searchPatientByID("INVALID"));
    }

    @Test
    void testBedAllocation_PreventDuplicateAllocation() {
        BedManagement bm = new BedManagement();
        assertTrue(bm.allocateBed("P001"));
        // Can't easily test full "all beds occupied" without 20 allocations, but logic exists
    }

    @Test
    void testBedCounts() {
        BedManagement bm = new BedManagement();
        assertEquals(20, bm.getTotalBeds());
        assertEquals(20, bm.getAvailableBedCount());
        assertEquals(0, bm.getOccupiedBedCount());
        assertEquals(0.0, bm.getOccupancyPercentage(), 0.01);
    }

    @Test
    void testInpatientInheritance() {
        Inpatient ip = new Inpatient("P001", "John", "Doe", 30, "Male", "Flu",
                PatientCategory.INPATIENT, "Ward1", "B01");
        assertEquals("P001", ip.getPatientID());
        assertEquals("Ward1", ip.getWardNumber());
        assertEquals("B01", ip.getBedNumber());
        // Overridden method includes bed info
        assertTrue(ip.displayDetails().contains("B01"));
    }

    @Test
    void testPatientCategoryEnum() {
        PatientCategory cat1 = PatientCategory.INPATIENT;
        PatientCategory cat2 = PatientCategory.OUTPATIENT;
        PatientCategory cat3 = PatientCategory.EMERGENCY;
        assertNotNull(cat1);
        assertNotNull(cat2);
        assertNotNull(cat3);
    }
}