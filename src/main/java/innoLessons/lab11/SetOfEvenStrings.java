package innoLessons.lab11;

import java.util.HashSet;
import java.util.Set;

public class SetOfEvenStrings {
    public static void main(String[] args) {
        Set<String> stringSet = new HashSet<String>();
        stringSet.add("Klykva");
        stringSet.add("Kukuruza");
        stringSet.add("Kreker");
        stringSet.add("Apple");
        stringSet.add("Mango");
        stringSet.removeIf(s -> s.length() % 2 != 0); //это вместо кода ниже:
//        Iterator<String> iterator = stringSet.iterator();
//        while (iterator.hasNext()) {
//            String s = iterator.next();
//            if (s.length() % 2 != 0) {
//                iterator.remove();
//            }
//        }
        System.out.println("Set with even-length elements: " + stringSet);
    }
}
