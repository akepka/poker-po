package items;


import application.Controller;


public class Chips {
	private int quantity;
	private int text;
	private Controller controller;
	
	public Chips() {
		quantity = 0;
	}

	public Chips(int quantity) {
		this.quantity = quantity;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public void setController(Controller controller){
		this.controller = controller;
	}
	
	public void setText(int text){
		this.text = text;
	}
	
	public void setQuantity(int a){
		quantity = a;
		if(text == 1)
			controller.getComputerChipsText().setText("$" + quantity);
		else if(text == 2)
			controller.getPotText().setText("$" + quantity);
		else if(text == 3)
			controller.getPlayerChipsText().setText("$" + quantity);
	}

	public void changeQuantity(int a){
		quantity = quantity + a;
		if(text == 1)
			controller.getComputerChipsText().setText("$" + quantity);
			//controller.println("" + quantity);
		else if(text == 2)
			controller.getPotText().setText("$" + quantity);
			//controller.println("" + quantity);
		else if(text == 3)
			controller.getPlayerChipsText().setText("$" + quantity);
			//controller.println("" + quantity);
	}
}
