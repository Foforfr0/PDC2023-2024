/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.responsableProyecto.controllers;

import eminus5.databaseManagment.model.POJO.Actividad;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class FXMLModificarActividadController implements Initializable {
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
    private ComboBox<String> cbTipo;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    
    
    public static Actividad currentActividad = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
        initializeStage();
    }    
    
    private void initializeData() {
        this.cbTipo.getItems().setAll(
            "Frontend", 
            "Backend", 
            "Controladoes", 
            "Base de datos", 
            "JavaScript"
        );
        
        this.tfNombre.setText(currentActividad.getNombre());
        this.tfDescripcion.setText(currentActividad.getDescripcion());
        this.cbTipo.setValue(currentActividad.getTipo());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate localDate = LocalDate.parse(currentActividad.getFechaInicio(), formatter);
            this.dpFechaInicio.setValue(localDate);
            localDate = LocalDate.parse(currentActividad.getFechaFin(), formatter);
            this.dpFechaFin.setValue(localDate);
        this.cbTipo.setValue(currentActividad.getTipo());
    }
    
    private void initializeStage(){
        stageCrearActividad.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE){
                event.consume();
                closeWindow((Stage) this.tfNombre.getScene().getWindow());
            }
        }); 
    }
    
    @FXML
    private void clicAddActividad(ActionEvent event) {
    }
    
    private void closeWindow(Stage currentStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("¿Está seguro?");
        alert.setHeaderText("¿Está seguro de cancelar?");
        alert.setContentText("¿Ésta acción no se podrá revertir?");


        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                currentStage.close(); 
            }
        });
    }

    @FXML
    private void clicCancelAdd(ActionEvent event) {
        closeWindow((Stage) this.tfNombre.getScene().getWindow());
    }   
}
