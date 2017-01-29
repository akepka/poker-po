package application;


import game.*;
import items.Deck;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import players.Computer;
import players.Player;

public class Controller {
	
	private Player player = new Player("Andrzej", 1000, this);
	private Computer computer = new Computer("Computer", 1000, this);
	private Controller controller = this;
	
	private Deck deck = new Deck();
	private boolean dealer = false; 	//mówi kto jest aktualnie dealerem (false - komputer, true - gracz)
	private Hand currentHand;
	private boolean playerTurn;
	
	int minBet;
	int maxBet;
	
	private String state;
	
	
	private int i = 1;	//numer rozdania
	
	private int playerDecision =  -12;

	
    @FXML
    private Button btnFold;
    
    @FXML
    private Button btnCheckCall;
    
    @FXML
    private Button btnBetRaise;
    
    @FXML
    private Button btnNextHand;

    @FXML
    private TextArea console;
    
    @FXML
    private TextField textFieldBetRaise;
    
    
    public int getPlayerDecision(){
    	return playerDecision;
    }
    
    public String getState(){
    	return state;
    }
    
    public void setState(String state){
    	this.state = state;
    }
    
    public boolean isPlayerTurn(){
    	return playerTurn;
    }
    
    public void setPlayerTurn(boolean playerTurn){
    	this.playerTurn = playerTurn;
    }
    
    public Button getBtnFold(){
		return btnFold;
    }
    
    public Button getBtnCheckCall(){
  		return btnCheckCall;
      }
    
    public Button getBtnBetRaise(){
  		return btnBetRaise;
      }
    
    public TextField getTextFieldBetRaise(){
  		return textFieldBetRaise;
      }
    
    public void setMinBet(int min){
    	minBet = min;
    }
    
    public void setMaxBet(int max){
    	minBet = max;
    }
    
    public void disableInput(){
    	controller.getBtnFold().setDisable(true);
		controller.getBtnCheckCall().setDisable(true);
		controller.getBtnBetRaise().setDisable(true);
		controller.getTextFieldBetRaise().setDisable(true);
    }
    
    @FXML
    void initialize(){
    	
    	btnNextHand.setOnAction(e -> {
			dealer = !dealer;
			deck.Shuffle();
			println("\nHand #" + i +":");
			currentHand = new Hand(player, computer, deck, dealer, controller);
			i++;
			
			state = "Preflop";
			if(player.getChips().getQuantity() == 0 || computer.getChips().getQuantity() == 0)
				currentHand.allInAfterBlinds();
			else{
				if(dealer == true){
				playerTurn = true;
				currentHand.getPreflop().showPlayersOptions();;
				}
				else{
				playerTurn = false;
				currentHand.start();
				}
			}
    	});
    	
    	btnFold.setOnAction(e -> {
			playerDecision = -2;
			currentHand.start();
    	});
    	
    	btnCheckCall.setOnAction(e -> {
    		if(btnCheckCall.getText().equals("Call"))
    			playerDecision = 0;
    		if(btnCheckCall.getText().equals("Check"))
    			playerDecision = -1;
			currentHand.start();
			
    	});
    	
    	btnBetRaise.setOnAction(e -> {
    		int value = Integer.parseInt(textFieldBetRaise.getText()) + 0;
    		if(value > 0){
    			playerDecision = value;
    		}
    		currentHand.start();
    	});
    	
    	textFieldBetRaise.setOnAction(e -> {
    		int value = Integer.parseInt(textFieldBetRaise.getText()) + 0;
    		if(value > 0){
    			playerDecision = value;
    		}
    		currentHand.start();
    	});
 
    }
    
    public void print(String string){
    	console.appendText(string);
    }
    
    public void print(int i){
    	console.appendText("" + i);
    }
    
    
    public void println(String string){
    	console.appendText(string + "\n");
    }
    
    public void println(int i) {
		console.appendText(i + "\n");
	}
    


}

