package rounds;

import players.*;
import items.*;

public class Turn extends Flop{
	protected Card card;

	public Turn(Player player, Computer computer, boolean dealer, Chips pot, Card card) {
		super(player, computer, dealer, pot);
		this.card = card;
	}

	public Card getCard() {
		return card;
	}
	

}
