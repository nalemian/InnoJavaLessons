package ru.kirill.nalemian;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class ManTest {
    List<Man> generatePeople(int numOfParents, int numOfChildren) {
        List<Man> people = new ArrayList<>();
        Random random = new Random();
        List<Man> moms = new ArrayList<>();
        List<Man> dads = new ArrayList<>();
        List<Man> children = new ArrayList<>();
        for (int index = 1; index <= numOfParents; index++) {
            Man mom = new Man("mom" + index);
            Man dad = new Man("dad" + index);
            moms.add(mom);
            dads.add(dad);
            people.add(mom);
            people.add(dad);
        }
        for (int index = 1; index <= numOfChildren; index++) {
            Man child = new Man("child" + index);
            children.add(child);
            people.add(child);
        }
        int childIndex = 1;
        for (int index = 1; index <= numOfParents; index++) {
            Man mom = moms.get(index - 1);
            Man dad = dads.get(index - 1);
            int childrenOfParents = random.nextInt(3);
            for (int counter = 0; counter < childrenOfParents && childIndex <= numOfChildren; counter++) {
                Man child = children.get(childIndex - 1);
                child.setMom(mom);
                child.setDad(dad);
                mom.getChildren().add(child);
                dad.getChildren().add(child);
                childIndex++;
            }
        }

        people.forEach(man -> System.out.println(man.getName()));
        return people;
    }

    @Test
    void printOrphans() {
        generatePeople(5, 10).stream()
                .filter(man -> man.getName().contains("child"))
                .filter(man -> man.getDad() == null && man.getMom() == null)
                .map(Man::getName)
                .forEach(System.out::println);
    }

    @Test
    void printParentsWithOneChild() {
        System.out.println(generatePeople(5, 10).stream()
                .filter(man -> man.getChildren().size() == 1)
                .map(Man::getName)
                .collect(Collectors.joining(", "))
        );
    }

    @Test
    void printGroupedByDadName() {
        var result = generatePeople(5, 10).stream()
                .collect(Collectors.groupingBy(man -> Optional.ofNullable(man.getDad()).map(Man::getName).orElse("John Doe")));
    }

    @Test
    void printUsingToString() {
        generatePeople(5, 10).forEach(System.out::println);
    }


    @Test
    void collectingAndThenExample() {
        String namesAsString = generatePeople(5, 10).stream()
                .map(Man::getName)
                .collect(Collectors.collectingAndThen(
                        Collectors.joining(", "),
                        result -> "Names: " + result
                ));
        System.out.println(namesAsString);
    }

    @Test
    void filteringExample() {
        List<String> childrenNames = generatePeople(5, 10).stream()
                .collect(Collectors.filtering(
                        man -> man.getName().contains("child"),
                        Collectors.mapping(Man::getName, Collectors.toList())
                ));
        System.out.println(childrenNames);
    }

}
