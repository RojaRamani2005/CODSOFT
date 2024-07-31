import java.util.Scanner;
 public class Chatbot{
    private static String getResponse(String userInput) {
        userInput = userInput.toLowerCase();

        if (userInput.contains("hello") || userInput.contains("hi")) {
            return "Hello! How can I assist you today?";
        } else if (userInput.contains("how are you")) {
            return "I'm just a bot, but I'm here to help! How can I assist you?";
        } else if (userInput.contains("what is your name")) {
            return "I don't have a name, but you can call me Chatbot.";
        } else if (userInput.contains("help")) {
            return "Sure! What do you need help with?";
        } else if (userInput.contains("bye") || userInput.contains("goodbye")) {
            return "Goodbye! Have a great day!";
        } else {
            return "I'm sorry, I don't understand that. Can you please rephrase?";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Chatbot! Type 'bye' to end the conversation.");

        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();
            if (userInput.toLowerCase().contains("bye") || userInput.toLowerCase().contains("goodbye")) {
                System.out.println("Chatbot: Goodbye! Have a great day!");
                break;
            }
            String response = getResponse(userInput);
            System.out.println("Chatbot: " + response);
        }

        scanner.close();
    }
}
