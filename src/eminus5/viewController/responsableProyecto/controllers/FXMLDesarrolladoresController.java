/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package eminus5.viewController.responsableProyecto.controllers;

import eminus5.databaseManagment.model.POJO.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mhken
 */
public class FXMLDesarrolladoresController implements Initializable {

    private int idResponsable;
    private ObservableList<Usuario>usuario; 
    
    @FXML
    private TableView<Usuario> tvDesarrolladores;
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colMatricula;
    @FXML
    private TableColumn colCorreoInstitucional;
    @FXML
    private TableColumn colCorreoPersonal;
    @FXML
    private TableColumn colSemestre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicRegresar(MouseEvent event) {
        Stage escenario = (Stage) tvDesarrolladores.getScene().getWindow();
        escenario.close();
    }

    @FXML
    private void btnAñadir(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                eminus5.Eminus5.class.getResource("viewController/responsableProyecto/views/FXMLAñadirDesarrollador.fxml"));            
            Parent vista = loader.load();
            Scene escena = new Scene (vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Añadir Desarrollador");
            escenario.showAndWait(); 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnModificar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                eminus5.Eminus5.class.getResource("viewController/responsableProyecto/views/FXMLModificarDesarrollador.fxml"));            
            Parent vista = loader.load();
            Scene escena = new Scene (vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Modificar Desarrollador");
            escenario.showAndWait(); 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
    }

    @FXML
    private void btnBitacoras(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                eminus5.Eminus5.class.getResource("viewController/responsableProyecto/views/FXMLBitacora.fxml"));            
            Parent vista = loader.load();
            Scene escena = new Scene (vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Bitacoras de Desarrollador");
            escenario.showAndWait(); 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
