/*
Purpose: Learn how to write in Kotlin by making a blackjack game
*/

// Imports:
import kotlin.random.Random

data class cards(val cardName: String, val value: Int)

/*
Plan for the blackjack class:
Have two main methods that will control all game functionality related to the cards in the game.

deal() method:
Deal all cards to the player. Will rely on the shuffle method to randomize all the cards in the deck

shuffle() method:
Shuffles all the cards in the deck. Will be used by the deal function.

Use of the data class of cards:
The idea of using this form of representation for the cards is to be able to store the name and value of each card without needing to match names and values with seperate arrays.
One idea is that I could create a list of data class cards that store each card/value.

* */

public class Blackjack(){

    /*
    * Create a list containing all the cards needed for a deck. This would be 52 cards without jokers
    * */

    private val cardDeck = listOf(cards("Ace1 of Diamonds", 1), cards("One of Diamonds", 1), cards("Two of Diamonds", 2), cards("Three of Diamonds", 3), cards("Four of Diamonds", 4), cards("Five of Diamonds", 5), cards("Six of Diamonds", 6), cards("Seven of Diamonds", 7),
        cards("Eight of Diamonds", 8), cards("Nine of Diamonds", 9), cards("Ten of Diamonds", 10), cards("Jack of Diamonds", 10), cards("Queen of Diamonds", 10), cards("King of Diamonds", 10), cards("Ace2 of Diamonds", 11),
        cards("Ace1 of Hearts", 1), cards("One of Hearts", 1), cards("Two of Hearts", 2), cards("Three of Hearts", 3), cards("Four of Hearts", 4), cards("Five of Hearts", 5), cards("Six of Hearts", 6), cards("Seven of Hearts", 7),
        cards("Eight of Hearts", 8), cards("Nine of Hearts", 9), cards("Ten of Hearts", 10), cards("Jack of Hearts", 10), cards("Queen of Hearts", 10), cards("King of Hearts", 10), cards("Ace2 of Hearts", 11),
        cards("Ace1 of Spades", 1), cards("One of Spades", 1), cards("Two of Spades", 2), cards("Three of Spades", 3), cards("Four of Spades", 4), cards("Five of Spades", 5), cards("Six of Spades", 6), cards("Seven of Spades", 7),
        cards("Eight of Spades", 8), cards("Nine of Spades", 9), cards("Ten of Spades", 10), cards("Jack of Spades", 10), cards("Queen of Spades", 10), cards("King of Spades", 10), cards("Ace2 of Spades", 11),
        cards("Ace1 of Clubs", 1), cards("One of Clubs", 1), cards("Two of Clubs", 2), cards("Three of Clubs", 3), cards("Four of Clubs", 4), cards("Five of Clubs", 5), cards("Six of Clubs", 6), cards("Seven of Clubs", 7),
        cards("Eight of Clubs", 8), cards("Nine of Clubs", 9), cards("Ten of Clubs", 10), cards("Jack of Clubs", 10), cards("Queen of Clubs", 10), cards("King of Clubs", 10), cards("Ace2 of Clubs", 11))

    private var shuffledDeck = mutableListOf<cards>()

    // Getter method for the regular deck. (Primarily used for debugging)
    fun getCards(){
        for (card in cardDeck){
            println("${card.cardName}: ${card.value}")
        }
    }

    // Getter method for the shuffled deck. (Primarily used for debugging)
    fun getShuffledCards(){
        for (card in shuffledDeck){
            println("Card: ${card.cardName}\nValue: ${card.value}")
        }
    }

    // Function to deal cards
    // Expanding Idea: Add a return type to return the name and value of each card to the user
    fun deal(type: Int): Int{
        /*
        * Algorithm Idea: Get a random index, print the values at that index from the shuffled cards deck
        * Then, using a for loop, iterate through the shuffled deck list and find the matching card that was just dealt, then remove it from the deck
        * */

        var randomShuffledIndex = shuffledDeck.random()
        var card_value = 0

        if (type == 0){
            println("Your card is: ${randomShuffledIndex.cardName}, value: ${randomShuffledIndex.value}")
            card_value = randomShuffledIndex.value
        }
        else if (type == 1){
            println("The dealers first card is: ${randomShuffledIndex.cardName}, value: ${randomShuffledIndex.value}")
            card_value = randomShuffledIndex.value
        }
        else if (type == 2){
            println("The dealer is dealt a second card")
            card_value = randomShuffledIndex.value
        }



        // Remove matching card from shuffled deck
        var i = 0
        for (card in shuffledDeck){
            if ((shuffledDeck[i].cardName == randomShuffledIndex.cardName) && (shuffledDeck[i].value == randomShuffledIndex.value)){
                shuffledDeck.removeAt(i)
                break
            }
            i++
        }

        return card_value
    }

    // Function to shuffle the deck
    fun shuffle(){
        //var random_index = cardDeck.random()

        // For loop that takes a random index from the card deck and puts it into the shuffled deck list
        for (card in cardDeck){
            var randomIndex = cardDeck.random()

            shuffledDeck.add(cards(randomIndex.cardName, randomIndex.value))
        }

        //print the shuffled deck for testing
        //getShuffledCards()
    }

    // This function deals with the dealers hand. This function will be run after the user gets their cards dealt
    // Retunrs the total hand value of the dealer
    fun dealerHand(): Int{
        var dealer_hand_total = 0
        var dealer_type = 1
        // Initally deals twice to the dealer
        // Need to figure out a way to only display one of the dealers cards
        dealer_hand_total += deal(dealer_type)
        dealer_type += 1
        dealer_hand_total += deal(dealer_type)

        // Checks if the dealer will hit based off of the standard rules the dealer follows
        while (dealer_hand_total <= 16){
            dealer_hand_total += deal(dealer_type)
        }

        return dealer_hand_total
    }

    // Function that contains the whole game itself. Leaves the main function for only dealing with the basics of the game.
    // This function will handle things like dealing cards, shuffling the deck, and actually playing the game
    // Parameter(s): Reference to the players hand list
    fun startGame(balance: Double): Double{
        // Start the game by shuffling the deck
        // The users bet amount
        var bet_amount = 0.0
        var new_balance = 0.0
        // The hand dealt to the player. Will contain the sum of card values
        var player_hand_total = 0
        var dealer_hand_total = 0

        println("-----Starting the game!-----")

        // Loop to get users bet amount. Continuously prompts user until they enter a valid bet amount
        while (true){
            print("Please enter a bet amount: ")
            bet_amount = readln().toDouble()

            if (bet_amount > balance){
                println("You cannot bet more than you have in your balance! Try again!")
                continue
            }
            else if (bet_amount <= 0){
                println("You cannot bet zero or lower! Try again!")
            }
            else if (bet_amount > 0){
                println("Your bet amount is: $${bet_amount}")
                break
            }
        }

        // Calls the shuffle method to shuffle the deck
        println("Shuffling the deck...")
        shuffle()

        // Calls the deal method to deal a card twice
        player_hand_total += deal(0)
        player_hand_total += deal(0)

        // Then, print out the players total. Prompt the user if they want to "hit" or "stand". Double down and/or split will come later
        while (true){
            println("Your hand total is: ${player_hand_total}")

            if (player_hand_total >= 22){
                println("Sorry, you have lost the hand")
                new_balance = balance - bet_amount
                return new_balance
            }

            println("Would you like to hit or stand? (H/S)")
            // Variable that stores the user input
            var user_input = readlnOrNull()

            // Checks the user input
            if (user_input == "H"){
                // Deal again
                player_hand_total += deal(0)
            }
            else if (user_input == "S"){
                println("You decide to stand")
                break
            }
            else{
                continue
            }
        }

        dealer_hand_total = dealerHand()

        println("Your hand total is: ${player_hand_total}")
        println("The dealers hand total is: ${dealer_hand_total}")

        if (dealer_hand_total >= 22){
            println("Congrats! You have won the hand!")
            bet_amount *= 1.5
            return bet_amount
        }
        else if (player_hand_total > dealer_hand_total){
            println("Congrats! You have won the hand!")
            bet_amount *= 1.5
            return bet_amount
        }
        else if (player_hand_total <= dealer_hand_total){
            println("Sorry, you have lost the hand")
            new_balance = balance - bet_amount
            return balance
        }
        return -1.0
    }
}

fun main(){

    // Define possible variables that shouldn't be defined in the scope of the main loop

    // Player balance
    var balance = 0.0
    // Player bet amount
    //var bet_amount = 0
    // List of dealt cards given to the player
    var player_hand = mutableListOf<cards>()

    // Create a Blackjack Object
    val Game = Blackjack()

    // Primary game loop
    while (true) {
        // Prompt the user to play the game
        println("----- Welcome to the Blackjack Table! -----")
        print("Would you like to play? (Y/N): ")

        var user_input = readlnOrNull()

        if (user_input == "N" || user_input == ""){
            break
        }

        // Inputs total balance and checks if it is greater than zero
        print("Welcome to the game! Please enter your balance: ")
        balance = readln().toDouble()
        if (balance <= 0){
            println("YOU CANNOT HAVE A BALANCE OF ZERO OR LESS! LEAVE THE TABLE!")
            break
        }

        balance = Game.startGame(balance)
        continue
    }
    println("----- Thank you for playing! -----")
}