package items;

import java.util.Random;

public class Deck {
	public Card[] deck = new Card[52];
	private int top;
	
	public Deck(){
		for(int i = 0; i < 13; i++){
			deck[i] = new Card(1, i+1);
		}
	
		for(int i = 13; i < 26; i++){
			deck[i] = new Card(2, i-12);
		}
		
		for(int i = 26; i < 39; i++){
			deck[i] = new Card(3, i-25);
		}
		
		for(int i = 39; i < 52; i++){
			deck[i] = new Card(4, i-38);
		}
		
		top = 51;
	}
	
	public Card top(){
		int x = top;
		top--;
		if(x>=0)
			return deck[x];
		else
			return deck[0];
	}
	
	public void Shuffle(){
		top = 51;
		Random generator = new Random();
		int number1;
		int number2;
		Card card;
		for(int i = 0; i < 300; i++){
			number1 = generator.nextInt(52);
			number2 = generator.nextInt(52);
			card = deck[number1];
			deck[number1] = deck[number2];
			deck[number2] = card;
		}
	}
	
}