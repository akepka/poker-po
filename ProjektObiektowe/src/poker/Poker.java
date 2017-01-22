package poker;

public class Poker {

	public static void main(String[] args) {
		Player player = new Player("Andrzej", 1000);
		Computer computer = new Computer("Computer", 1000);
		
		Game game = new Game(player, computer);
		/*Card[] cards = new Card[7];
		cards[0] = new Card(2,8);
		cards[1] = new Card(1,8);
		cards[2] = new Card(3,1);
		cards[3] = new Card(4,2);
		cards[4] = new Card(3,8);
		cards[5] = new Card(1,4);
		cards[6] = new Card(1,10);
		Evaluator ev = new Evaluator();
		System.out.println(ev.evaluate(cards).getName());*/
		
		game.start();
	}
}
