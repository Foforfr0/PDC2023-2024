package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.POJO.Actividad;
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
public class FXMLDetallesActividadDController implements Initializable {

    @FXML
    private Label lbTituloActividad;
    @FXML
    private Label lbDescripcionActividad;

    public static Actividad currentActividad = null;
    
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
