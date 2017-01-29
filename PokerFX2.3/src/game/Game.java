package game;

import application.Controller;
import items.Deck;
import javafx.application.Platform;
import players.Computer;
import players.Player;

public class Game{
	private Player player;
	private Computer computer;
	private Controller controller;
	
	private Deck deck = new Deck();
	private boolean dealer = false; 	//mówi kto jest aktualnie dealerem (false - komputer, true - gracz)
	
	private int i = 1;	//numer rozdania
	
	public Game(Player p, Computer c, Controller controller){
		player = p;
		computer = c;
		this.controller = controller;
	}
	
	public void start(){
		
		/*deck.Shuffle();
		Card karta;
		for(int i =0; i<52; i++){
				karta = deck.top();
				//System.out.println(karta.getValue());
				//System.out.println(karta.getSuit());
				System.out.println(karta.getName());
		}*/
		while(player.getChips().getQuantity() > 0 && computer.getChips().getQuantity() > 0){
			
			dealer = !dealer;
			deck.Shuffle();
			Hand hand = new Hand(player, computer, deck, dealer, controller);
			//Platform.runLater(() -> controller.println("\nHand #" + i +":"));
			hand.start();
			i++;
			
		}
	}

}