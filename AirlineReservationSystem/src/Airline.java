public class Airline {


    private Person[] people;

    public Airline(){
        this.people = new Person[11];
     }
     public void createReservation(Person person){
        int index = person.getSeatNumber()-1;
        while (people[index] != null){
            System.out.println("\n" + person.getName() + ", seat: " + person.getSeatNumber() + " is already taken. Please choose another seat.\n");
            person.chooseSeat();
        }
        people[index]= new Person(person);
         System.out.println("Thank you "+person.getName()+" for flying with Java airlines. Your seat number is " + person.getSeatNumber() + ".\n");

     }

    public Person getPerson(int index) {
        Person person = this.people[index];

        return new Person(person);
    }

    public void setPerson(Person person){
        int index = person.getSeatNumber()-1;
        this.people[index]=new Person(person);

    }
}

