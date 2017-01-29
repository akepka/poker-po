package items;

public class Card {
	private int suit;
	private int value;
	private String[] valuesFullNames = {"Ace","Deuce","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King"};
	private String[] suitsFullNames = {"Spade","Heart","Club","Diamond"};
	private String[] valuesShortNames = {"A","2","3","4","5","6","7","8","9","T","J","Q","K"};
	private String[] suitsShortNames = {"s","h","c","d"};
	
	public Card(int s, int v){
		this.suit = s;
		this.value = v;
	}

	public int getSuit() {
		return suit;
	}

	public int getValue() {
		return value;
	}
	
	public String getName(){
		return valuesShortNames[value-1] + suitsShortNames[suit-1];
	}
	
	public String getFullValueName(){
		return valuesFullNames[value-1];
	}
	
	public String getFullSuitName(){
		return suitsFullNames[suit-1];
	}
}