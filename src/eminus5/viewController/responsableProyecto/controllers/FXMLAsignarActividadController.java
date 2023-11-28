/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.responsableProyecto.controllers;

import eminus5.databaseManagment.model.DAO.DesarrolladorDAO;
import eminus5.databaseManagment.model.POJO.Desarrollador;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;


public class FXMLAsignarActividadController implements Initializable {
    @FXML
    private Pane pnPrincipal;
    @FXML
    private TableView<Desarrollador> tvDesarrolladores;
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

    
    public static int idProyecto = 0;
    public static int idActividad = 0;
    public static int idDesarrolladorAsignado = 0;
    private ObservableList<Desarrollador> desarrolladores = FXCollections.observableArrayList();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeStage();
        initializeData();
    }        
    
    private void initializeData() {
        try{  
            this.desarrolladores = FXCollections.observableArrayList(
                (ObservableList) DesarrolladorDAO.getDesarrolladoresProyecto(idProyecto).getData()
            );
            this.tvDesarrolladores.setItems(this.desarrolladores);
        }catch(SQLException sqlex){
            showMessageFailureConnection();
            System.err.println("Error de \"SQLException\" en archivo \"FXMLAsignarActividadController\" en método \"initializeData\"");
            sqlex.printStackTrace();
        }
        
        ToggleGroup tgAsignado = new ToggleGroup();
        this.tcAsignado.setCellFactory(col -> new TableCell<Desarrollador, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty == false) {
                    RadioButton radioButton = new RadioButton();
                    radioButton.setToggleGroup(tgAsignado);
                    setGraphic(radioButton);
                }
            }
        });
        
        for (Desarrollador item : tvDesarrolladores.getItems()) {
            if (item.getIdDesarrollador() == idDesarrolladorAsignado) {
                
            }
        }
    }
    
    private void initializeStage() {
        this.tcApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        this.tcApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        this.tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tcMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        this.tcCorreoInstitucional.setCellValueFactory(new PropertyValueFactory("correoInstitucional"));
        this.tcSemestre.setCellValueFactory(new PropertyValueFactory("semestre"));
        
        this.tcNumDesarrollador.setCellFactory(col -> new TableCell<Desarrollador, Integer>() {
            @Override       
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty == false) {
                    setText(Integer.toString(getIndex() + 1));
                }
            }
        });
        /*
        ToggleGroup tgAsignado = new ToggleGroup();
        this.tcAsignado.setCellFactory(col -> new TableCell<Desarrollador, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty == false) {
                    RadioButton radioButton = new RadioButton();
                    radioButton.setToggleGroup(tgAsignado);
                    setGraphic(radioButton);
                }
            }
        });*/
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
