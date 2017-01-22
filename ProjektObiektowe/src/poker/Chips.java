package poker;

public class Chips {
	private int quantity;
	
	public Chips() {
		quantity = 0;
	}

	public Chips(int quantity) {
		this.quantity = quantity;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public void setQuantity(int a){
		quantity = a;
	}
	


	public void changeQuantity(int a){
		quantity = quantity + a;
	}
}
