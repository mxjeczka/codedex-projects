import java.util.Scanner;

public class Drakar {
    public static void main(String[] args) {

        System.out.println("This is Drakar. He's a dragon from the Fire Nation.");
        System.out.println("                \\||/");
        System.out.println("                |  @___oo");
        System.out.println("      /\\  /\\   / (__,,,,|");
        System.out.println("     ) /^\\) ^\\/ _)");
        System.out.println("     )   /^\\/   _)");
        System.out.println("     )   _ /  / _)");
        System.out.println(" /\\  )/\\/ ||  | )_)");
        System.out.println("<  >      |(,,) )__)");
        System.out.println(" ||      /    \\)___)\\");
        System.out.println(" | \\____(      )___) )___");
        System.out.println("  \\______(_______;;; __;;;");
        System.out.println(); 

        System.out.println("Drakar loves to fly high above the mountains and breathe fire!");
        System.out.println("But watch out, he gets hungry and tired quickly.");
        System.out.println(); 

        Scanner scanner = new Scanner (System.in);
        int firePower = 10;
        int hunger = 10;
        int energy = 10;
        String choice = "";

        
        System.out.println("Current stats:");
        System.out.println("Hunger: " + hunger);
        System.out.println("Energy: " + energy);
        System.out.println("Firepower: " + firePower);
        System.out.println();

        System.out.println(" p - Play with Drakar (costs energy and hunger, increases firepower)");
        System.out.println(" f - Feed Drakar (increases hunger, costs energy)");
        System.out.println(" r - Rest Drakar (restores energy)");
        System.out.println(" q - Quit the game");
        System.out.println();

        while (!choice.equals("q")){
        System.out.println("============================");
        System.out.println("What do you wanna do?");
        choice = scanner.nextLine();

        if (choice.equals("p")) {
            hunger -= 2;
            energy -= 4;
            firePower += 5;
            System.out.println();
            System.out.println("You played with Drakar!");

        } else if (choice.equals("f")) {
            hunger += 3;
            energy -= 2;
            System.out.println();
            System.out.println("You fed Drakar.");

        } else if (choice.equals("r")) {
            energy += 5;
            System.out.println();
            System.out.println("Drakar is resting... ");

        } else if (choice.equals("q")) {
            System.out.println();
            System.out.println("Goodbye, Dragon Master!");

        } else {
            System.out.println();
            System.out.println("Invalid command.");
        }

        System.out.println();
        System.out.println("Updated stats:");
        System.out.println("Hunger: " + hunger);
        System.out.println("Firepower: " + firePower);
        System.out.println("Energy: " + energy);
        System.out.println();
        }
       scanner.close();
    }
}
