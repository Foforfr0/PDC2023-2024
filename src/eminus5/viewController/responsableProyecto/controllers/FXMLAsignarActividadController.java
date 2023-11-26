/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.responsableProyecto.controllers;

import eminus5.databaseManagment.model.POJO.Desarrollador;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class FXMLAsignarActividadController implements Initializable {
    @FXML
    private Pane pnPrincipal;
    @FXML
    private TableColumn<Desarrollador, Integer> tcNumDesarrollador;
    @FXML
    private TableColumn<Desarrollador, String> tcApellidoPaterno;
    @FXML
    private TableColumn<Desarrollador, String> tcApellidoMaterno;
    @FXML
    private TableColumn<Desarrollador, String> tcNombre;
    @FXML
    private TableColumn<Desarrollador, String> tcMatricula;
    @FXML
    private TableColumn<Desarrollador, String> tcCorreoInstitucional;
    @FXML
    private TableColumn<Desarrollador, String> tcSemestre;
    @FXML
    private TableColumn<Desarrollador, Boolean> tcAsignado;

    
    public static int idActividad = 0;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeStage();
        initializeData();
    }        
    
    private void initializeData() {
        
    }
    
    private void initializeStage() {
        this.tcApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        this.tcApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        this.tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tcMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        this.tcCorreoInstitucional.setCellValueFactory(new PropertyValueFactory("correoInstitucional"));
        this.tcSemestre.setCellValueFactory(new PropertyValueFactory("semestre"));
        
        tcNumDesarrollador.setCellFactory(col -> new TableCell<Desarrollador, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });
        tcAsignado.setCellFactory(col -> new TableCell<Desarrollador, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    RadioButton radioButton = new RadioButton();
                    // Configurar lógica para seleccionar/deseleccionar RadioButtons
                    setGraphic(radioButton);
                }
            }
        });
    }
    
    @FXML
    private void clicSaveAsignacion(MouseEvent event) {
    }

    @FXML
    private void clicCleanAsignacion(MouseEvent event) {
    }

    @FXML
    private void clicCancelAsignacion(MouseEvent event) {
    }
}
