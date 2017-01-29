package players;

import java.util.Scanner;

import application.Controller;
import items.Chips;

public class Player {
	private String name = "";
	private Chips chips = new Chips();
	protected Controller controller;
	
	public Player(String n, int ch, Controller controller){
		setName(n);
		chips.setQuantity(ch);
		this.controller = controller;
	}

	public Chips getChips() {
		return chips;
	}

	public void setChips(Chips chips) {
		this.chips = chips;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void calculateOptions(int yourBet, int opponentBet, boolean opponentAllIn){
		if(yourBet < opponentBet){
			if(getChips().getQuantity() + yourBet > opponentBet && opponentAllIn == false){
				controller.getBtnFold().setDisable(false);
				controller.getBtnCheckCall().setDisable(false);
				controller.getBtnCheckCall().setText("Call");
				controller.getBtnBetRaise().setDisable(false);
				controller.getBtnBetRaise().setText("Raise");
				controller.getTextFieldBetRaise().setDisable(false);
			}
			else{
				controller.getBtnFold().setDisable(false);
				controller.getBtnCheckCall().setDisable(false);
				controller.getBtnCheckCall().setText("Call");
				controller.getBtnBetRaise().setDisable(true);
				controller.getBtnBetRaise().setText("");
				controller.getTextFieldBetRaise().setDisable(false);
				}
				int min;
				if(yourBet + getChips().getQuantity() < (2 * opponentBet - yourBet))
					min = yourBet + getChips().getQuantity();
				else if(2 * opponentBet - yourBet < 20)
					min = 20;
				else 
					min = 2 * opponentBet - yourBet;
				int max = yourBet + getChips().getQuantity();
				
				controller.setMaxBet(max);
				controller.setMinBet(min);
				controller.getTextFieldBetRaise().setText("" + min);
		}
		else if(yourBet == 0 && opponentBet == 0){
			controller.getBtnFold().setDisable(false);
			controller.getBtnCheckCall().setDisable(false);
			controller.getBtnCheckCall().setText("Check");
			controller.getBtnBetRaise().setDisable(false);
			controller.getBtnBetRaise().setText("Bet");
			controller.getTextFieldBetRaise().setDisable(false);
	
			int min;
			if(getChips().getQuantity() >= 10)
				min = 10;
			else
				min = getChips().getQuantity();
			int max = getChips().getQuantity();
			controller.setMaxBet(max);
			controller.setMinBet(min);
			controller.getTextFieldBetRaise().setText("" + min);
		}
		//------------playerBet i computerBet s¹ sobie równe, ale nie s¹ zerami-------------
		else{		
			controller.getBtnFold().setDisable(false);
			controller.getBtnCheckCall().setDisable(false);
			controller.getBtnCheckCall().setText("Check");
			controller.getBtnBetRaise().setDisable(false);
			controller.getBtnBetRaise().setText("Raise");
			controller.getTextFieldBetRaise().setDisable(false);

			int min;
			if(yourBet + getChips().getQuantity() < (2*opponentBet - yourBet))
				min = yourBet + getChips().getQuantity();
			else if(2 * opponentBet - yourBet < 20)
				min = 20;
			else 
				min = 2 * opponentBet - yourBet;
			int max = yourBet + getChips().getQuantity();
			controller.setMaxBet(max);
			controller.setMinBet(min);
			controller.getTextFieldBetRaise().setText("" + min);
		}
	}
	
	public int decision(int yourBet, int opponentBet, boolean opponentAllIn){
		int choice = controller.getPlayerDecision();
		
		if(yourBet < opponentBet){
			if(choice == -2)
				return fold();
			else if(choice == 0)
				return call(yourBet, opponentBet);
			else if(choice > 0){
				controller.print(getName() + ": raises " + (choice - opponentBet) + " to " + choice);
				if(choice == getChips().getQuantity())
					controller.println(" and is all-in");
				else
					controller.println("");
				return choice;
			}
		}
		else if(yourBet == 0 && opponentBet == 0){
			if(choice == -2)
				return fold();
			else if(choice == -1)
				return check();
			else if(choice > 0){
				controller.print(getName() + ": bets " + choice);
				if(choice == getChips().getQuantity())
					controller.println(" and is all-in");
				else
					controller.println("");
				return choice;
			}
		}
		//------------playerBet i computerBet s¹ sobie równe, ale nie s¹ zerami-------------
		else{		
			if(choice == -2)
				return fold();
			else if(choice == -1)
				return check();
			else if(choice > 0){
				controller.print(getName() + ": raises " + (choice - opponentBet) + " to " + choice);
				if(choice == getChips().getQuantity())
					controller.println(" and is all-in");
				else
					controller.println("");
				return choice;
			}
		}
		return -12;
	}

	protected int fold(){
		controller.println(getName() + ": folds");
		return -2;
	}
	
	protected int check(){
		controller.println(getName() + ": checks");
		return -1;
	}
	
	protected int call(int yourBet, int opponentBet){
		controller.print(getName() + ": calls ");
		if(getChips().getQuantity() + yourBet <= opponentBet)
			controller.println(getChips().getQuantity() + " and is all-in");
		else
			controller.println(opponentBet - yourBet);
		return 0;
	}
}