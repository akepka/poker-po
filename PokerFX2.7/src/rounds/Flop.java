package rounds;

import application.Controller;
import items.*;
import players.*;

public class Flop extends Preflop{
	
	private Card[] cards = new Card[3];
	
	public Flop(Player player, Computer computer, boolean dealer, Chips pot, Controller controller){
		super(player, computer, dealer, pot, controller);
		playerBet = 0;
		computerBet = 0;
	}

	public Flop(Player player, Computer computer, boolean dealer, Chips pot, Card c1, Card c2, Card c3, Controller controller) {
		super(player, computer, dealer, pot, controller);
		playerBet = 0;
		computerBet = 0;
		cards[0] = c1;
		cards[1] = c2;
		cards[2] = c3;
	}
	
	public Card[] getCards() {
		return cards;
	}
	

	public boolean start(){
		
		//------------Jeœli komputer rozdawa³-----------------
		if(dealer == false){
			if(controller.isPlayerTurn()){
				//-----------------Decyzja gracza------------------------
				int decision = player.decision(playerBet, computerBet, allIn);
				if(decision > 0){
					player.getChips().changeQuantity(playerBet - decision);
					pot.changeQuantity(decision - playerBet);
					playerBet = decision;
					if(player.getChips().getQuantity() == 0)
						allIn = true;
					
					controller.getPlayerBetText().setText("$" + decision);
				}
				else if(decision == -2){
					playerFolds();
					return false;
				}
				//------Jeœli pierwsza osoba czeka, to nic siê nie dzieje-----
				else if(decision == 0){
					playerCalls();
					return true;
				}
				if((player.getChips().getQuantity() == 0 || computer.getChips().getQuantity() == 0) && playerBet == computerBet)
					return true;
			}
			if(!controller.isPlayerTurn()){
				//----------------------Decyzja komputera-------------
				int decision2 = computer.decision(computerBet, playerBet, allIn);
				if(decision2 > 0){
					computer.getChips().changeQuantity(computerBet - decision2);
					pot.changeQuantity(decision2 - computerBet);
					computerBet = decision2;
					if(computer.getChips().getQuantity() == 0)
						allIn = true;
					
					controller.getComputerBetText().setText("$" + decision2);
				}
				else if(decision2 == -2){
					computerFolds();
					return false;
				}
				else if(decision2 == -1){
					return true;
				}
				else if(decision2 == 0){
					computerCalls();
					return true;
				}
				if((player.getChips().getQuantity() == 0 || computer.getChips().getQuantity() == 0) && playerBet == computerBet)
					return true;
			}
			
		}
		//---------Jeœli gracz rozdawa³-------------------
		else{
			if(!controller.isPlayerTurn()){
				//-------------------Decyzja komputera-------------
				int decision2 = computer.decision(computerBet, playerBet, allIn);
				if(decision2 > 0){
					computer.getChips().changeQuantity(computerBet - decision2);
					pot.changeQuantity(decision2 - computerBet);
					computerBet = decision2;
					if(computer.getChips().getQuantity() == 0)
						allIn = true;
					
					controller.getComputerBetText().setText("$" + decision2);
				}
				else if(decision2 == -2){
					computerFolds();
					return false;
				}
				//------Jeœli pierwsza osoba czeka, to nic siê nie dzieje-----
				else if(decision2 == 0){
					computerCalls();
					return true;
				}
				if((player.getChips().getQuantity() == 0 || computer.getChips().getQuantity() == 0) && playerBet == computerBet)
					return true;
			}
			if(controller.isPlayerTurn()){
				//-----------------Decyzja gracza------------------
				int decision = player.decision(playerBet, computerBet, allIn);
				if(decision > 0){
					player.getChips().changeQuantity(playerBet - decision);
					pot.changeQuantity(decision - playerBet);
					playerBet = decision;
					if(computer.getChips().getQuantity() == 0)
						allIn = true;
					
					controller.getPlayerBetText().setText("$" + decision);
				}
				else if(decision == -2){
					playerFolds();
					return false;
				}
				else if(decision == -1){
					return true;
				}
				else if(decision == 0){
					playerCalls();
					return true;
				}
				if((player.getChips().getQuantity() == 0 || computer.getChips().getQuantity() == 0) && playerBet == computerBet)
					return true;
			}
		}
		return false;
	}


}
