/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.responsableProyecto.controllers;

import eminus5.databaseManagment.model.POJO.Actividad;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class FXMLDetallesActividadController implements Initializable {
    @FXML
    private AnchorPane stageCrearActividad;
    @FXML
    private Pane pnPrincipal;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbDescripcion;
    @FXML
    private Label lbTipo;
    @FXML
    private Label lbFechaInicio;
    @FXML
    private Label lbFechaFin;
    @FXML
    private Button btAceptar;
    @FXML
    private Label lbAsignado;
    
    
    public static Actividad currentActividad = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
        initializeStage();
    }    
    
    private void initializeData(){
        this.lbNombre.setText(currentActividad.getNombre());
        this.lbDescripcion.setText(currentActividad.getDescripcion());
        this.lbTipo.setText(currentActividad.getTipo());
        this.lbFechaInicio.setText(currentActividad.getFechaInicio());
        this.lbFechaFin.setText(currentActividad.getFechaFin());
        
        this.lbAsignado.setText("Asignado");
    }
    
    private void initializeStage() {
        
    }
    
    @FXML
    private void clicAceptar(ActionEvent event) {
    }
}
