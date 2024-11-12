package innoLessons.lab11.exercise2;

public class Doctor {
    private final int doctorId;
    private final String name;

    public Doctor(int doctorId, String name) {
        this.doctorId = doctorId;
        this.name = name;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public void checkPatient(Patient patient) {
        System.out.println("Doctor " + name + " is checking patient " + patient.getName());
    }
}