package showdown;

import items.Card;
import items.Chips;
import players.Computer;
import players.Player;

public class Showdown {
	
	private Player player;
	private Computer computer;
	private Chips pot;
	private Card[] playerCards = new Card[2];
	private Card[] computerCards = new Card[2];
	private Card[] board = new Card[5];
	
	private Card[] allPlayerCards = new Card[7];
	private Card[] allComputerCards = new Card[7];
	
	private PokerHand playerHand;
	private PokerHand computerHand;
	
	public Showdown(Player player, Computer computer, Chips pot, Card[] playerCards, Card[] computerCards, Card[] flop, Card turn, Card river){
		this.player = player;
		this.computer = computer;
		this.pot = pot;
		this.playerCards = playerCards;
		this.computerCards = computerCards;
		board[0] = flop[0];
		board[1] = flop[1];
		board[2] = flop[2];
		board[3] = turn;
		board[4] = river;
	}
	
	public void start(){
		for(int i = 0; i < 2; i++){
			allPlayerCards[i] = playerCards[i];
			allComputerCards[i] = computerCards[i];
		}
		
		for(int i = 2; i < 7; i++){
			allPlayerCards[i] = board[i-2];
			allComputerCards[i] = board[i-2];
		}
		
		Evaluator ev = new Evaluator();
		playerHand = ev.evaluate(allPlayerCards);
		computerHand = ev.evaluate(allComputerCards);
		
		Comparator comp = new Comparator();
		int result = comp.Compare(playerHand, computerHand);
		
		
		System.out.println("*** SHOW DOWN ***");
		System.out.print("Board: [");
		for(int i = 0; i < 5; i++){
			System.out.print(board[i].getName());
			if(i != 4)
				System.out.print(" ");
		}
		System.out.println("]   Pot: " + pot.getQuantity());
		System.out.println(player.getName() + ": shows [" + playerCards[0].getName() + " " + playerCards[1].getName() + "] (" + playerHand.getName() + ")");
		System.out.println(computer.getName() + ": shows [" + computerCards[0].getName() + " " + computerCards[1].getName() + "] (" + computerHand.getName() + ")");
		if(result == 1){
			System.out.println(player.getName() + " wins and collects " + pot.getQuantity() + " from pot");
			player.getChips().changeQuantity(pot.getQuantity());
			pot.setQuantity(0);
		}
		if(result == -1){
			System.out.println(computer.getName() + " wins and collects " + pot.getQuantity() + " from pot");
			computer.getChips().changeQuantity(pot.getQuantity());
			pot.setQuantity(0);
		}
		if(result == 0){
			System.out.println(player.getName() + " and " + computer.getName() + " both win and collect " + pot.getQuantity()/2 + " from pot");
			player.getChips().changeQuantity(pot.getQuantity() / 2);
			computer.getChips().changeQuantity(pot.getQuantity() / 2);
			pot.setQuantity(0);
		}
	}

}
