import java.sql.Array;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Person[] people = new Person[] {
                new Person("Cleopatra", "Egypt", "69 BC", 1),
                new Person("Alexander the Great", "Macedon", "356 BC", 2),
                new Person("Julius Caesar", "Rome", "100 BC", 3),
                new Person("Hannibal", "Carthage", "247 BC", 4),
                new Person("Confucius", "China", "551 BC", 5),
                new Person("Pericles", "Greece", "429 BC", 6),
                new Person("Spartacus", "Thrace", "111 BC", 7),
                new Person("Marcus Aurelius", "Rome", "121 AD", 8),
                new Person("Leonidas", "Greece", "540 BC", 9),
                new Person("Sun Tzu", "China", "544 BC", 10),
                new Person("Hammurabi", "Babylon", "1750 BC", 11),
        };
//        Person  human = new Person ("Jacek", "Polska","06/12/91", 2  );
//        human.setName("Placek");
//        human.setPassport();
//
//        Person againAnotherPerson = new Person("quo","vadis");
//        human.setDateOfBirth("06/12/91");
//        Person anotherHuman = new Person("Wacek", "anglia", "01/01/22", 3);
//        anotherHuman.setPassport();
//        Person brotherOfHuman = new Person(human);
//        brotherOfHuman.setName("Gacek");
//        System.out.println(human);
//        System.out.println(anotherHuman);
//        System.out.println(brotherOfHuman);
        Airline airline = new Airline();

        for (Person person : people) {
            if(person.applyPassport()){
                person.setPassport();
                airline.createReservation(person);
            }
            else {
                System.out.println("Sorry " + person.getName() + ". Your passport: " + Arrays.toString(person.getPassport()) + " is not valid.\n");
            }

            airline.setPerson(person);

        }
//        System.out.println(airline.getPerson(2));
//        System.out.println(airline.getPerson(5));
//        for (int i = 0; i < people.length; i++) {
//            boolean passportApproved = people[i].applyPassport();
//            if (passportApproved) {
//                people[i].setPassport();
//                airline.createReservation(people[i]);
//            }
//        }
    }



}

