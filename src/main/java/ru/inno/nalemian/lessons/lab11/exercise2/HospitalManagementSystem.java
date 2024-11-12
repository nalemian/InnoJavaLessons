package ru.inno.nalemian.lessons.lab11.exercise2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final Map<Integer, Patient> patients = new HashMap<>();
    private static final Map<Integer, Doctor> doctors = new HashMap<>();
    private static final Map<Integer, Receptionist> receptionists = new HashMap<>();

    public static void main(String[] args) {
        Doctor doctor1 = new Doctor(1, "Dr. Smith");
        doctors.put(doctor1.getDoctorId(), doctor1);

        Receptionist receptionist1 = new Receptionist(1, "Alice");
        receptionists.put(receptionist1.getReceptionistId(), receptionist1);

        Patient patient1 = new Patient(1, "John Doe");
        patients.put(patient1.getPatientId(), patient1);

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nWelcome to the Hospital Management System");
            System.out.println("Choose user type:");
            System.out.println("1. Patient");
            System.out.println("2. Receptionist");
            System.out.println("3. Doctor");
            System.out.println("4. Exit");

            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> handlePatient(scanner);
                case 2 -> handleReceptionist(scanner);
                case 3 -> handleDoctor(scanner);
                case 4 -> System.out.println("Exiting system...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private static void handlePatient(Scanner scanner) {
        System.out.print("Enter your Patient ID: ");
        int patientId = scanner.nextInt();

        Patient patient = patients.get(patientId);
        if (patient != null) {
            System.out.println("Hello, " + patient.getName() + "!");
            System.out.println("1. Pay Bill");
            int action = scanner.nextInt();

            if (action == 1) {
                patient.payBill();
            } else {
                System.out.println("Invalid action.");
            }
        } else {
            System.out.println("Patient not found.");
        }
    }

    private static void handleReceptionist(Scanner scanner) {
        System.out.print("Enter your Receptionist ID: ");
        int receptionistId = scanner.nextInt();

        Receptionist receptionist = receptionists.get(receptionistId);
        if (receptionist != null) {
            System.out.println("Hello, " + receptionist.getReceptionistId());
            System.out.println("1. Give Appointment");
            System.out.println("2. Generate Bill");
            int action = scanner.nextInt();

            switch (action) {
                case 1 -> {
                    System.out.print("Enter Patient ID: ");
                    int patientId = scanner.nextInt();
                    Patient patient = patients.get(patientId);
                    if (patient != null) {
                        System.out.print("Enter Doctor ID: ");
                        int doctorId = scanner.nextInt();
                        Doctor doctor = doctors.get(doctorId);
                        if (doctor != null) {
                            receptionist.giveAppointment(patient, doctor);
                        } else {
                            System.out.println("Doctor not found.");
                        }
                    } else {
                        System.out.println("Patient not found.");
                    }
                }
                case 2 -> {
                    System.out.print("Enter Patient ID: ");
                    int patientId = scanner.nextInt();
                    Patient patient = patients.get(patientId);
                    if (patient != null) {
                        System.out.print("Enter Bill Name: ");
                        scanner.nextLine(); // Consume newline
                        String billName = scanner.nextLine();
                        System.out.print("Enter Amount: ");
                        double amount = scanner.nextDouble();
                        receptionist.generateBill(billName, amount, patient);
                    } else {
                        System.out.println("Patient not found.");
                    }
                }
                default -> System.out.println("Invalid action.");
            }
        } else {
            System.out.println("Receptionist not found.");
        }
    }

    private static void handleDoctor(Scanner scanner) {
        System.out.print("Enter your Doctor ID: ");
        int doctorId = scanner.nextInt();

        Doctor doctor = doctors.get(doctorId);
        if (doctor != null) {
            System.out.println("Hello, " + doctor.getName());
            System.out.print("Enter Patient ID to check: ");
            int patientId = scanner.nextInt();
            Patient patient = patients.get(patientId);

            if (patient != null) {
                doctor.checkPatient(patient);
            } else {
                System.out.println("Patient not found.");
            }
        } else {
            System.out.println("Doctor not found.");
        }
    }
}

