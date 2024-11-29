package ru.kirill.nalemian;

import java.util.ArrayList;
import java.util.List;

public class Man {
    public Man dad;
    public Man mom;
    public List<Man> children = new ArrayList<>();
    public String name;

    public Man(String name) {
        this.name = name;
    }
}
