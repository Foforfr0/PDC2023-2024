/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package eminus5.viewController.desarrollador.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author abrah
 */
public class FXMLFormularioDefectoController implements Initializable {

    @FXML
    private TextField tfTituloDefecto;
    @FXML
    private TextField tfDescDefecto;
    @FXML
    private TextField tfTipoDefecto;
    @FXML
    private TextField tfEsfuerzo;
    @FXML
    private DatePicker dpFechaReporteDefecto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnCancelarDefecto(ActionEvent event) {
    }

    @FXML
    private void btnGuardarDefecto(ActionEvent event) {
    }
    
}
