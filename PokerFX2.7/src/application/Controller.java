package application;


import game.*;
import items.Card;
import items.Deck;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
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
    
    @FXML
    private Slider slider;
    
    @FXML
    private Text potText;

    @FXML
    private Text computerNameText;

    @FXML
    private Text playerNameText;

    @FXML
    private Text computerChipsText;
    
    @FXML
    private Text potString;

    @FXML
    private Text playerChipsText;

    @FXML
    private Text playerBetText;

    @FXML
    private Text computerBetText;
    
    @FXML
    private ImageView flopImageView1;

    @FXML
    private ImageView flopImageView2;

    @FXML
    private ImageView flopImageView3;

    @FXML
    private ImageView turnImageView;

    @FXML
    private ImageView riverImageView;
    
    @FXML
    private ImageView playerCardImageView1;

    @FXML
    private ImageView playerCardImageView2;

    @FXML
    private ImageView computerCardImageView1;

    @FXML
    private ImageView computerCardImageView2;
    
    @FXML
    public Text getPotText(){
    	return potText;
    }
    
    @FXML
    public Text getComputerChipsText(){
    	return computerChipsText;
    }
    
    @FXML    
    public Text getComputerBetText(){
    	return computerBetText;
    }
    
    @FXML
    public Text getPlayerChipsText(){
    	return playerChipsText;
    }
    
    @FXML
    public Text getPlayerBetText(){
    	return playerBetText;
    }
    
 
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
    
    @FXML
    public Button getBtnNextHand(){
		return btnNextHand;
    }
    
    @FXML
    public Button getBtnFold(){
		return btnFold;
    }
    
    @FXML
    public Button getBtnCheckCall(){
  		return btnCheckCall;
      }
    
    @FXML
    public Button getBtnBetRaise(){
  		return btnBetRaise;
      }
    
    @FXML
    public TextField getTextFieldBetRaise(){
  		return textFieldBetRaise;
      }
    
    @FXML
    public Slider getSlider(){
    	return slider;
    }
    
 
    public void setMinBet(int min){
    	minBet = min;
    }
    
    public void setMaxBet(int max){
    	maxBet = max;
    }
    
    public void disableInput(){
    	controller.getBtnFold().setDisable(true);
		controller.getBtnCheckCall().setDisable(true);
		controller.getBtnBetRaise().setDisable(true);
		controller.getTextFieldBetRaise().setDisable(true);
		controller.getSlider().setDisable(true);
    }
    
    @FXML
    void initialize(){
    	
    	playerNameText.setText(player.getName() + ": ");
    	computerNameText.setText(computer.getName() + ": ");
    	
    	btnNextHand.setOnAction(e -> {
    		btnNextHand.setDisable(true);
    		turnOffCards();
    		potText.setText("$0");
			dealer = !dealer;
			deck.Shuffle();
			println("\nHand #" + i +":");
			currentHand = new Hand(player, computer, deck, dealer, controller);
			i++;
			
			state = "Preflop";
			if(player.getChips().getQuantity() == 0 || computer.getChips().getQuantity() == 0 && currentHand.getPot().getQuantity() % 2 == 0)
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
    		String digits = textFieldBetRaise.getText().replaceAll("[^0-9]","");//[^\\.0123456789]
    		int value;
    		if(digits.equals(""))
    			value = 0;
    		else
    			value = Integer.parseInt(digits) + 0;
    		if(value < minBet || value > maxBet){
    			showAlert();
    		}
    		else{
    			playerDecision = value;	
    			currentHand.start();
    		}
    	
    	});
    	
    	textFieldBetRaise.setOnAction(e -> {
    		String digits = textFieldBetRaise.getText().replaceAll("[^0-9]","");
    		int value;
    		if(digits.equals(""))
    			value = 0;
    		else
    			value = Integer.parseInt(digits) + 0;
    		if(value < minBet || value > maxBet){
    			showAlert();
    		}
    		else{
    			playerDecision = value;
    			currentHand.start();
    		}
    	});
    	
    	
    	slider.setOnDragDetected(e -> {
    		int a = (int) slider.getValue();
    		textFieldBetRaise.setText("" + a);
    	});
    	
    	slider.setOnDragDone(e -> {
    		int a = (int) slider.getValue();
    		textFieldBetRaise.setText("" + a);
    	});
    	
    	slider.setOnDragDropped(e -> {
    		int a = (int) slider.getValue();
    		textFieldBetRaise.setText("" + a);
    	});
    	
       	slider.setOnDragEntered(e -> {
    		int a = (int) slider.getValue();
    		textFieldBetRaise.setText("" + a);
    	});
   
    	slider.setOnDragExited(e -> {
    		int a = (int) slider.getValue();
    		textFieldBetRaise.setText("" + a);
    	});
    	
    	
    	slider.setOnDragOver(e -> {
    		int a = (int) slider.getValue();
    		textFieldBetRaise.setText("" + a);
    	});
    	
    	slider.setOnMouseDragEntered(e -> {
    		int a = (int) slider.getValue();
    		textFieldBetRaise.setText("" + a);
    	});
   
    	slider.setOnMouseDragExited(e -> {
    		int a = (int) slider.getValue();
    		textFieldBetRaise.setText("" + a);
    	});
    	
    	slider.setOnMouseDragged(e -> {
    		int a = (int) slider.getValue();
    		textFieldBetRaise.setText("" + a);
    	});
    	
    	slider.setOnMouseDragOver(e -> {
    		int a = (int) slider.getValue();
    		textFieldBetRaise.setText("" + a);
    	});
    	
    	slider.setOnMouseDragReleased(e -> {
    		int a = (int) slider.getValue();
    		textFieldBetRaise.setText("" + a);
    	});
    	
    	slider.setOnMouseClicked(e -> {
    		int a = (int) slider.getValue();
    		textFieldBetRaise.setText("" + a);
    	});
    	
      	slider.setOnMousePressed(e -> {
    		int a = (int) slider.getValue();
    		textFieldBetRaise.setText("" + a);
    	});
      	
    	slider.setOnMouseReleased(e -> {
    		int a = (int) slider.getValue();
    		textFieldBetRaise.setText("" + a);
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
    
    public void showFlop(Card[] cards){
    	flopImageView1.setVisible(true);
    	flopImageView2.setVisible(true);
    	flopImageView3.setVisible(true);
    	
		Image fullImage = new Image("file:../../Image/cards.png");

		// target width and height:
		double scaledWidth = 50;
		double scaledHeight = 70;

		flopImageView1.setImage(fullImage);
		flopImageView2.setImage(fullImage);
		flopImageView3.setImage(fullImage);
		
		int x = (cards[0].getValue() - 1) * 50;
		
		int y = 0;
		if(cards[0].getSuit() == 1)
			y = 210;
		else if(cards[0].getSuit() == 2)
			y = 140;
		else if(cards[0].getSuit() == 3)
			y = 0;
		else if(cards[0].getSuit() == 4)
			y = 70;
		
		// define crop in image coordinates:
		Rectangle2D croppedPortion = new Rectangle2D(x, y, 50, 70);
		
		flopImageView1.setViewport(croppedPortion);
		flopImageView1.setFitWidth(scaledWidth);
		flopImageView1.setFitHeight(scaledHeight);
		flopImageView1.setSmooth(true);
		
		
		 x = (cards[1].getValue() - 1) * 50;
		
		if(cards[1].getSuit() == 1)
			y = 210;
		else if(cards[1].getSuit() == 2)
			y = 140;
		else if(cards[1].getSuit() == 3)
			y = 0;
		else if(cards[1].getSuit() == 4)
			y = 70;
		
		Rectangle2D croppedPortion2 = new Rectangle2D(x, y, 50, 70);
		
		flopImageView2.setViewport(croppedPortion2);
		flopImageView2.setFitWidth(scaledWidth);
		flopImageView2.setFitHeight(scaledHeight);
		flopImageView2.setSmooth(true);
		
		
		 x = (cards[2].getValue() - 1) * 50;
			
		if(cards[2].getSuit() == 1)
			y = 210;
		else if(cards[2].getSuit() == 2)
			y = 140;
		else if(cards[2].getSuit() == 3)
			y = 0;
		else if(cards[2].getSuit() == 4)
			y = 70;
		
		Rectangle2D croppedPortion3 = new Rectangle2D(x, y, 50, 70);
		
		flopImageView3.setViewport(croppedPortion3);
		flopImageView3.setFitWidth(scaledWidth);
		flopImageView3.setFitHeight(scaledHeight);
		flopImageView3.setSmooth(true);
    }
    
    public void showTurn(Card card){
    	turnImageView.setVisible(true);
    	
		Image fullImage = new Image("file:../../Image/cards.png");

		// target width and height:
		double scaledWidth = 50;
		double scaledHeight = 70;

		turnImageView.setImage(fullImage);
		
		int x = (card.getValue() - 1) * 50;
		
		int y = 0;
		if(card.getSuit() == 1)
			y = 210;
		else if(card.getSuit() == 2)
			y = 140;
		else if(card.getSuit() == 3)
			y = 0;
		else if(card.getSuit() == 4)
			y = 70;
		
		// define crop in image coordinates:
		Rectangle2D croppedPortion = new Rectangle2D(x, y, 50, 70);
		
		turnImageView.setViewport(croppedPortion);
		turnImageView.setFitWidth(scaledWidth);
		turnImageView.setFitHeight(scaledHeight);
		turnImageView.setSmooth(true);
    }
    
    public void showRiver(Card card){
    	riverImageView.setVisible(true);
    	
		Image fullImage = new Image("file:../../Image/cards.png");

		//target width and height:
		double scaledWidth = 50;
		double scaledHeight = 70;

		riverImageView.setImage(fullImage);
		
		int x = (card.getValue() - 1) * 50;
		
		int y = 0;
		if(card.getSuit() == 1)
			y = 210;
		else if(card.getSuit() == 2)
			y = 140;
		else if(card.getSuit() == 3)
			y = 0;
		else if(card.getSuit() == 4)
			y = 70;
		
		// define crop in image coordinates:
		Rectangle2D croppedPortion = new Rectangle2D(x, y, 50, 70);
		
		riverImageView.setViewport(croppedPortion);
		riverImageView.setFitWidth(scaledWidth);
		riverImageView.setFitHeight(scaledHeight);
		riverImageView.setSmooth(true);
    }
    
    public void showPlayerCards(Card[] cards){
    	playerCardImageView1.setVisible(true);
    	playerCardImageView2.setVisible(true);
    	
		Image fullImage = new Image("file:../../Image/cards.png");

		// target width and height:
		double scaledWidth = 50;
		double scaledHeight = 70;

		playerCardImageView1.setImage(fullImage);
		playerCardImageView2.setImage(fullImage);
		
		int x = (cards[0].getValue() - 1) * 50;
		
		int y = 0;
		if(cards[0].getSuit() == 1)
			y = 210;
		else if(cards[0].getSuit() == 2)
			y = 140;
		else if(cards[0].getSuit() == 3)
			y = 0;
		else if(cards[0].getSuit() == 4)
			y = 70;
		
		// define crop in image coordinates:
		Rectangle2D croppedPortion = new Rectangle2D(x, y, 50, 70);
		
		playerCardImageView1.setViewport(croppedPortion);
		playerCardImageView1.setFitWidth(scaledWidth);
		playerCardImageView1.setFitHeight(scaledHeight);
		playerCardImageView1.setSmooth(true);
		
		
		 x = (cards[1].getValue() - 1) * 50;
		
		if(cards[1].getSuit() == 1)
			y = 210;
		else if(cards[1].getSuit() == 2)
			y = 140;
		else if(cards[1].getSuit() == 3)
			y = 0;
		else if(cards[1].getSuit() == 4)
			y = 70;
		
		Rectangle2D croppedPortion2 = new Rectangle2D(x, y, 50, 70);
		
		playerCardImageView2.setViewport(croppedPortion2);
		playerCardImageView2.setFitWidth(scaledWidth);
		playerCardImageView2.setFitHeight(scaledHeight);
		playerCardImageView2.setSmooth(true);
    }
    
    public void showComputerCards(Card[] cards){
    	computerCardImageView1.setVisible(true);
    	computerCardImageView2.setVisible(true);
    	
		Image fullImage = new Image("file:../../Image/cards.png");

		// target width and height:
		double scaledWidth = 50;
		double scaledHeight = 70;

		computerCardImageView1.setImage(fullImage);
		computerCardImageView2.setImage(fullImage);
		
		int x = (cards[0].getValue() - 1) * 50;
		
		int y = 0;
		if(cards[0].getSuit() == 1)
			y = 210;
		else if(cards[0].getSuit() == 2)
			y = 140;
		else if(cards[0].getSuit() == 3)
			y = 0;
		else if(cards[0].getSuit() == 4)
			y = 70;
		
		// define crop in image coordinates:
		Rectangle2D croppedPortion = new Rectangle2D(x, y, 50, 70);
		
		computerCardImageView1.setViewport(croppedPortion);
		computerCardImageView1.setFitWidth(scaledWidth);
		computerCardImageView1.setFitHeight(scaledHeight);
		computerCardImageView1.setSmooth(true);
		
		
		 x = (cards[1].getValue() - 1) * 50;
		
		if(cards[1].getSuit() == 1)
			y = 210;
		else if(cards[1].getSuit() == 2)
			y = 140;
		else if(cards[1].getSuit() == 3)
			y = 0;
		else if(cards[1].getSuit() == 4)
			y = 70;
		
		Rectangle2D croppedPortion2 = new Rectangle2D(x, y, 50, 70);
		
		computerCardImageView2.setViewport(croppedPortion2);
		computerCardImageView2.setFitWidth(scaledWidth);
		computerCardImageView2.setFitHeight(scaledHeight);
		computerCardImageView2.setSmooth(true);
    }
    
    public void showComputerCardsHidden(){
    	computerCardImageView1.setVisible(true);
    	computerCardImageView2.setVisible(true);
    	
		Image fullImage = new Image("file:../../Image/back.jpg");

		// target width and height:
		double scaledWidth = 50;
		double scaledHeight = 70;

		computerCardImageView1.setImage(fullImage);
		computerCardImageView2.setImage(fullImage);
		
		
		int y = 0;
		int cardback = 5;// wartoœci od 0 do 5
		
		int x = cardback * 84;
		
		// define crop in image coordinates:
		Rectangle2D croppedPortion = new Rectangle2D(x, y, 84, 118);
		
		computerCardImageView1.setViewport(croppedPortion);
		computerCardImageView1.setFitWidth(scaledWidth);
		computerCardImageView1.setFitHeight(scaledHeight);
		computerCardImageView1.setSmooth(true);
		
		computerCardImageView2.setViewport(croppedPortion);
		computerCardImageView2.setFitWidth(scaledWidth);
		computerCardImageView2.setFitHeight(scaledHeight);
		computerCardImageView2.setSmooth(true);
    }
    
    public void turnOffCards(){
    	flopImageView1.setVisible(false);
    	flopImageView2.setVisible(false);
    	flopImageView3.setVisible(false);
    	turnImageView.setVisible(false);
    	riverImageView.setVisible(false);
    	
    	playerCardImageView1.setVisible(false);
    	playerCardImageView2.setVisible(false);
    	computerCardImageView1.setVisible(false);
    	computerCardImageView2.setVisible(false);
    }
    
    public void showAlert() { 
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error!");
        alert.setHeaderText("Invalid Bet Size");
        alert.setContentText("Please insert a number between " + minBet + " and " + maxBet);
        alert.showAndWait();
    }

}

