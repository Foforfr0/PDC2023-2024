/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.responsableProyecto.controllers;

import eminus5.databaseManagment.model.DAO.ActividadDAO;
import eminus5.databaseManagment.model.POJO.Actividad;
import eminus5.databaseManagment.model.ResultOperation;
import eminus5.utils.ShowMessage;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class FXMLCrearActividadController implements Initializable {
    @FXML
    private TextField tfNombre;    
    @FXML
    private TextArea tfDescripcion;
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private Button btAddActividad;
    @FXML
    private Button btCancelAddActividad;
    @FXML
    private AnchorPane stageCrearActividad;
    @FXML
    private Pane pnPrincipal;
    
    
    public static int idResponsable = 0;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeStage();
    }    
    
    private void initializeStage() {
        this.cbTipo.getItems().setAll(                      //addOptionsCbTipo();
            "Frontend", 
            "Backend", 
            "Controladoes", 
            "Base de datos", 
            "JavaScript"
        );
        
        this.dpFechaFin.setDisable(true);               //initializaDatePickers();
        this.dpFechaInicio.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now())); // Deshabilitar fechas anteriores a la fecha actual
                if (dpFechaInicio.getValue() == null) {
                    dpFechaFin.setDisable(true);
                } else {
                    dpFechaFin.setDisable(false);
                }
            }
        });
        this.dpFechaFin.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now())); // Deshabilitar fechas anteriores a la fecha actual
                dpFechaFin.setDayCellFactory(picker -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        setDisable(date.isBefore(dpFechaInicio.getValue())); // Deshabilitar fechas anteriores a la del primer DatePicker
                    }
                });
            }
        });
        
        stageCrearActividad.addEventFilter(KeyEvent.KEY_PRESSED, event -> {     //addActionToStage();
            if (event.getCode() == KeyCode.ESCAPE){
                event.consume();
                closeWindow((Stage) this.tfNombre.getScene().getWindow());
            }
        }); 
    }
    
    private boolean validateFullFields() {      //faltanDatos ? return true : return false;
        if (tfNombre.getText().length() <= 0) {
            return true;
        }
        if (tfDescripcion.getText().length() <= 0) {
            return true;
        }
        if (tfNombre.getText().length() <= 0) {
            return true;
        }
        if (cbTipo.getValue() == null) {
            return true;
        }
        if (dpFechaInicio.getValue() == null) {
            return true;
        }
        if (dpFechaFin.getValue() == null) {
            return true;
        }
        return false;
    }
    
    @FXML
    private void clicAddActividad(ActionEvent event) {
        if (validateFullFields() == true) {
            ShowMessage.showMessage(
                "ERROR", 
                "Campos incompletos", 
                "Faltan campos por ingresar", 
                "Favor de ingresar datos faltantes e intente de nuevo"
            );
        } else {
            try {
                Actividad newActividad = new Actividad();
                    newActividad.setNombre(this.tfNombre.getText());
                    newActividad.setDescripcion(this.tfDescripcion.getText());
                    newActividad.setTipo(this.cbTipo.getValue());
                    newActividad.setFechaInicio(this.dpFechaInicio.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    newActividad.setFechaFin(this.dpFechaFin.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                ResultOperation resultCreate = ActividadDAO.createActividad(idResponsable, newActividad);
                if (resultCreate.getIsError() == true) {
                    ShowMessage.showMessage(
                        "ERROR", 
                        "Error inesperado", 
                        resultCreate.getMessage(), 
                        "Intente más tarde"
                    );
                } else {
                    ShowMessage.showMessage(
                            "INFORMATION", 
                            "Se ha creado correctamente", 
                            "Se creó con éxito la actividad", 
                            ""
                    );
                    Stage currentStage = (Stage) this.tfNombre.getScene().getWindow();
                    currentStage.close();
                }
            } catch (SQLException sqlex) {
                System.out.println("\"Error de \"SQLException\" en archivo \"FXMLCrearActividadController\" en método \"createActividad\"");
                sqlex.printStackTrace();
            }
        }
    }
    
    private void closeWindow(Stage currentStage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
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
