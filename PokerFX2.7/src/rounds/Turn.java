package rounds;

import players.*;
import application.Controller;
import items.*;

public class Turn extends Flop{
	protected Card card;

	public Turn(Player player, Computer computer, boolean dealer, Chips pot, Card card, Controller controller) {
		super(player, computer, dealer, pot, controller);
		this.card = card;
	}

	public Card getCard() {
		return card;
	}
	

}
