package com.example.nanapiyasa;

public class Student {
    private int id;
    private String name;
    private String phone;
    private boolean paid;

    public Student(int id, String name, String phone, boolean paid) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.paid = paid;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public boolean isPaid() { return paid; }
}
