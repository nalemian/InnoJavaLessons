package ru.kirill.nalemian;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Man {
    private Man dad;
    private Man mom;
    private List<Man> children = new ArrayList<>();
    private String name;

    public Man(String name) {
        this.name = name;
    }

    public Man getDad() {
        return dad;
    }

    public void setDad(Man dad) {
        this.dad = dad;
    }

    public Man getMom() {
        return mom;
    }

    public void setMom(Man mom) {
        this.mom = mom;
    }

    public List<Man> getChildren() {
        return children;
    }

    public void setChildren(List<Man> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String childrenNames = children.stream()
                .map(Man::getName)
                .collect(Collectors.joining(", "));
        return String.format("%s [%s]", name, childrenNames);
    }
}
