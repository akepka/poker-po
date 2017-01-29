package rounds;

import players.*;
import application.Controller;
import items.*;

public class River extends Turn{

	public River(Player player, Computer computer, boolean dealer, Chips pot, Card card, Controller controller) {
		super(player, computer, dealer, pot, card, controller);
	}

}
