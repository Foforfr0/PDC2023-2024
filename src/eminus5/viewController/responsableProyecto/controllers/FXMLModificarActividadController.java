/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.responsableProyecto.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class FXMLModificarActividadController implements Initializable {
    public static int idActividad;
    @FXML
    private AnchorPane stageCrearActividad;
    @FXML
    private Pane pnPrincipal;
    @FXML
    private TextField tfNombre;
    @FXML
    private Button btAddActividad;
    @FXML
    private Button btCancelAddActividad;
    @FXML
    private TextArea tfDescripcion;
    @FXML
    private ComboBox<?> cbTipo;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicAddActividad(ActionEvent event) {
    }

    @FXML
    private void clicCancelAdd(ActionEvent event) {
    }
    
}
