import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

class Patient {
    String name;
    boolean isPriority;

    public Patient(String name, boolean isPriority) {
        this.name = name;
        this.isPriority = isPriority;
    }

    //How a patient is formatted
    @Override
    public String toString() {
        return name + (isPriority ? " [Priority]" : " [Regular]");
    }
}

public class main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        // Using Deque (Double-Ended Queue) so we can add to the back (enqueue) and add to the front (undo)
        Deque<Patient> priorityQueue = new LinkedList<>();
        Deque<Patient> regularQueue = new LinkedList<>();
        
        //LIFO Stack for the Undo button
        Stack<Patient> historyStack = new Stack<>();
        
        boolean running = true;
        
        while (running) {
            System.out.println("\nSmart Hospital Ticketing System");
            System.out.println("1. Register New Patient");
            System.out.println("2. Serve Next Patient");
            System.out.println("3. Undo Last Service (Recall Patient)");
            System.out.println("4. View Current Queues");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            
            int choice = input.nextInt();
            input.nextLine();
            
            
            // REGISTRATION PROTOCOL
            // SERVICE PROTOCOL (Dequeue)
            // REVERSAL PROTOCOL (Undo/Recall)
            // SYSTEM STATE
            // SHUTDOWN
            switch (choice) {
                case 1:
                    System.out.print("Enter Patient Name: ");
                    String name = input.nextLine();
                    System.out.print("Is this a Priority case? (Y/N): ");
                    char status = input.next().charAt(0);
                    boolean isPriority = (status == 'y' || status == 'Y');
                    
                    Patient newPatient = new Patient(name, isPriority);
                    
                    if (isPriority) {
                        priorityQueue.addLast(newPatient); // Add to back
                    } else {
                        regularQueue.addLast(newPatient); // Add to back
                    }
                    System.out.println(">> " + name + " added to the " + (isPriority ? "Priority" : "Regular") + " queue.");
                    break;
                    
                case 2:
                    Patient servedPatient = null;
                    
                    //Priority Routing
                    if (!priorityQueue.isEmpty()) {
                        servedPatient = priorityQueue.pollFirst();
                    } else if (!regularQueue.isEmpty()) {
                        servedPatient = regularQueue.pollFirst();
                    }
                    
                    if (servedPatient != null) {
                        System.out.println(">> Now serving: " + servedPatient.name);
                        historyStack.push(servedPatient); 
                    } else {
                        System.out.println(">> No patients currently waiting.");
                    }
                    break;
                    
                case 3:
                    if (!historyStack.isEmpty()) {
                        // Pop the last served patient from the Stack
                        Patient recalledPatient = historyStack.pop();
                        System.out.println(">> Undo successful. Recalling: " + recalledPatient.name);
                        
                        // Put them back at the FRONT of their original queue
                        if (recalledPatient.isPriority) {
                            priorityQueue.addFirst(recalledPatient);
                        } else {
                            regularQueue.addFirst(recalledPatient);
                        }
                    } else {
                        System.out.println(">> History is empty. Nothing to undo.");
                    }
                    break;
                    
                case 4:
                    System.out.println("\nCurrent Status");
                    System.out.println("Priority Queue: " + priorityQueue);
                    System.out.println("Regular Queue:  " + regularQueue);
                    System.out.println("Service History (Stack): " + historyStack);
                    break;
                    
                case 5:
                    running = false;
                    System.out.println(">> Shutting down system. Goodbye!");
                    break;
                    
                default:
                    System.out.println(">> Invalid option. Please try again.");
            }
        }
        input.close();
    }
}