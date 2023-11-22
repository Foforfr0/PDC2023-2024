/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package eminus5.viewController.responsableProyecto.controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mhken
 */
public class FXMLModificarDesarrolladorController implements Initializable {

    @FXML
    private TextField tfSemestre;
    @FXML
    private TextField tfCorreoPersonal;
    @FXML
    private TextField tfCorreoInstitucional;
    @FXML
    private TextField tfMatricula;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfNombre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicRegresar(MouseEvent event) {
        Stage escenario = (Stage) tfApellidoMaterno.getScene().getWindow();
        escenario.close();
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancelar");
        alert.setHeaderText("¿Estás seguro de cancelar?");
        alert.setContentText("No se podrá revertir la acción");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
    }
    
}
