import java.util.Random;
import java.lang.StringBuilder;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class Simulation{
    public static void main(String[] args) throws InterruptedException, IllegalArgumentException {

        String[] names = {"Dan", "Steve", "Peter", "Andy", "Matthew", "Paul", "Robert", "Angelo"};
        
        Gate gate = new Gate();
        Elevator elevator = new Elevator(1500);
        PaymentTerminal paymentTerminal = new PaymentTerminal();
        ServiceManager serviceManager = new ServiceManager("Seva");
        CarQueue carQueue = new CarQueue();

        Parking parking = new Parking(gate, elevator, paymentTerminal, serviceManager, carQueue);

        parking.getLevels().add(new Level(0, 2));
        parking.getLevels().add(new Level(1, 3));

        parking.getServiceManager().open(parking);
        TimeUnit.SECONDS.sleep(2);
        System.out.println();

        System.out.println("INITIAL CAR ENTERED THE PARKING");
        parking.getCarQueue().addCar(new Car(generateID(), new Driver("Collin"), 1500));
        parking.parkTheCar();
        System.out.println();
        

        String[] scenarios = {"Simple car with mass < 1500", "Simple car with mass >= 1500", "Electric car with a charged battery", "Electric car with a low battery", "Disability car"};

        System.out.println("Introduce one of the scenarios available:");
        for (int i = 1; i <= scenarios.length; i++) {
            System.out.println(i + " " + scenarios[i - 1] + " comes to the parking");
        }
        System.out.println();

        Scanner scann = new Scanner(System.in);
        int scenario = scann.nextInt();
        Car car;

        switch(scenario){
            case 1:
                car = new Car(generateID(), new Driver("Chris"), 1000); 
                break;
            case 2: 
                car = new Car(generateID(), new Driver("Andy"), 2500); 
                break;
            case 3:
                car = new ElectricCar(generateID(), new Driver("Sam"), 2000, 8000, 5000);
                break;
            case 4:
                car = new ElectricCar(generateID(), new Driver("Dave"), 3000, 8000, 3000);
                break;
            case 5:
                car = new DisabilityCar(generateID(), new Driver("Matthew"), 2000);
                break;
            default:
                throw new IllegalArgumentException("No such scenario available!");
        }

        System.out.println();
        parking.getCarQueue().addCar(car);
        proceedCar(car, parking);        

        System.out.println();
        TimeUnit.SECONDS.sleep(2);
        if(scenario == 4){
            parking.getServiceManager().supplyTheChargers(parking.getLevels());
        }
    }

    private static String generateID(){
        StringBuilder sb = new StringBuilder();
        Random r = new Random();

        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for(int i = 0; i < 3; i++){
            int index = r.nextInt(26);
            sb.append(alphabet.charAt(index));
        }
        sb.append(" ");

        int num = r.nextInt(900) + 100;
        sb.append(num);

        return sb.toString();
    }

    private static void proceedCar(Car car, Parking parking) throws InterruptedException{
        parking.parkTheCar();
        System.out.println();
        TimeUnit.SECONDS.sleep(2);
        parking.removeTheCar(car.getID());
    }
}