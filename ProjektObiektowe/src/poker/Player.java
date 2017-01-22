package poker;

import java.util.Scanner;

public class Player {
	private String name = "";
	private Chips chips = new Chips();
	
	public Player(String n, int ch){
		setName(n);
		chips.setQuantity(ch);
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
	
	public int decision(int yourBet, int opponentBet, boolean opponentAllIn){
		String choice = "";
		@SuppressWarnings("resource")
		Scanner read = new Scanner(System.in);
		
		if(yourBet < opponentBet){
			if(getChips().getQuantity() + yourBet > opponentBet && opponentAllIn == false){
				while(!choice.equals("fold") && !choice.equals("call") && !choice.equals("raise")){
					System.out.println("Choose your play " + getName() + ". Type fold, call or raise:");
					choice = read.nextLine();
				}
			}
			else{
				while(!choice.equals("fold") && !choice.equals("call")){
					System.out.println("Choose your play " + getName() + ". Type fold or call:");
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
					System.out.println("To what amount do you raise? Choose between " + min + " and " + max + "." );
					raise = read.nextInt();
				}
				System.out.print(getName() + ": raises " + (raise - opponentBet) + " to " + raise);
				if(raise == max)
					System.out.println(" and is all-in");
				else
					System.out.println("");
				return raise;
			}
		}
		else if(yourBet == 0 && opponentBet == 0){
			while(!choice.equals("fold") && !choice.equals("check") && !choice.equals("bet")){
				System.out.println("Choose your play " + getName() + ". Type fold, check or bet:");
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
					System.out.println("How big is your bet? Choose between " + min + " and " + max + "." );
					bet = read.nextInt();
				}
				System.out.print(getName() + ": bets " + bet);
				if(bet == max)
					System.out.println(" and is all-in");
				else
					System.out.println("");
				return bet;
			}
		}
		//------------playerBet i computerBet s¹ sobie równe, ale nie s¹ zerami-------------
		else{		
			while(!choice.equals("fold") && !choice.equals("check") && !choice.equals("raise")){
				System.out.println("Choose your play " + getName() + ". Type fold, check or raise:");
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
					System.out.println("To what amount do you raise? Choose between " + min + " and " + max + "." );
					raise = read.nextInt();
				}
				System.out.print(getName() + ": raises " + (raise - opponentBet) + " to " + raise);
				if(raise == max)
					System.out.println(" and is all-in");
				else
					System.out.println("");
				return raise;
			}
		}
		return -12;
	}

	private int fold(){
		System.out.println(getName() + ": folds");
		return -2;
	}
	
	private int check(){
		System.out.println(getName() + ": checks");
		return -1;
	}
	
	private int call(int yourBet, int opponentBet){
		System.out.print(getName() + ": calls ");
		if(getChips().getQuantity() + yourBet <= opponentBet)
			System.out.println(getChips().getQuantity() + " and is all-in");
		else
			System.out.println(opponentBet - yourBet);
		return 0;
	}
}