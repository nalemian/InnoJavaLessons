package innoLessons.lab11.exercise2;

public class Receptionist {
    private final int receptionistId;
    private final String name;
    private static int billCounter = 1;

    public Receptionist(int receptionistId, String name) {
        this.receptionistId = receptionistId;
        this.name = name;
    }

    public int getReceptionistId() {
        return receptionistId;
    }

    public void giveAppointment(Patient patient, Doctor doctor) {
        System.out.println(name + " has given an appointment to patient " + patient.getName() + " with doctor " + doctor.getName());
    }

    public Bill generateBill(String billName, double amount, Patient patient) {
        Bill bill = new Bill(billCounter++, billName, amount, patient.getPatientId(), receptionistId);
        patient.assignBill(bill);
        System.out.println(name + " has generated a bill for patient " + patient.getName() + ": " + bill);
        return bill;
    }
}

