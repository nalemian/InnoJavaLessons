package ru.kirill.nalemian;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                child.mom = mom;
                child.dad = dad;
                mom.children.add(child);
                dad.children.add(child);
                childIndex++;
            }
        }

        people.forEach(man -> System.out.println(man.name));
        return people;
    }

    @Test
    void printOrphans() {
        generatePeople(5, 10).stream()
                .filter(man -> man.name.contains("child"))
                .filter(man -> man.dad == null && man.mom == null)
                .map(man -> man.name)
                .forEach(System.out::println);
    }

    @Test
    void printParentsWithOneChild() {
        final boolean[] isFirst = {true};
        generatePeople(5, 10).stream()
                .filter(man -> man.children.size() == 1)
                .filter(man -> man.dad == null && man.mom == null)
                .map(man -> man.name)
                .forEach(name -> {
                    if (isFirst[0]) {
                        System.out.print(name);
                        isFirst[0] = false;
                    } else {
                        System.out.print(", " + name);
                    }
                });
        System.out.println();
    }
}
