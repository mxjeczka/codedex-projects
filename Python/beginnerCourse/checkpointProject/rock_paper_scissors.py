import random

choices = ["✊", "✋", "✌️", "🦎", "🖖"]

# Dictionary: Key wins against the symbols in the list
win_rules = {
    "✊": ["✌️", "🦎"],
    "✋": ["✊", "🖖"],
    "✌️": ["✋", "🦎"],
    "🦎": ["✋", "🖖"],
    "🖖": ["✊", "✌️"]
}
# Start of the infinite loop
while True:
    print("=================================")
    print("Rock Paper Scissors Lizard Spock")
    print("=================================")
    # Get user input as a number
    user_input = int(input("1) ✊\n2) ✋\n3) ✌️\n4) 🦎 \n5) 🖖\nPick a number: "))

    # Connect the number to the emoji
    if user_input == 1:
        print("You chose: ✊")
        user_choice = "✊"
    elif user_input == 2:
        print("You chose: ✋")
        user_choice = "✋"
    elif user_input == 3:
        print("You chose: ✌️")
        user_choice = "✌️"
    elif user_input == 4:
        print("You chose: 🦎")
        user_choice = "🦎"
    elif user_input == 5:
        print("You chose: 🖖")
        user_choice = "🖖"
    else:
        print("Pick a number from 1 to 5")
        continue  # Skip the rest of the code and restart the loop

    # Computer picks a random emoji
    cpu_choice = random.choice(choices)
    print("Cpu chose:", cpu_choice)

    # Check the winner
    if user_choice == cpu_choice:
        print("It's a tie!")
    elif cpu_choice in win_rules[user_choice]:
        print("🎉 YOU won! 🎉")
    elif user_choice in win_rules[cpu_choice]:
        print("🤖 CPU won! 🤖")
    else:
        print("Invalid choice!")

    # Ask the user to play again or quit
    print("----------------------------------")
    again = input("Do you want to play again? (y/n): ")

    # Convert input to lowercase and remove spaces,
    # then check if user wants to quit
    if again.lower().strip() == "n":
        print("Thanks for playing:) Goodbye!")
        break
