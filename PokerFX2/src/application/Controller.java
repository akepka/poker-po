package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Controller {

    @FXML
    private Button btnFold;

    @FXML
    private TextArea console;
    
    @FXML
    void initialize(){
    	console.setEditable(false);
    }
    
    public void print(String string){
    	console.appendText(string);
    }
    
    public void println(String string){
    	console.appendText(string + "\n");
    }
    
    public Button getButton(){
    	return btnFold;
    }

}

