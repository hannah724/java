import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

public class CardGame {
   
    private ArrayList<Card> deck;
    private ArrayList<Card> p1Cards;
    private ArrayList<Card> p2Cards;
    private ArrayList<Card> communityCards;
    private Font normal = new Font("Serif", Font.PLAIN, 10);
    private Font big = new Font("Serif", Font.PLAIN, 40);
    private Font bigger = new Font("Serif", Font.PLAIN, 25);
    private BufferedImage start, thanks, key;
    private Color brown = new Color(237, 211, 199);
    private Color lBrown = new Color(224, 215, 211);
    private Color black = new Color(0, 0, 0);
    private Color blue = new Color(209, 222, 240);
    private int p1Total, p2Total, screen, p1Bet, p2Bet, pot, p1Points, p2Points, p1Add, p2Add, set, raised, which, highest, playerLose, won, p1HighestSet, p2HighestSet;
    private String action;
    private boolean show = false;
    private boolean p2Turn;
    private boolean p1Win, p2Win;
    private ArrayList<Card> prevCards1, prevCards2;

    public CardGame(){
        deck = new ArrayList<Card>();
        p1Cards = new ArrayList<Card>();
        p2Cards = new ArrayList<Card>();
        prevCards1 = new ArrayList<Card>();
        prevCards2 = new ArrayList<Card>();
        communityCards = new ArrayList<Card>();
        screen = 1;
        p1Points = 10000;
        p2Points = 10000;
        p1Bet = 0;
        p2Bet = 0;
        pot = 0;
        raised = 0;
        set = 0;
        which = 1;
        p1Add = 0;
        p2Add = 0;
        //shared by p1 and p2

        try{
            start = ImageIO.read(new File("start.png"));
        } catch(IOException e){
            System.out.println(e);
        }
        try{
            thanks = ImageIO.read(new File("thanks.png"));
        } catch(IOException e){
            System.out.println(e);
        }
        try{
            key = ImageIO.read(new File("key.png"));
        } catch(IOException e){
            System.out.println(e);
        }


        //cards 0-12 = hearts
        deck.add(new Card("A", 14, "heart"));
        deck.add(new Card("2", 2, "heart"));
        deck.add(new Card("3", 3, "heart"));
        deck.add(new Card("4", 4, "heart"));
        deck.add(new Card("5", 5, "heart"));
        deck.add(new Card("6", 6, "heart"));
        deck.add(new Card("7", 7, "heart"));
        deck.add(new Card("8", 8, "heart"));
        deck.add(new Card("9", 9, "heart"));
        deck.add(new Card("10", 10, "heart"));
        deck.add(new Card("J", 10, "heart"));
        deck.add(new Card("Q", 10, "heart"));
        deck.add(new Card("K", 10, "heart"));
        
        //cards 13-25 = clubs
        deck.add(new Card("A", 14, "club"));
        deck.add(new Card("2", 2, "club"));
        deck.add(new Card("3", 3, "club"));
        deck.add(new Card("4", 4, "club"));
        deck.add(new Card("5", 5, "club"));
        deck.add(new Card("6", 6, "club"));
        deck.add(new Card("7", 7, "club"));
        deck.add(new Card("8", 8, "club"));
        deck.add(new Card("9", 9, "club"));
        deck.add(new Card("10", 10, "club"));
        deck.add(new Card("J", 11, "club"));
        deck.add(new Card("Q", 12, "club"));
        deck.add(new Card("K", 13, "club"));

        //cards 26-38 = spades
        deck.add(new Card("A", 14, "spade"));
        deck.add(new Card("2", 2, "spade"));
        deck.add(new Card("3", 3, "spade"));
        deck.add(new Card("4", 4, "spade"));
        deck.add(new Card("5", 5, "spade"));
        deck.add(new Card("6", 6, "spade"));
        deck.add(new Card("7", 7, "spade"));
        deck.add(new Card("8", 8, "spade"));
        deck.add(new Card("9", 9, "spade"));
        deck.add(new Card("10", 10, "spade"));
        deck.add(new Card("J", 11, "spade"));
        deck.add(new Card("Q", 12, "spade"));
        deck.add(new Card("K", 13, "spade"));

        //cards 39-51 = diamonds
        deck.add(new Card("A", 14, "diamond"));
        deck.add(new Card("2", 2, "diamond"));
        deck.add(new Card("3", 3, "diamond"));
        deck.add(new Card("4", 4, "diamond"));
        deck.add(new Card("5", 5, "diamond"));
        deck.add(new Card("6", 6, "diamond"));
        deck.add(new Card("7", 7, "diamond"));
        deck.add(new Card("8", 8, "diamond"));
        deck.add(new Card("9", 9, "diamond"));
        deck.add(new Card("10", 10, "diamond"));
        deck.add(new Card("J", 11, "diamond"));
        deck.add(new Card("Q", 12, "diamond"));
        deck.add(new Card("K", 13, "diamond"));
        
    }

    public void sortCards(ArrayList<Card> a){
        //sort lowest to highest
        int n = a.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (a.get(j).getValue() > a.get(j + 1).getValue()) {
                        Card temp = a.get(j);
                        a.set(j, a.get(j + 1));
                        a.set(j + 1, temp);
                    }
                }
            }
    }


    

    public void drawGame(Graphics g){
        int x = 0;
        int y = 0;
        //choose diff screens
        switch (screen){
            case 1:
                //start screen (instructions)
                g.drawImage(start, 0, 0, 1000, 600, null);
                break;
            case 2:
                //player 1 turn (cards up)
                g.setColor(brown);
                g.fillRect(0, 0, 1000, 600);

                //draw p2 cards
                x = 400;
                y = 50;
                for (int i = 0; i < 2; i++){
                    p1Cards.get(i).setSide(true);
                    p1Cards.get(i).drawMe(g, x, y);
                    x += 60;
                }

                x = 440;
                y = 400;
                for (int i = 0; i < 2; i++){
                    p2Cards.get(i).setSide(false);
                    p2Cards.get(i).drawMe(g, x, y);
                    x += 60;
                }
                g.setColor(black);
                g.drawString("Player 1 Total Points: " + p1Points, 50, 30);
                g.drawString("Player 2 Total Points: " + p2Points, 50, 50);
                g.drawString("Player 1 Bet: " + p1Bet, 50, 70);
				g.drawString("Pot (total bets): " + pot, 50, 90);
                g.drawString("Highest bet: " + highest, 50, 110);
                g.drawString("Player 1", 480, 30);
                g.drawString("Player 2", 480, 580);
                x = 140;
                y = 230;
                for (int i = 0; i < 5; i++){
                    g.setColor(black);
                    g.drawRect(x, y, 120, 150);
                    x += 130;
                }
                x = 140;
                y = 230;
                for (int j = 0; j < communityCards.size(); j++){
                    communityCards.get(j).drawMe(g, x, y);
                    x += 130;
                }
                break;
            case 3:
                //player 2 turn (cards up)
                g.setColor(brown);
                g.fillRect(0, 0, 1000, 600);

                //draw p2 cards
                x = 440;
                y = 50;
                for (int i = 0; i < 2; i++){
                    p1Cards.get(i).setSide(false);
                    p1Cards.get(i).drawMe(g, x, y);
                    x += 60;
                }

                x = 440;
                y = 400;
                for (int i = 0; i < 2; i++){
                    p2Cards.get(i).setSide(true);
                    p2Cards.get(i).drawMe(g, x, y);
                    x += 60;
                }
                g.setColor(black);
                g.drawString("Player 1 Total Points: " + p1Points, 50, 30);
                g.drawString("Player 2 Total Points: " + p2Points, 50, 50);
                g.drawString("Player 2 Bet: " + p2Bet, 50, 70);
				g.drawString("Pot (total bets): " + pot, 50, 90);
                g.drawString("Highest bet: " + highest, 50, 110);
                g.drawString("Player 1", 480, 30);
                g.drawString("Player 2", 480, 580);
                x = 140;
                y = 230;
                for (int i = 0; i < 5; i++){
                    g.setColor(black);
                    g.drawRect(x, y, 120, 150);
                    x += 130;
                }
                x = 140;
                y = 230;
                for (int j = 0; j < communityCards.size(); j++){
                    communityCards.get(j).drawMe(g, x, y);
                    x += 130;
                }
                break;
            case 4:
                //blank screen when changing turns
                g.setColor(lBrown);
                g.fillRect(0, 0, 1000, 600);
                g.setColor(black);
                g.setFont(big);
                g.drawString("Switching Player...", 350, 150);
                break;
            case 5:
                g.setColor(blue);
                g.fillRect(0, 0, 1000, 600);
                g.setFont(big);
                if (p1Win == true){
                    g.setColor(black);
                    g.drawString("Player 1 Wins " + won + " points!", 300, 150);
                }
                else{
                    g.setColor(black);
                    g.drawString("Player 2 Wins " + won + " points!", 300, 150);
                }
                //celebration screen for player who wins (when other player has nothing to bet)
                break;
            case 6:
                //"thank you for playing" screen
                g.drawImage(thanks, 0, 0, 1000, 600, null);
                break;
            case 7:
                g.setColor(blue);
                g.fillRect(0, 0, 1000, 600);
                g.setFont(bigger);
                g.setColor(black);
                g.drawString("Player " + which + " input blind bet: ", 350, 240);
                if (which == 1){
                    g.drawString("Player 1 Total Points: " + p1Points, 340, 280);
                }
                else{
                    g.drawString("Player 2 Total Points: " + p2Points, 340, 280);
                }
                break;
            case 8:
                //error screen
                g.drawString("Screen does not exist", 400, 200);
                break;
            case 9:
                g.setColor(brown);
                g.fillRect(0, 0, 1000, 600);

                x = 440;
                y = 50;
                for (int i = 0; i < 2; i++){
                    prevCards1.get(i).setSide(true);
                    prevCards1.get(i).drawMe(g, x, y);
                    x += 60;
                }

                x = 440;
                y = 400;
                for (int i = 0; i < 2; i++){
                    prevCards2.get(i).setSide(true);
                    prevCards2.get(i).drawMe(g, x, y);
                    x += 60;
                }
                g.setColor(black);
                g.drawString("Player 1 Total Points: " + p1Points, 50, 30);
                g.drawString("Player 2 Total Points: " + p2Points, 50, 50);
                g.drawString("Player 2 Bet: " + p2Bet, 50, 70);
				g.drawString("Pot (total bets): " + pot, 50, 90);
                g.drawString("Highest bet: " + highest, 50, 110);

                x = 140;
                y = 230;
                for (int i = 0; i < 5; i++){
                    g.setColor(black);
                    g.drawRect(x, y, 120, 150);
                    x += 130;
                }
                x = 140;
                y = 230;
                for (int j = 0; j < communityCards.size(); j++){
                    communityCards.get(j).drawMe(g, x, y);
                    x += 130;
                }
                int winner = 0;
                if (p1Win == true){
                    winner = 1;
                }
                else{
                    winner = 2;
                }
                g.setFont(normal);
                if (p1HighestSet == p2HighestSet){
                    g.drawString("Tie! Both Players have same high card!", 800, 500);
                }
                else{
                    g.drawString("Player " + winner + " wins with the highest set!", 800, 500);
                }
                break;
            case 10:
                g.drawImage(key, 0, 0, 1000, 600, null);
                break;
            default:
                screen = 8;
                break;
        }
        
	}

    //switch player
    public void switching(){
        show = !show;
        if (show == false && p2Turn == true){
            //p2 turn
            screen = 3;
            p2Turn = !p2Turn;
        }
        else if(show == false && p2Turn == false){
            //p1 turn
            screen = 2;
            p2Turn = !p2Turn;
        }
        else{
            //show is true so -> switching players
            screen = 4;
        }
    }

    public void dealCard(){
        //take a card from the top of deck at index 0 and give to player
        for (int i = 0; i < 2; i++){
            p1Cards.add(deck.get(0));
            prevCards1.add(deck.get(0));
            deck.remove(0);
        }
        for (int i = 0; i < 2; i++){
            p2Cards.add(deck.get(0));
            prevCards2.add(deck.get(0));
            deck.remove(0);
        }
    }

    public void addComCard(){
        if (communityCards.size() < 5){
            communityCards.add(deck.get(0));
            p1Cards.add(deck.get(0));
            p2Cards.add(deck.get(0));
            deck.remove(0);
        }
    }

    //diff "slides"
    public void setScreen(int i){
        screen = i;
    }

    public void shuffle(){
        for (int i = 0; i < deck.size(); i++){
            int first = (int)(Math.random() * deck.size());
            for (int j = 0; j < deck.size(); j++){
                Card a = deck.get(j);
                Card b = deck.get(first);
                deck.set(first, a);
                deck.set(j, b);
            }
        }
	}

    //compare what each player has
    //set > suit > value
    public void showdown(){
        sortCards(p1Cards);
        sortCards(p2Cards);
        for (int i = 0; i < p1Cards.size(); i++){
            p1Cards.get(i).setSide(true);
            p2Cards.get(i).setSide(true);
        }

        //check player cards for sets
        p1HighestSet = straightFlush(p1Cards) + fourOfKind(p1Cards) + fullHouse(p1Cards) + flush(p1Cards) + straight(p1Cards) + threeOfKind(p1Cards) + twoPair(p1Cards) + pair(p1Cards);
        p2HighestSet = straightFlush(p2Cards) + fourOfKind(p2Cards) + fullHouse(p2Cards) + flush(p2Cards) + straight(p2Cards) + threeOfKind(p2Cards) + twoPair(p2Cards) + pair(p2Cards);
        
        int p1Sets = p1HighestSet;
        int p2Sets = p2HighestSet;

        playerLose = 0;
        if (p1Sets > p2Sets){
            p1Win = true;
            p2Win = false;
            playerLose = 2;
        }
        else if(p2Sets > p1Sets){
            p2Win = true;
            p1Win = false;
            playerLose = 1;
        }
        else if(p1Sets == p2Sets){
            if (highCard(p1Cards) > highCard(p2Cards)){
                p1Win = true;
                playerLose = 2;
            }
            else{
                p2Win = true;
                playerLose = 1;
            }
        }
        
    }


    //check player hands for sets - also make method to compare player hands in case there are ties
    public int straightFlush(ArrayList<Card> a){
        //check player cards for 5 consecutive AND same suit
        boolean check = false;
        int count = 0;
        for (int i = 0; i < a.size(); i++){
            for (int j = 0; j < a.size() - 1; j++){
                if (a.get(j).getValue() == a.get(j + 1).getValue() + 1 && a.get(j).getSuit() == a.get(j + 1).getSuit()){
                    count++;
                }
            }
            if (count >= 5){
                check = true;
            }
            else{
                count = 0;
            }
        }
        if (check == true){
            return 9;
        }
        return 0;
    }

    public void winScreen(){
        fold(playerLose);
    }

    public int fourOfKind(ArrayList<Card> a){
        //check player cards for 4 of same value card
        int frequency = 0;
        boolean four = false;
        for (int i = 0; i < a.size(); i++){
            for (int j = 0; j < a.size(); j++){
                if (a.get(i).getValue() == a.get(j).getValue()){
                    frequency++;
                }
            }
            if (frequency == 4){
                four = true;
            }
            else{
                frequency = 0;
            }
        }
        if (four == true){
            return 8;
        }
        return 0;
    }

    public int fullHouse(ArrayList<Card> a){
        //check player cards for 3 of a kind and 2 of a kind
        //from number cards in hand passed in, find if there are any repetitions of any of the values
        boolean pair = false;
        boolean triple = false;
        
        if (pair == true && triple == true){
            return 7;
        }
        return 0;
    }

    public int flush(ArrayList<Card> a){
        //check player cards for all of same suit
        boolean suit = false;
        int count = 0;
        for (int i = 0; i < a.size(); i++){
            for (int j = 0; j < a.size(); j++){
                if (a.get(i).getSuit() == a.get(j).getSuit()){
                    count++;
                }
            }
            if (count >= 5){
                suit = true;
            } else{
                count = 0;
            }
        }
        if (suit == true){
            return 6;
        }
        return 0;
    }

    public int straight(ArrayList<Card> a){
        //check player cards for 5 consecutive cards
        boolean value = false;
        int count = 0;
        for (int i = 0; i < a.size(); i++){
            for (int j = 0; j < a.size() - 1; j++){
                if (a.get(j).getValue() == a.get(j + 1).getValue() + 1){
                    count++;
                }
            }
            if (count >= 5){
                value = true;
            }
        }
        if (value == true){
            return 5;
        }
        return 0;
    }

    public int threeOfKind(ArrayList<Card> a){
        //3 of same value card
        int frequency = 0;
        boolean three = false;
        for (int i = 0; i < a.size(); i++){
            for (int j = 0; j < a.size(); j++){
                if (a.get(i).getValue() == a.get(j).getValue()){
                    frequency++;
                }
            }
            if (frequency == 3){
                three = true;
            }
            else{
                frequency = 0;
            }
        }
        if (three == true){
            return 4;
        }
        return 0;
    }

    public int twoPair(ArrayList<Card> a){
        //two pairs
        int frequency = 0;
        int pair = 0;
        boolean two = false;
        for (int i = 0; i < a.size(); i++){
            for (int j = 0; j < a.size(); j++){
                if (a.get(i).getValue() == a.get(j).getValue()){
                    frequency++;
                }
            }
            if (frequency == 2){
                two = true;
                pair++;
            }
            else{
                frequency = 0;
            }
        }
        if (two == true && pair == 2){
            return 3;
        }
        return 0;
    }

    public int pair(ArrayList<Card> a){
        //one pair
        int frequency = 0;
        boolean two = false;
        for (int i = 0; i < a.size(); i++){
            for (int j = 0; j < a.size(); j++){
                if (a.get(i).getValue() == a.get(j).getValue()){
                    frequency++;
                }
            }
            if (frequency == 2){
                two = true;
            }
            else{
                frequency = 0;
            }
        }
        if (two == true){
            return 2;
        }
        return 0;
    }

    public int highCard(ArrayList<Card> a){
        //having highest card (ace)
        int largest = 0;
        for (int i = 0; i < a.size(); i++){
            if (a.get(i).getValue() > largest){
                largest = a.get(i).getValue();
            }
        }
        return largest;
    }


    //actions
    public void call(int p){
        //bet enough to match highest thing betted
        if (p == 1){
            p1Bet += highest;
            pot += highest;
            p1Points -= highest;
        }
        if (p == 2){
            p2Bet += highest;
            pot += highest;
            p2Points -= highest;
        }
        action = "called";
    }

    public void raise(int p, int amt){
        //bet enough to match what has been bet since the last time you bet (calling), then you 'raise' the bet another amount
        if (p == 1){
            //amt = betted amount (bet + raised amt)
            p1Bet += amt;
            p1Add = amt;
            raised += p1Bet;
            p1Points -= amt;
            pot += amt;
        }
        if (p == 2){
            p2Bet += amt;
            p2Add = amt;
            raised += p2Bet;
            p2Points -= amt;
            pot += amt;
        }
        action = "raised";
        highest = amt;
    }

    public void fold(int p){
        //player resigns
        if (p == 1){
            p1Win = false;
            p2Win = true;
            p2Points += pot;
        }
        if (p == 2){
            p2Win = false;
            p1Win = true;
            p1Points += pot;
        }
        won = pot;
        pot = 0;
        screen = 5;
        action = "folded";
    }

    public int getP1Bet(){
        return p1Bet;
    }

    public int getP2Bet(){
        return p2Bet;
    }

    public int getP1Points(){
        return p1Points;
    }

    public int getP2Points(){
        return p2Points;
    }

    public int getScreen(){
        return screen;
    }

    public int getPot(){
        return pot;
    }

    public void setP1Bet(int x){
        p1Bet = x;
    }

    public void setP2Bet(int x){
        p2Bet = x;
    }

    public int getHighest(){
        return highest;
    }

    public boolean pTurn(){
        return p2Turn;
        //p2Turn = true means it's p2 turn
        //p2Turn = false means it's p1 turn
    }

    public void reset(){
        pot = 0;
        p1Bet = 0;
        p2Bet = 0;
        set = 0;
        if (which == 1){
            which = 2;
        }
        else{
            which = 1;
        }
        for (int i = 0; i < communityCards.size(); i++){
            deck.add(communityCards.get(0));
            communityCards.remove(0);
        }
        for (int i = 0; i < 2; i++){
            deck.add(p1Cards.get(0));
            deck.add(p2Cards.get(0));
            p1Cards.remove(0);
            p2Cards.remove(0);
        }
        shuffle();
    }

    public int getPlayer(){
        return which;
    }
}
