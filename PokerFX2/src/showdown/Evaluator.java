package showdown;

import items.Card;

public class Evaluator {
	private Card[] allCards;
	private Card[] fiveBestCards = new Card[5];
	private int[] values = new int[15];//Deklarujê 14 miejsc, chocia¿ wartoœci kart jest 13, bo potrzebne s¹ indeksy od 1 do 14 (14 jako dodatkowe miejsce na asa przy sprawdzaniu strita)
	private int[] suits = new int[5];//Podobnie jak wy¿ej, deklarujê 5 miejsc zamiast 4
	
	public Evaluator(){
	}
	
	public PokerHand evaluate(Card[] cards){
		//-----Zerujê tablice z wartoœciami i kolorami-----
		for(int i = 0; i < 15; i++)
			values[i] = 0;
		for(int i = 0; i < 5; i++)
			suits[i] = 0;
		
		//----Wype³niam tablicê ze wsystkimi kartami oraz w tablicach values oraz suits obliczam ile jest kart danego typu
		allCards = new Card[cards.length];
		for(int i = 0; i < allCards.length; i++){
			allCards[i] = cards[i];
			values[allCards[i].getValue()]++;
			suits[allCards[i].getSuit()]++;
			if(allCards[i].getValue() == 1)
				values[14]++;
		}
		
		insertionSort(allCards, allCards.length);
		int maxValues = 0;//iloœæ kart wartoœci, której jest najwiêcej
		int maxSuits = 0;//iloœæ kart koloru, którego jest najwiêcej
		int duplicateValues = 0;//iloœæ kart wartoœci, których jest wiêcej ni¿ jedna
		
		for(int i = 1; i < 14; i++){
			if(values[i] > maxValues)
				maxValues = values[i];
			if(values[i] > 1)
				duplicateValues += values[i];
		}
		
		for(int i = 1; i < 5; i++){
			if(suits[i] > maxSuits)
				maxSuits = suits[i];
		}	
		
		//Sprawdzamy, czy uk³ad jest karet¹ 
		if(maxValues == 4){
			int fourCards = 0;
			int oneCard = 0;
			for(int i = 13; i > 0; i--){
				if(values[i] == 4){
					fourCards = i;
					break;
				}
			}
			for(int i = 13; i > 0; i--){
				if(values[i] < 4 && values[i] > 0){
					oneCard = i;
					break;
				}
			}
			
			for(int i = 0; i < allCards.length; i++){
				if(allCards[i].getValue() == fourCards){
					fiveBestCards[0] = allCards[i];
					fiveBestCards[1] = allCards[i+1];
					fiveBestCards[2] = allCards[i+2];
					fiveBestCards[3] = allCards[i+3];
					break;
				}		
			}
			for(int i = 0; i < allCards.length; i++){
				if(allCards[i].getValue() == oneCard){
					fiveBestCards[4] = allCards[i];
					break;
				}		
			}
	
			return new PokerHand(7, fiveBestCards);
		}
		
		
		//Sprawdzamy, czy uk³ad jest fulem
		if(maxValues == 3 && duplicateValues >= 5){
			int threeCards = 0;
			int twoCards = 0;
			
			if(values[1] == 3){
				threeCards = 1;
				for(int i = 13; i > 0; i--){
					if(values[i] >= 2 && threeCards != i){
						twoCards = i;
						break;
					}	
				}
			}
			else if(values[1] == 2){
				twoCards = 1;
				for(int i = 13; i > 0; i--){
					if(values[i] == 3){
						threeCards = i;
						break;
					}
				}
			}
			else{
				for(int i = 13; i > 0; i--){
					if(values[i] == 3){
						threeCards = i;
						break;
					}
				}
				for(int i = 13; i > 0; i--){
					if(values[i] >= 2 && threeCards != i){
						twoCards = i;
						break;
					}
				}
			}
			
			
			for(int i = 0; i < allCards.length; i++){
				if(allCards[i].getValue() == threeCards){
					fiveBestCards[0] = allCards[i];
					fiveBestCards[1] = allCards[i+1];
					fiveBestCards[2] = allCards[i+2];
					break;
				}		
			}
			for(int i = 0; i < allCards.length; i++){
				if(allCards[i].getValue() == twoCards){
					fiveBestCards[3] = allCards[i];
					fiveBestCards[4] = allCards[i+1];
					break;
				}		
			}

			return new PokerHand(6, fiveBestCards);
		}
		
		//Sprawdzamy, czy karty mo¿na u³o¿yæ w strita
		int straightEnd = 0;
		for(int i = 1; i < 11; i++){
			int counter = 0;
			if(values[i] > 0){
				counter = 1;
				for(int j = i + 1; j < 15 && counter < 5 && counter > 0; j++){
					if(values[j] > 0)
						counter++;
					else
						counter = 0;
					
					if(counter == 5)
						straightEnd = j;
				}
			}
		}
		
		//Sprawdzamy, czy uk³ad jest pokerem
		if(maxSuits >= 5 && straightEnd != 0){
			int suit = 0;
			for(int i = 1; i < 5; i++){
				if(suits[i] == maxSuits)
					suit = i;
			}
			
			int cardsRequired = 0;
			
			for(int k = 0; k < 3; k++){
			
				if(straightEnd == 14){
					for(int i = allCards.length - 1; i >= 0; i--){
						if(allCards[i].getValue() == 1 && allCards[i].getSuit() == suit){
							fiveBestCards[4] = allCards[i];
							cardsRequired++;
							break;
						}		
					}
					
					for(int i = 0; i < 4; i++){
						for(int j = allCards.length - 1; j >= 0; j--){
							if(allCards[j].getValue() == 13 - i && allCards[j].getSuit() == suit){
								fiveBestCards[3 - i] = allCards[j];
								cardsRequired++;
								break;
							}		
						}
					}
				}
				else{
					for(int i = 0; i < 5; i++){
						for(int  j = allCards.length - 1; j >= 0; j--){
							if(allCards[j].getValue() == straightEnd - i && allCards[j].getSuit() == suit){
								fiveBestCards[4 - i] = allCards[j];
								cardsRequired++;
								break;
							}		
						}
					}
				}

				if(cardsRequired == 5)
					return new PokerHand(8, fiveBestCards);
				cardsRequired = 0;
				straightEnd--;
			}
			
			straightEnd += 3;
		}
	
		//Sprawdzamy, czy uk³ad to kolor
		if(maxSuits >= 5){
			int suit = 0;
			for(int i = 1; i < 5; i++){
				if(suits[i] == maxSuits)
					suit = i;
			}
			
			int currentCard = 4;
			for(int  i = allCards.length - 1; i >= 0 && currentCard >= 0; i--){
				if(allCards[i].getSuit() == suit){
					fiveBestCards[currentCard] = allCards[i];	
					currentCard--;
				}
			}
			
			return new PokerHand(5,fiveBestCards);
		}
		
		//Sprawdzamy, czy uk³ad jest stritem
		if(straightEnd != 0){
			if(straightEnd == 14){
				for(int i = allCards.length - 1; i >= 0; i--){
					if(allCards[i].getValue() == 1){
						fiveBestCards[4] = allCards[i];
						break;
					}		
				}
				
				for(int i = 0; i < 4; i++){
					for(int j = allCards.length - 1; j >= 0; j--){
						if(allCards[j].getValue() == 13 - i){
							fiveBestCards[3 - i] = allCards[j];
							break;
						}		
					}
				}
			}
			else{
				for(int i = 0; i < 5; i++){
					for(int  j = allCards.length - 1; j >= 0; j--){
						if(allCards[j].getValue() == straightEnd - i){
							fiveBestCards[4 - i] = allCards[j];
							break;
						}		
					}
				}
			}
			return new PokerHand(4, fiveBestCards);
		}
		
		//Sprawdzamy, czy uk³ad jest trójk¹
		if(maxValues  == 3){
			int threeCards = 0;
			int highCard = 0;
			int secondHighCard = 0;
			for(int i = 13; i > 0; i--){
				if(values[i] == 3){
					threeCards = i;
					break;
				}
			}
			
			if(values[1] == 1)
				highCard = 1;
			else{
				for(int i = 13; i > 0; i--){
					if(values[i] == 1){
						highCard = i;
						break;
					}
				}
			}
			
			for(int i = 13; i > 0; i--){
				if(values[i] == 1 && i != highCard){
					secondHighCard = i;
					break;
				}
			}
			
			for(int i = 0; i < allCards.length; i++){
				if(allCards[i].getValue() == threeCards){
					fiveBestCards[0] = allCards[i];
					fiveBestCards[1] = allCards[i+1];
					fiveBestCards[2] = allCards[i+2];
					break;
				}		
			}
			
			for(int i = 0; i < allCards.length; i++){
				if(allCards[i].getValue() == highCard){
					fiveBestCards[4] = allCards[i];
					break;
				}		
			}
			
			for(int i = 0; i < allCards.length; i++){
				if(allCards[i].getValue() == secondHighCard){
					fiveBestCards[3] = allCards[i];
					break;
				}		
			}
			
			return new PokerHand(3, fiveBestCards);
		}
		
		//Sprawdzamy, czy uk³ad to dwie pary
		if(maxValues == 2 && duplicateValues >= 4){
			int firstPair = 0;
			int secondPair = 0;
			
			if(values[1] == 2)
				firstPair = 1;
			else{
				for(int i = 13; i > 0; i--){
					if(values[i] == 2){
						firstPair = i;
						break;
					}
				}
			}
			for(int i = 13; i > 0; i--){
				if(values[i] == 2 && i != firstPair){
					secondPair = i;
					break;
				}
			}
			System.out.println(firstPair);
			System.out.println(secondPair);
			for(int i = 0; i < allCards.length; i++){
				if(allCards[i].getValue() == firstPair){
					fiveBestCards[0] = allCards[i];
					fiveBestCards[1] = allCards[i+1];
					break;
				}		
			}
			for(int i = 0; i < allCards.length; i++){
				if(allCards[i].getValue() == secondPair){
					fiveBestCards[2] = allCards[i];
					fiveBestCards[3] = allCards[i+1];
					break;
				}		
			}
			
			for(int i = allCards.length - 1; i >= 0; i--){
				if(allCards[i].getValue() != firstPair && allCards[i].getValue() != secondPair){
					fiveBestCards[4] = allCards[i];
					break;
				}		
			}
			
			return new PokerHand(2, fiveBestCards);
		}
		
		//Sprawdzamy, czy uk³ad jest par¹
		if(maxValues == 2){
			int pair = 0;
			for(int i = 13; i > 0; i--){
				if(values[i] == 2){
					pair = i;
					break;
				}
			}
			
			for(int i = 0; i < allCards.length; i++){
				if(allCards[i].getValue() == pair){
					fiveBestCards[0] = allCards[i];
					fiveBestCards[1] = allCards[i+1];
					break;
				}		
			}
			
			int currentCard = 4;
			for(int i = allCards.length - 1; i >= 0 && currentCard > 1; i--){
				if(allCards[i].getValue() != pair){
					fiveBestCards[currentCard] = allCards[i];
					currentCard--;
				}		
			}
			
			return new PokerHand(1, fiveBestCards);
		}
		
		//Jeœli nic z powy¿szych, to uk³ad jest wysok¹ kart¹
		for(int i = 0; i <5; i++)
				fiveBestCards[4 - i] = allCards[allCards.length - 1 - i];
		
		
		return new PokerHand(0,fiveBestCards);
			
	}
	
	private void insertionSort(Card[] A, int n){
		for(int i = 1; i < n; i++){
			Card key = A[i];
			int j = i - 1;
			while(j >=0 && key.getValue() != 1 && (A[j].getValue() > key.getValue() || A[j].getValue() == 1)){
				A[j+1] = A[j];
				j--;
			}
			A[j+1] = key;
		}
	}

}
