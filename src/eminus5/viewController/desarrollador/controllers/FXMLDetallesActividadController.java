package eminus5.viewController.desarrollador.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author abrah
 */
public class FXMLDetallesActividadController implements Initializable {

    @FXML
    private Label lbTituloActividad;
    @FXML
    private Label lbDescripcionActividad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnEntregar(ActionEvent event) {
    }

    @FXML
    private void btnCancelarActividad(ActionEvent event) {
    }
    
}
