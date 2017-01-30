package showdown;

public class Comparator {
	
	public Comparator(){
		
	}
	
	public int Compare(PokerHand playerHand, PokerHand computerHand){//Zwraca 1 (gracz wygra³), -1(komputer wygra³) lub 0 (remis)
		
		if(playerHand.getRank() > computerHand.getRank())
			return 1;
		if(computerHand.getRank() > playerHand.getRank())
			return -1;
		//Jeœli nikt jeszcze nie wygra³, to znaczy, ¿e uk³ady s¹ takie same i trzeba porównaæ kolejne karty uk³adu (kickery)
		int[] playerHandValues = new int[5];
		int[] computerHandValues = new int[5];
			
		for(int i = 0; i < 5; i++){
			if(playerHand.getCards()[i].getValue() == 1)
				playerHandValues[i] = 14;
			else
				playerHandValues[i] = playerHand.getCards()[i].getValue();
			
			if(computerHand.getCards()[i].getValue() == 1)
				computerHandValues[i] = 14;
			else
				computerHandValues[i] = computerHand.getCards()[i].getValue();
		}
		
		if(playerHand.getRank() == 0){
			

			for(int i = 4; i >= 0; i--){
				playerHand.setKickersToShow(4 - i);
				computerHand.setKickersToShow(4 - i);
				if(playerHandValues[i] > computerHandValues[i])
					return 1;
				if(computerHandValues[i] > playerHandValues[i])
					return -1;
			}
			return 0;
		}
		if(playerHand.getRank() == 1){
			if(playerHandValues[0] > computerHandValues[0])
				return 1;
			if(computerHandValues[0] > playerHandValues[0])
				return -1;
			
			for(int i = 4; i >= 2; i--){
				playerHand.setKickersToShow(5 - i);
				computerHand.setKickersToShow(5 - i);
				if(playerHandValues[i] > computerHandValues[i])
					return 1;
				if(computerHandValues[i] > playerHandValues[i])
					return -1;
			}
			return 0;
		}
		
		if(playerHand.getRank() == 2){
			if(playerHandValues[0] > computerHandValues[0])
				return 1;
			if(computerHandValues[0] > playerHandValues[0])
				return -1;
			
			if(playerHandValues[2] > computerHandValues[2])
				return 1;
			if(computerHandValues[2] > playerHandValues[2])
				return -1;
			
			
			playerHand.setKickersToShow(1);
			computerHand.setKickersToShow(1);
			if(playerHandValues[4] > computerHandValues[4])
				return 1;
			if(computerHandValues[4] > playerHandValues[4])
				return -1;
			
			return 0;
		}
		
		if(playerHand.getRank() == 3){
			if(playerHandValues[0] > computerHandValues[0])
				return 1;
			if(computerHandValues[0] > playerHandValues[0])
				return -1;
			
			for(int i = 4; i >= 3; i--){
				playerHand.setKickersToShow(5 - i);
				computerHand.setKickersToShow(5 - i);
				if(playerHandValues[i] > computerHandValues[i])
					return 1;
				if(computerHandValues[i] > playerHandValues[i])
					return -1;
			}
			return 0;
		}
		
		if(playerHand.getRank() == 4 || playerHand.getRank() == 8){
			if(playerHandValues[4] > computerHandValues[4])
				return 1;
			if(computerHandValues[4] > playerHandValues[4])
				return -1;
			
			return 0;
		}
		
		if(playerHand.getRank() == 5){
			if(playerHandValues[4] > computerHandValues[4])
				return 1;
			if(computerHandValues[4] > playerHandValues[4])
				return -1;
			
			for(int i = 3; i >= 0; i--){
				playerHand.setKickersToShow(4 - i);
				computerHand.setKickersToShow(4 - i);
				if(playerHandValues[i] > computerHandValues[i])
					return 1;
				if(computerHandValues[i] > playerHandValues[i])
					return -1;
			}
			return 0;
		}
		
		if(playerHand.getRank() == 6){
			if(playerHandValues[0] > computerHandValues[0])
				return 1;
			if(computerHandValues[0] > playerHandValues[0])
				return -1;
			
			if(playerHandValues[3] > computerHandValues[3])
				return 1;
			if(computerHandValues[3] > playerHandValues[3])
				return -1;
			
			return 0;
		}
		
		if(playerHand.getRank() == 7){
			if(playerHandValues[0] > computerHandValues[0])
				return 1;
			if(computerHandValues[0] > playerHandValues[0])
				return -1;
			
			playerHand.setKickersToShow(1);
			computerHand.setKickersToShow(1);
			
			if(playerHandValues[4] > computerHandValues[4])
				return 1;
			if(computerHandValues[4] > playerHandValues[4])
				return -1;
			
			return 0;
		}
		
		return 0;
	}
	
	
}
