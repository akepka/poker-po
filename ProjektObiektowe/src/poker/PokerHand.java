package poker;

public class PokerHand {
	private Card[] cards = new Card[5];	//karty w uk³adzie pokerowym, u³o¿one od najmniejszej do najwiêkszej
	private int rank;	//liczba okreœlaj¹ca, jaki uk³ad pokerowy reprezentuje ta rêka
	private String[] name = {"a high card", "a pair", "two pair", "three of a kind", "a straight", "a flush", "a full house", "four of a kind", "a straight flush"};
	private int kickersToShow = 0;
	
	public PokerHand(int rank, Card[] cards){
		this.rank = rank;
		for(int i = 0; i < 5; i++)
			this.cards[i] = cards[i];
	}

	public Card[] getCards() {
		return cards;
	}

	public int getRank() {
		return rank;
	}
	
	public void setKickersToShow(int kickersToShow){
		this.kickersToShow = kickersToShow;
	}

	public String getName() {
		String kickers = kickersShow(kickersToShow);
		switch(rank){
		case 0:
			kickers = "";
			for(int i = 1; i <= kickersToShow; i++){
				kickers = kickers + " + " + cards[4 - i].getFullValueName();
				if(i == kickersToShow)
					kickers = kickers + " kicker";
			}
			return name[rank] + ", " + cards[4].getFullValueName() + kickers;
		case 1:
			return name[rank] + ", " + cards[0].getFullValueName() + "s" + kickers;
		case 2:
			return name[rank] + ", " + cards[0].getFullValueName() + "s and " + cards[2].getFullValueName() + "s" + kickers;
		case 3:
			return name[rank] + ", " + cards[0].getFullValueName() + "s" + kickers;
		case 4:
			return  name[rank] + ", " + cards[0].getFullValueName() + " to " + cards[4].getFullValueName();
		case 5:
			kickers = "";
			for(int i = 1; i <= kickersToShow; i++)
				kickers = kickers + "-" + cards[4 - i].getFullValueName();
			return name[rank] + ", " + cards[4].getFullValueName() + kickers + " high";
		case 6:
			return name[rank] + ", " + cards[0].getFullValueName() + "s full of " + cards[4].getFullValueName() + "s";
		case 7:
			kickers = "";
			if(kickersToShow == 1)
				kickers = " + " + cards[4].getFullValueName() + " kicker";
			return name[rank] + ", " + cards[0].getFullValueName() + "s" + kickers;
		case 8:
			if(cards[4].getValue() == 1)
				return "a Royal Flush";
			else
				return name[rank] + ", " + cards[0].getFullValueName() + " to " + cards[4].getFullValueName();
		}
		//return null;
		return "";
	}
	
	private String kickersShow(int kickersToShow){
		String kickers = "";
		for(int i = 0; i < kickersToShow; i++){
			kickers = kickers + " + " + cards[4 - i].getFullValueName();
			if(i == kickersToShow - 1)
				kickers = kickers + " kicker";
		}
		return kickers;
	}
	
}
