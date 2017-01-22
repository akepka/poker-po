package poker;

public class Hand {
	private Player player;
	private Computer computer;
	private Deck deck;
	private boolean dealer;
	
	
	private Card playerCards[] = new Card[2];
	private Card computerCards[] = new Card[2];
	
	private Preflop preflop;
	private Flop flop;
	private Turn turn;
	private River river;
	private Showdown showdown;
	
	private Chips pot = new Chips();
	
	public Hand(Player player, Computer computer, Deck deck, boolean dealer){
		this.player = player;
		this.computer = computer;
		this.deck = deck;
		this.dealer = dealer;
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
	
	public void start(){
		//--------------Rozdawanie kart i wp³acanie blindów---------------
		Card card1 = deck.top();
		Card card2 = deck.top();
		Card card3 = deck.top();
		Card card4 = deck.top();
		
		if(dealer == true){
			setComputerCards(card1, card3);
			setPlayerCards(card2, card4);
			
			if(player.getChips().getQuantity() <= 5){
				int remaining = player.getChips().getQuantity();
				System.out.println(player.getName() + ": posts small blind " + remaining + " and is all-in");
				System.out.println(computer.getName() + ": posts big blind " + 10);
				player.getChips().changeQuantity(-remaining);
				computer.getChips().changeQuantity(-10);
				pot.changeQuantity(10 + remaining);
			}
			else if(computer.getChips().getQuantity() <= 10){
				int remaining = computer.getChips().getQuantity();
				System.out.println(player.getName() + ": posts small blind " + 5);
				System.out.println(computer.getName() + ": posts big blind " + remaining + " and is all-in");
				player.getChips().changeQuantity(-5);
				computer.getChips().changeQuantity(-remaining);
				pot.changeQuantity(5 + remaining);
			}
			else{
				System.out.println(player.getName() + ": posts small blind 5");
				System.out.println(computer.getName() + ": posts big blind 10");
				player.getChips().changeQuantity(-5);
				computer.getChips().changeQuantity(-10);
				pot.changeQuantity(15);
			}
		}
		else{	
			setPlayerCards(card1, card3);
			setComputerCards(card2, card4);
			
			if(computer.getChips().getQuantity() <= 5){
				int remaining = computer.getChips().getQuantity();
				System.out.println(computer.getName() + ": posts small blind " + remaining + " and is all-in");
				System.out.println(player.getName() + ": posts big blind " + 10);
				player.getChips().changeQuantity(-10);
				computer.getChips().changeQuantity(-remaining);
				pot.changeQuantity(10 + remaining);
			}
			else if(player.getChips().getQuantity() <= 10){
				int remaining = player.getChips().getQuantity();
				System.out.println(computer.getName() + ": posts small blind " + 5);
				System.out.println(player.getName() + ": posts big blind " + remaining + " and is all-in");
				player.getChips().changeQuantity(-remaining);
				computer.getChips().changeQuantity(-5);
				pot.changeQuantity(remaining + 5);
			}
			else{
				System.out.println(computer.getName() + ": posts small blind 5");
				System.out.println(player.getName() + ": posts big blind 10");
				computer.getChips().changeQuantity(-5);
				player.getChips().changeQuantity(-10);
				pot.changeQuantity(15);
			}
		}
		//--------------Rozpoczêcie rozdania-----------------
		preflop = new Preflop(player, computer, dealer, pot);
		preflop.calculateBets();
		System.out.println("*** HOLE CARDS ***");
		showInfo();
		showPlayerCards();
		if(preflop.start()){
			flop = new Flop(player, computer, dealer, pot, deck.top(), deck.top(), deck.top());
			System.out.println("*** FLOP ***");
			System.out.print("[");
			showBoard(flop.getCards());
			System.out.println("]");
			sleep(1000);
			//Jeœli któryœ z graczy wszed³ all-in i zosta³ sprawdzony, 
			//to pomijane s¹ rundy obstawiania i od razu wyœwietlane s¹ karty i dochodzi do showdownu
			if(player.getChips().getQuantity() == 0 || computer.getChips().getQuantity() == 0){
				turn = new Turn(player, computer, dealer, pot, deck.top());
				System.out.println("*** TURN ***");
				System.out.print("[");
				showBoard(flop.getCards());
				System.out.print("][");
				showBoard(turn.getCard());
				System.out.println("]");
				sleep(1000);
				river = new River(player, computer, dealer, pot, deck.top());
				System.out.println("*** RIVER ***");
				System.out.print("[");
				showBoard(flop.getCards());
				System.out.print(" ");
				showBoard(turn.getCard());
				System.out.print("][");
				showBoard(river.getCard());
				System.out.println("]");
				sleep(1000);
				showdown = new Showdown(player, computer, pot, getPlayerCards(), getComputerCards(), flop.getCards(), turn.getCard(), river.getCard());
				showdown.start();
				return;
			}
			showInfo();
			showPlayerCards();
			if(flop.start()){
				turn = new Turn(player, computer, dealer, pot, deck.top());
				System.out.println("*** TURN ***");
				System.out.print("[");
				showBoard(flop.getCards());
				System.out.print("][");
				showBoard(turn.getCard());
				System.out.println("]");
				sleep(1000);
				if(player.getChips().getQuantity() == 0 || computer.getChips().getQuantity() == 0){
					river = new River(player, computer, dealer, pot, deck.top());
					System.out.println("*** RIVER ***");
					System.out.print("[");
					showBoard(flop.getCards());
					System.out.print(" ");
					showBoard(turn.getCard());
					System.out.print("][");
					showBoard(river.getCard());
					System.out.println("]");
					sleep(1000);
					showdown = new Showdown(player, computer, pot, getPlayerCards(), getComputerCards(), flop.getCards(), turn.getCard(), river.getCard());
					showdown.start();
					return;
				}
				showInfo();
				showPlayerCards();
				if(turn.start()){
					river = new River(player, computer, dealer, pot, deck.top());
					System.out.println("*** RIVER ***");
					System.out.print("[");
					showBoard(flop.getCards());
					System.out.print(" ");
					showBoard(turn.getCard());
					System.out.print("][");
					showBoard(river.getCard());
					System.out.println("]");
					sleep(1000);
					if(player.getChips().getQuantity() == 0 || computer.getChips().getQuantity() == 0){
						showdown = new Showdown(player, computer, pot, getPlayerCards(), getComputerCards(), flop.getCards(), turn.getCard(), river.getCard());
						showdown.start();
						return;
					}
					showInfo();
					showPlayerCards();
					if(river.start()){
						showdown = new Showdown(player, computer, pot, getPlayerCards(), getComputerCards(), flop.getCards(), turn.getCard(), river.getCard());
						showdown.start();
					}
				}
			}
		}
	}
	
	private void showBoard(Card[] cards){
		for(int i = 0; i < cards.length; i++){
			if(i != cards.length - 1)
				System.out.print(cards[i].getName() + " ");
			else
				System.out.print(cards[i].getName());
		}
	}
	
	private void showBoard(Card card){
				System.out.print(card.getName());
	}
	
	private void showPlayerCards(){
		System.out.println("Your cards: " + getPlayerCards()[0].getName() + ", " + getPlayerCards()[1].getName());
	}
	
	private void showInfo(){
		System.out.print("Your chips: " + player.getChips().getQuantity());
		System.out.print("    " + computer.getName() + "'s chips: " + computer.getChips().getQuantity());
		System.out.println("    Pot: " + pot.getQuantity());
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