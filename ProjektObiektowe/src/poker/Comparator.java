package poker;

public class Comparator {
	
	public Comparator(){
		
	}
	
	public int Compare(PokerHand playerHand, PokerHand computerHand){//Zwraca 1 (gracz wygra�), -1(komputer wygra�) lub 0 (remis)
		
		if(playerHand.getRank() > computerHand.getRank())
			return 1;
		if(computerHand.getRank() > playerHand.getRank())
			return -1;
		//Je�li nikt jeszcze nie wygra�, to znaczy, �e uk�ady s� takie same i trzeba por�wna� kolejne karty uk�adu (kickery)
		if(playerHand.getRank() == 0){
			
			for(int i = 4; i >= 0; i--){
				playerHand.setKickersToShow(4 - i);
				computerHand.setKickersToShow(4 - i);
				if(playerHand.getCards()[i].getValue() > computerHand.getCards()[i].getValue())
					return 1;
				if(computerHand.getCards()[i].getValue() > playerHand.getCards()[i].getValue())
					return -1;
			}
			return 0;
		}
		if(playerHand.getRank() == 1){
			if(playerHand.getCards()[0].getValue() > computerHand.getCards()[0].getValue())
				return 1;
			if(computerHand.getCards()[0].getValue() > playerHand.getCards()[0].getValue())
				return -1;
			
			for(int i = 4; i >= 2; i--){
				playerHand.setKickersToShow(5 - i);
				computerHand.setKickersToShow(5 - i);
				if(playerHand.getCards()[i].getValue() > computerHand.getCards()[i].getValue())
					return 1;
				if(computerHand.getCards()[i].getValue() > playerHand.getCards()[i].getValue())
					return -1;
			}
			return 0;
		}
		
		if(playerHand.getRank() == 2){
			if(playerHand.getCards()[0].getValue() > computerHand.getCards()[0].getValue())
				return 1;
			if(computerHand.getCards()[0].getValue() > playerHand.getCards()[0].getValue())
				return -1;
			
			if(playerHand.getCards()[2].getValue() > computerHand.getCards()[2].getValue())
				return 1;
			if(computerHand.getCards()[2].getValue() > playerHand.getCards()[2].getValue())
				return -1;
			
			
			playerHand.setKickersToShow(1);
			computerHand.setKickersToShow(1);
			if(playerHand.getCards()[4].getValue() > computerHand.getCards()[4].getValue())
				return 1;
			if(computerHand.getCards()[4].getValue() > playerHand.getCards()[4].getValue())
				return -1;
			
			return 0;
		}
		
		if(playerHand.getRank() == 3){
			if(playerHand.getCards()[0].getValue() > computerHand.getCards()[0].getValue())
				return 1;
			if(computerHand.getCards()[0].getValue() > playerHand.getCards()[0].getValue())
				return -1;
			
			for(int i = 4; i >= 3; i--){
				playerHand.setKickersToShow(5 - i);
				computerHand.setKickersToShow(5 - i);
				if(playerHand.getCards()[i].getValue() > computerHand.getCards()[i].getValue())
					return 1;
				if(computerHand.getCards()[i].getValue() > playerHand.getCards()[i].getValue())
					return -1;
			}
			return 0;
		}
		
		if(playerHand.getRank() == 4 || playerHand.getRank() == 8){
			if(playerHand.getCards()[4].getValue() > computerHand.getCards()[4].getValue())
				return 1;
			if(computerHand.getCards()[4].getValue() > playerHand.getCards()[4].getValue())
				return -1;
			
			return 0;
		}
		
		if(playerHand.getRank() == 5){
			if(playerHand.getCards()[4].getValue() > computerHand.getCards()[4].getValue())
				return 1;
			if(computerHand.getCards()[4].getValue() > playerHand.getCards()[4].getValue())
				return -1;
			
			for(int i = 3; i >= 0; i--){
				playerHand.setKickersToShow(4 - i);
				computerHand.setKickersToShow(4 - i);
				if(playerHand.getCards()[i].getValue() > computerHand.getCards()[i].getValue())
					return 1;
				if(computerHand.getCards()[i].getValue() > playerHand.getCards()[i].getValue())
					return -1;
			}
			return 0;
		}
		
		if(playerHand.getRank() == 6){
			if(playerHand.getCards()[0].getValue() > computerHand.getCards()[0].getValue())
				return 1;
			if(computerHand.getCards()[0].getValue() > playerHand.getCards()[0].getValue())
				return -1;
			
			if(playerHand.getCards()[3].getValue() > computerHand.getCards()[3].getValue())
				return 1;
			if(computerHand.getCards()[3].getValue() > playerHand.getCards()[3].getValue())
				return -1;
			
			return 0;
		}
		
		if(playerHand.getRank() == 7){
			if(playerHand.getCards()[0].getValue() > computerHand.getCards()[0].getValue())
				return 1;
			if(computerHand.getCards()[0].getValue() > playerHand.getCards()[0].getValue())
				return -1;
			
			playerHand.setKickersToShow(1);
			computerHand.setKickersToShow(1);
			
			if(playerHand.getCards()[4].getValue() > computerHand.getCards()[4].getValue())
				return 1;
			if(computerHand.getCards()[4].getValue() > playerHand.getCards()[4].getValue())
				return -1;
			
			return 0;
		}
		
		return 0;
	}
	
	
}
