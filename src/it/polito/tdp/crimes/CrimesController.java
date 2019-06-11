/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.model.District;
import it.polito.tdp.model.Model;
import it.polito.tdp.model.Neighbor;
import it.polito.tdp.model.Simulatore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CrimesController {

	private Model model;
	private Simulatore sim;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	this.txtResult.clear();
    	this.boxMese.getSelectionModel().clearSelection();
    	this.boxGiorno.getSelectionModel().clearSelection();
    	this.txtN.clear();
    	
    	if(this.boxAnno.getSelectionModel().isEmpty()) {
    		this.txtResult.appendText("Selezionare un anno.");
    		return;
    	}
    	
    	int year = this.boxAnno.getSelectionModel().getSelectedItem();
    	model.createGraph(year);
    	this.txtResult.appendText("Rete Cittadina creata.\n\n");
    	
    	List<District> districts = model.getDistrictsList();
    	for(int i = 0; i < districts.size(); i++) {
			List<Neighbor> neighbors = model.getNeighborsOf(districts.get(i));
			this.txtResult.appendText("Distretti adiacenti al " + districts.get(i) + ":\n");
			for(Neighbor n : neighbors)
				this.txtResult.appendText(n + "\n");
			
			if(i < districts.size()-1)
				this.txtResult.appendText("\n");
		}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	
    	if(model.getDistrictsList().size() != 0) {
    		int anno, mese, giorno, N;
        	
        	if(this.boxAnno.getSelectionModel().isEmpty()) {
        		this.txtResult.appendText("Selezionare un anno.");
        		return;
        	}
        	if(this.boxMese.getSelectionModel().isEmpty()) {
        		this.txtResult.appendText("Selezionare un mese.");
        		return;
        	}
        	if(this.boxGiorno.getSelectionModel().isEmpty()) {
        		this.txtResult.appendText("Selezionare un giorno.");
        		return;
        	}
        	try {
    			if(Integer.parseInt(this.txtN.getText()) < 1 || Integer.parseInt(this.txtN.getText()) > 10) {
    				this.txtResult.appendText("Inserire un numero N compreso tra 1 e 10.");
    				return;
    			}
    		} catch (NumberFormatException e) {
    			this.txtResult.appendText("Inserire un numero N valido.");
    			e.printStackTrace();
    		}
        	
        	anno = boxAnno.getSelectionModel().getSelectedItem();
        	mese = boxMese.getSelectionModel().getSelectedItem();
        	giorno = boxGiorno.getSelectionModel().getSelectedItem();
        	N = Integer.parseInt(txtN.getText());
        	
        	sim.init(N, anno, mese, giorno);
        	sim.run();
        	
        	this.txtResult.appendText("Casi gestiti: " + sim.getNumEventiGestiti() + "\n");
        	this.txtResult.appendText("Casi NON gestiti: " + sim.getNumEventiMalGestiti());
        	
    	} else
    		this.txtResult.appendText("Creare prima la rete cittadina.");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.sim = new Simulatore(model);
    	addItemsToBoxAnno();
    	addItemsToBoxMese();
    }

	private void addItemsToBoxAnno() {
		this.boxAnno.getItems().addAll(model.getAnnoList());
	}
	
	private void addItemsToBoxMese() {
		for(int i = 1; i <= 12; i ++) 
			this.boxMese.getItems().add(i);
	}
	
	@FXML
	void addItemsToBoxGiorno() {
		if(!this.boxMese.getSelectionModel().isEmpty()) {
			this.boxGiorno.getItems().clear();
			int mese = this.boxMese.getSelectionModel().getSelectedItem();
			
			if(mese == 2) {
				int anno = this.boxAnno.getSelectionModel().getSelectedItem();
				if((anno % 4) == 0 || (anno % 400) == 0) {
					for(int i = 1; i <= 29; i ++) 
						this.boxGiorno.getItems().add(i);
				} else {
					for(int i = 1; i <= 28; i ++) 
						this.boxGiorno.getItems().add(i);
				}
			} else if(mese == 1 || mese == 3 || mese == 5 || mese == 7 || mese == 8|| mese == 10 || mese == 12) {
				for(int i = 1; i <= 31; i ++) 
					this.boxGiorno.getItems().add(i);
			} else {
				for(int i = 1; i <= 30; i ++) 
					this.boxGiorno.getItems().add(i);
			}
		}
	}
	
}
