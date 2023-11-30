package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.POJO.Bitacora;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author abrah
 */
public class FXMLVerBitacoraController implements Initializable {
    

    @FXML
    private AnchorPane stageCrearBitacora;
    @FXML
    private Label lbNombreBitacora;
    @FXML
    private Label lbDetallesBitacora;

    public static Bitacora currentBitacora = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
    }    
    
    private void initializeData(){
        this.lbNombreBitacora.setText(currentBitacora.getNombreCambio());
        this.lbDetallesBitacora.setText(currentBitacora.getDescripcion());
    }
    
}
