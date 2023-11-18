/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.responsableProyecto.controllers;

import eminus5.databaseManagment.model.DAO.ActividadDAO;
import eminus5.databaseManagment.model.POJO.Actividad;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import static eminus5.utils.loadView.loadScene;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLActividadesProyectoController implements Initializable {
    @FXML
    private TableView<Actividad> tvActividades;
    @FXML
    private TableColumn<Actividad, String> tcNombre;
    @FXML
    private TableColumn<Actividad, String> tvAsignado;
    @FXML
    private TableColumn<Actividad, String> tcEstado;
    @FXML
    private TableColumn<Actividad, String> tcTipo;
    @FXML
    private TableColumn<Actividad, String> tcFechaInicio;
    @FXML
    private TableColumn<Actividad, String> tcFechaFin;
    
    
    public static int idResponsable = 0;
    private ObservableList<Actividad> actividades = FXCollections.observableArrayList();
    private Actividad selectedActividad = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTable();
        initializeData();
    }    
    
    public void initializeTable() {
        this.tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tvAsignado.setCellValueFactory(new PropertyValueFactory("isAsignado"));
        this.tcEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        this.tcTipo.setCellValueFactory(new PropertyValueFactory("tipo"));
        this.tcFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        this.tcFechaFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));
    }
    
    public void initializeData() {
        try{            
            this.actividades = FXCollections.observableArrayList(
                (ObservableList) ActividadDAO.getActividadesProyecto(idResponsable).getData()
            );
            this.tvActividades.setItems(this.actividades);
        }catch(SQLException sqlex){
            showMessageFailureConnection();
            System.out.println("Error de \"SQLException\" en archivo \"FXMLActividadesProyectoController\" en método \"initializeData\"");
            sqlex.printStackTrace();
        }
        
        this.tvActividades.getSortOrder().add(this.tcNombre);
    }

    @FXML
    private void clicAddActividad(MouseEvent event) {
        try {
            Stage stageAddPaciente = new Stage();
            stageAddPaciente.setScene(loadScene("viewController/responsableProyecto/views/FXMLCrearActividad.fxml"));
            stageAddPaciente.setTitle("Crear actividad");
            stageAddPaciente.initModality(Modality.WINDOW_MODAL);
            stageAddPaciente.initOwner(
                (Stage) this.tvActividades.getScene().getWindow()
            );
            stageAddPaciente.initStyle(StageStyle.UTILITY);
            stageAddPaciente.showAndWait();
        } catch (IOException ioex) {
            System.out.println("Error de \"IOException\" en archivo \"FXMLActividadesProyectoController\" en método \"clicAddActividad\"");
            ioex.printStackTrace();
        }
    }

    @FXML
    private void clicModifyActividad(MouseEvent event) {
    }

    @FXML
    private void clicDeleteActividad(MouseEvent event) {
    }
}
