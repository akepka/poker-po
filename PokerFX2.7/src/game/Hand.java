package game;

import application.Controller;
import items.*;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import players.*;
import rounds.*;
import showdown.Showdown;

public class Hand {
	private Player player;
	private Computer computer;
	private Deck deck;
	private boolean dealer;
	private Controller controller;
	
	
	private Card playerCards[] = new Card[2];
	private Card computerCards[] = new Card[2];
	
	private Preflop preflop;
	private Flop flop;
	private Turn turn;
	private River river;
	private Showdown showdown;
	
	private Chips pot = new Chips();
	
	public Hand(Player player, Computer computer, Deck deck, boolean dealer, Controller controller){
		this.player = player;
		this.computer = computer;
		this.deck = deck;
		this.dealer = dealer;
		this.controller = controller;
		pot.setController(controller);
		pot.setText(2);
		
		if(dealer == true){
			//---------Wp³acanie blindów-----------
			if(player.getChips().getQuantity() <= 5){
				int remaining = player.getChips().getQuantity();
				controller.println(player.getName() + ": posts small blind " + remaining + " and is all-in");
				controller.println(computer.getName() + ": posts big blind " + 10);
				player.getChips().changeQuantity(-remaining);
				computer.getChips().changeQuantity(-10);
				pot.changeQuantity(10 + remaining);
			}
			else if(computer.getChips().getQuantity() <= 10){
				int remaining = computer.getChips().getQuantity();
				controller.println(player.getName() + ": posts small blind " + 5);
				controller.println(computer.getName() + ": posts big blind " + remaining + " and is all-in");
				player.getChips().changeQuantity(-5);
				computer.getChips().changeQuantity(-remaining);
				pot.changeQuantity(5 + remaining);
			}
			else{
				controller.println(player.getName() + ": posts small blind 5");
				controller.println(computer.getName() + ": posts big blind 10");
				player.getChips().changeQuantity(-5);
				computer.getChips().changeQuantity(-10);
				pot.changeQuantity(15);
			}
		}
		else{
			if(computer.getChips().getQuantity() <= 5){
				int remaining = computer.getChips().getQuantity();
				controller.println(computer.getName() + ": posts small blind " + remaining + " and is all-in");
				controller.println(player.getName() + ": posts big blind " + 10);
				player.getChips().changeQuantity(-10);
				computer.getChips().changeQuantity(-remaining);
				pot.changeQuantity(10 + remaining);
			}
			else if(player.getChips().getQuantity() <= 10){
				int remaining = player.getChips().getQuantity();
				controller.println(computer.getName() + ": posts small blind " + 5);
				controller.println(player.getName() + ": posts big blind " + remaining + " and is all-in");
				player.getChips().changeQuantity(-remaining);
				computer.getChips().changeQuantity(-5);
				pot.changeQuantity(remaining + 5);
			}
			else{
				controller.println(computer.getName() + ": posts small blind 5");
				controller.println(player.getName() + ": posts big blind 10");
				computer.getChips().changeQuantity(-5);
				player.getChips().changeQuantity(-10);
				pot.changeQuantity(15);
			}
		}
		//------------------Rozdawanie kart---------------------
		Card card1 = deck.top();
		Card card2 = deck.top();
		Card card3 = deck.top();
		Card card4 = deck.top();
		
		if(dealer == true){
			setComputerCards(card1, card3);
			setPlayerCards(card2, card4);
		}
		else{	
			setPlayerCards(card1, card3);
			setComputerCards(card2, card4);
		}
		
		preflop = new Preflop(player, computer, dealer, pot, controller);
		preflop.calculateBets();
		flop = new Flop(player, computer, dealer, pot, deck.top(), deck.top(), deck.top(), controller);
		turn = new Turn(player, computer, dealer, pot, deck.top(), controller);
		river = new River(player, computer, dealer, pot, deck.top(), controller);
		showdown = new Showdown(player, computer, pot, getPlayerCards(), getComputerCards(), flop.getCards(), turn.getCard(), river.getCard(), controller);
		
		controller.println("*** HOLE CARDS ***");
		showPlayerCards();
		controller.showPlayerCards(getPlayerCards());
		controller.showComputerCardsHidden();
	}
	
	public Chips getPot(){
		return pot;
	}
	
	public Card[] getPlayerCards(){
		return playerCards;
	}
	
	public void setPlayerCards(Card c1, Card c2){
		playerCards[0] = c1;
		playerCards[1] = c2;
	}
	
	public Card[] getComputerCards(){
		return computerCards;
	}
	
	public void setComputerCards(Card c1, Card c2){
		computerCards[0] = c1;
		computerCards[1] = c2;
	}
	
	public Preflop getPreflop(){
		return preflop;
	}
	
	public Flop getFlop(){
		return flop;
	}
	
	public Turn getTurn(){
		return turn;
	}
	
	public River getRiver(){
		return river;
	}
	
	public void start(){

		//--------------Rozpoczêcie rozdania-----------------
		if(controller.getState().equals("Preflop")){
			if(preflop.start()){
				controller.getPlayerBetText().setText("");
				controller.getComputerBetText().setText("");
				
				controller.showFlop(flop.getCards());
				controller.println("*** FLOP ***");
				controller.print("[");
				showBoard(flop.getCards());
				controller.println("]");
				
				//Jeœli któryœ z graczy wszed³ all-in i zosta³ sprawdzony, 
				//to pomijane s¹ rundy obstawiania i od razu wyœwietlane s¹ karty i dochodzi do showdownu
				if(player.getChips().getQuantity() == 0 || computer.getChips().getQuantity() == 0){
					
					controller.disableInput();
					
					controller.showTurn(turn.getCard());
					controller.println("*** TURN ***");
					controller.print("[");
					showBoard(flop.getCards());
					controller.print("][");
					showBoard(turn.getCard());
					controller.println("]");
					
					controller.showRiver(river.getCard());
					controller.println("*** RIVER ***");
					controller.print("[");
					showBoard(flop.getCards());
					controller.print(" ");
					showBoard(turn.getCard());
					controller.print("][");
					showBoard(river.getCard());
					controller.println("]");
					
					showdown.start();
					return;
				}
				
				controller.setState("Flop");
				if(dealer == true){
					controller.setPlayerTurn(false);
					start();
					flop.showPlayersOptions();
					return;
				}
				else{
					controller.setPlayerTurn(true);
					flop.showPlayersOptions();
					return;
				}
			}
			else{
				if(pot.getQuantity() != 0){
					if(controller.isPlayerTurn() == true){
						controller.setPlayerTurn(false);
						start();
						return;
					}
					else{
						controller.setPlayerTurn(true);
						preflop.showPlayersOptions();
						return;
					}
				}
				return;
			}
		}
		if(controller.getState().equals("Flop")){
			if(flop.start()){
				
				controller.getPlayerBetText().setText("");
				controller.getComputerBetText().setText("");
				
				controller.showTurn(turn.getCard());
				controller.println("*** TURN ***");
				controller.print("[");
				showBoard(flop.getCards());
				controller.print("][");
				showBoard(turn.getCard());
				controller.println("]");
				
				if(player.getChips().getQuantity() == 0 || computer.getChips().getQuantity() == 0){
					
					controller.disableInput();
					
					controller.showRiver(river.getCard());
					controller.println("*** RIVER ***");
					controller.print("[");
					showBoard(flop.getCards());
					controller.print(" ");
					showBoard(turn.getCard());
					controller.print("][");
					showBoard(river.getCard());
					controller.println("]");
					
					showdown.start();
					return;
				}
				
				controller.setState("Turn");
				if(dealer == true){
					controller.setPlayerTurn(false);
					start();
					turn.showPlayersOptions();
					return;
				}
				else{
					controller.setPlayerTurn(true);
					turn.showPlayersOptions();
					return;
				}
				
			}
			else{
				if(pot.getQuantity() != 0){
					if(controller.isPlayerTurn() == true){
						controller.setPlayerTurn(false);
						start();
						return;
					}
					else{
						controller.setPlayerTurn(true);
						flop.showPlayersOptions();
						return;
					}
				}
				return;
			}
		}

		
		if(controller.getState().equals("Turn")){
			if(turn.start()){
				
				controller.getPlayerBetText().setText("");
				controller.getComputerBetText().setText("");
				
				controller.showRiver(river.getCard());
				controller.println("*** RIVER ***");
				controller.print("[");
				showBoard(flop.getCards());
				controller.print(" ");
				showBoard(turn.getCard());
				controller.print("][");
				showBoard(river.getCard());
				controller.println("]");
				
				if(player.getChips().getQuantity() == 0 || computer.getChips().getQuantity() == 0){
					controller.disableInput();
					showdown.start();
					return;
				}
				
				controller.setState("River");
				if(dealer == true){
					controller.setPlayerTurn(false);
					start();
					river.showPlayersOptions();
					return;
				}
				else{
					controller.setPlayerTurn(true);
					river.showPlayersOptions();
					return;
				}
				
			}
			else{
				if(pot.getQuantity() != 0){
					if(controller.isPlayerTurn() == true){
						controller.setPlayerTurn(false);
						start();
						return;
					}
					else{
						controller.setPlayerTurn(true);
						turn.showPlayersOptions();
						return;
					}
				}
				return;
			}
		}
		
		if(controller.getState().equals("River")){		

			if(river.start()){
				
				controller.getPlayerBetText().setText("");
				controller.getComputerBetText().setText("");
						
				showdown.start();
			}
			
			else{
				if(controller.isPlayerTurn() == true){
					if(controller.getPlayerDecision() != -2){
						controller.setPlayerTurn(false);
						start();
					}
					return;
				}
				else{
					controller.setPlayerTurn(true);
					river.showPlayersOptions();
					return;
				}
			}
		}
	}
		
	public void allInAfterBlinds(){	
		controller.disableInput();
		
		controller.showFlop(flop.getCards());
		controller.println("*** FLOP ***");
		controller.print("[");
		showBoard(flop.getCards());
		controller.println("]");
			
		controller.showTurn(turn.getCard());
		controller.println("*** TURN ***");
		controller.print("[");
		showBoard(flop.getCards());
		controller.print("][");
		showBoard(turn.getCard());
		controller.println("]");
			
		controller.showRiver(river.getCard());
		controller.println("*** RIVER ***");
		controller.print("[");
		showBoard(flop.getCards());
		controller.print(" ");
		showBoard(turn.getCard());
		controller.print("][");
		showBoard(river.getCard());
		controller.println("]");
			
		showdown.start();
		
	}
	
	private void showBoard(Card[] cards){
		for(int i = 0; i < cards.length; i++){
			if(i != cards.length - 1)
				controller.print(cards[i].getName() + " ");
			else
				controller.print(cards[i].getName());
		}
	}
	
	private void showBoard(Card card){
		controller.print(card.getName());
	}
	
	private void showPlayerCards(){
		controller.println("Your cards: " + getPlayerCards()[0].getName() + ", " + getPlayerCards()[1].getName());
	}
	
	
	private void sleep(int milliseconds){
		try {
		    Thread.sleep(milliseconds);
		} catch(InterruptedException e) {
		    Thread.currentThread().interrupt();
		}
	}
}
/*PokerStars Zoom Hand #164073854206:  Hold'em No Limit ($0.01/$0.02) - 2017/01/05 16:02:25 CET [2017/01/05 10:02:25 ET]
Table 'McNaught' 9-max Seat #1 is the button
Seat 1: Tiger Chong ($6.93 in chips) 
Seat 2: Waldemar A ($0.62 in chips) 
Seat 3: brikas12 ($0.91 in chips) 
Seat 4: Pelason ($2.05 in chips) 
Seat 5: MyWinCash ($2.46 in chips) 
Seat 6: 86kir86 ($3.08 in chips) 
Seat 7: CzasemWygram ($1.98 in chips) 
Seat 8: WilliamG13 ($2.33 in chips) 
Seat 9: mastino_5 ($2.02 in chips) 
Waldemar A: posts small blind $0.01
brikas12: posts big blind $0.02
*** HOLE CARDS ***
Pelason: folds 
MyWinCash: folds 
86kir86: raises $0.02 to $0.04
CzasemWygram: folds 
WilliamG13: folds 
mastino_5: folds 
Tiger Chong: calls $0.04
Waldemar A: folds 
brikas12: calls $0.02
*** FLOP *** [2s 5h 7s]
brikas12: checks 
86kir86: checks 
Tiger Chong: checks 
*** TURN *** [2s 5h 7s] [Tc]
brikas12: bets $0.07
86kir86: calls $0.07
Tiger Chong: raises $0.07 to $0.14
brikas12: raises $0.10 to $0.24
86kir86: calls $0.17
Tiger Chong: folds 
*** RIVER *** [2s 5h 7s Tc] [Td]
brikas12: bets $0.39
86kir86: raises $0.39 to $0.78
brikas12: calls $0.24 and is all-in
Uncalled bet ($0.15) returned to 86kir86
*** SHOW DOWN ***
86kir86: shows [Ts Qs] (three of a kind, Tens)
brikas12: shows [5c 5s] (a full house, Fives full of Tens)
brikas12 collected $1.94 from pot
*** SUMMARY ***
Total pot $2.01 | Rake $0.07 
Board [2s 5h 7s Tc Td]
Seat 1: Tiger Chong (button) folded on the Turn
Seat 2: Waldemar A (small blind) folded before Flop
Seat 3: brikas12 (big blind) showed [5c 5s] and won ($1.94) with a full house, Fives full of Tens
Seat 4: Pelason folded before Flop (didn't bet)
Seat 5: MyWinCash folded before Flop (didn't bet)
Seat 6: 86kir86 showed [Ts Qs] and lost with three of a kind, Tens
Seat 7: CzasemWygram folded before Flop (didn't bet)
Seat 8: WilliamG13 folded before Flop (didn't bet)
Seat 9: mastino_5 folded before Flop (didn't bet)*/