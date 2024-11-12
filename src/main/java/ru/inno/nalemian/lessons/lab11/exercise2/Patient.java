package ru.inno.nalemian.lessons.lab11.exercise2;

public class Patient {
    private final int patientId;
    private final String name;
    private Bill bill;

    public Patient(int patientId, String name) {
        this.patientId = patientId;
        this.name = name;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public void assignBill(Bill bill) {
        this.bill = bill;
    }

    public void payBill() {
        if (bill == null) {
            System.out.println(name + ", you have no bill to pay.");
        } else {
            System.out.println(name + " has paid the bill: " + bill);
            bill = null;
        }
    }
}

