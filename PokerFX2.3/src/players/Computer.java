package players;

import java.util.Scanner;

import application.Controller;

public class Computer extends Player {

	public Computer(String n, int ch, Controller controller) {
		super(n, ch, controller);
		// TODO Auto-generated constructor stub
	}
	
	public int decision(int yourBet, int opponentBet, boolean opponentAllIn){
		int choice = opponentBet + 10;
		
		if(opponentBet == 123)
			return fold();
		if(opponentBet == yourBet){
			controller.println(getName() + ": checks");
			return -1;
		}
			
		
		if(opponentBet < 100){
			controller.println(getName() + ": raises " + (choice - opponentBet) + " to " + choice);
			return choice;
		}
			
		
		return call(yourBet, opponentBet);
		
		/*if(yourBet < opponentBet){
			if(getChips().getQuantity() + yourBet > opponentBet && opponentAllIn == false){
				while(!choice.equals("fold") && !choice.equals("call") && !choice.equals("raise")){
					controller.println("Choose your play " + getName() + ". Type fold, call or raise:");
					choice = read.nextLine();
				}
			}
			else{
				while(!choice.equals("fold") && !choice.equals("call")){
					controller.println("Choose your play " + getName() + ". Type fold or call:");
					choice = read.nextLine();
				}
			}
			if(choice.equals("fold"))
				return fold();
			else if(choice.equals("call"))
				return call(yourBet, opponentBet);
			else if(choice.equals("raise")){
				int min;
				if(yourBet + getChips().getQuantity() < (2 * opponentBet - yourBet))
					min = yourBet + getChips().getQuantity();
				else if(2 * opponentBet - yourBet < 20)
					min = 20;
				else 
					min = 2 * opponentBet - yourBet;
				int max = yourBet + getChips().getQuantity();
				int raise = -1; 
				while(raise < min || raise > max ){
					controller.println("To what amount do you raise? Choose between " + min + " and " + max + "." );
					raise = read.nextInt();
				}
				controller.print(getName() + ": raises " + (raise - opponentBet) + " to " + raise);
				if(raise == max)
					controller.println(" and is all-in");
				else
					controller.println("");
				return raise;
			}
		}
		else if(yourBet == 0 && opponentBet == 0){
			while(!choice.equals("fold") && !choice.equals("check") && !choice.equals("bet")){
				controller.println("Choose your play " + getName() + ". Type fold, check or bet:");
				choice = read.nextLine();
			}
			if(choice.equals("fold"))
				return fold();
			else if(choice.equals("check"))
				return check();
			else if(choice.equals("bet")){
				int min;
				if(getChips().getQuantity() >= 10)
					min = 10;
				else
					min = getChips().getQuantity();
				int max = getChips().getQuantity();
				int bet = -1; 
				while(bet < min || bet > max ){
					controller.println("How big is your bet? Choose between " + min + " and " + max + "." );
					bet = read.nextInt();
				}
				controller.print(getName() + ": bets " + bet);
				if(bet == max)
					controller.println(" and is all-in");
				else
					controller.println("");
				return bet;
			}
		}
		//------------playerBet i computerBet s¹ sobie równe, ale nie s¹ zerami-------------
		else{		
			while(!choice.equals("fold") && !choice.equals("check") && !choice.equals("raise")){
				controller.println("Choose your play " + getName() + ". Type fold, check or raise:");
				choice = read.nextLine();
			}
			if(choice.equals("fold"))
				return fold();
			else if(choice.equals("check"))
				return check();
			else if(choice.equals("raise")){
				int min;
				if(yourBet + getChips().getQuantity() < (2*opponentBet - yourBet))
					min = yourBet + getChips().getQuantity();
				else if(2 * opponentBet - yourBet < 20)
					min = 20;
				else 
					min = 2 * opponentBet - yourBet;
				int max = yourBet + getChips().getQuantity();
				int raise = -1; 
				while(raise < min || raise > max ){
					controller.println("To what amount do you raise? Choose between " + min + " and " + max + "." );
					raise = read.nextInt();
				}
				controller.print(getName() + ": raises " + (raise - opponentBet) + " to " + raise);
				if(raise == max)
					controller.println(" and is all-in");
				else
					controller.println("");
				return raise;
			}
		}
		return -12;*/
	}
	
}
