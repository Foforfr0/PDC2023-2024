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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author abrah
 */
public class FXMLFormularioCambioController implements Initializable {

    @FXML
    private TextField tfTituloCambio;
    @FXML
    private TextField tfDescCambio;
    @FXML
    private ComboBox<?> cbEstadoCambio;
    @FXML
    private TextField tfTipoCambio;
    @FXML
    private DatePicker dpFechaInicioCambio;
    @FXML
    private DatePicker dpFechaFinCambio;
    @FXML
    private TextField tfEsfuerzo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnCancelarCambio(ActionEvent event) {
    }

    @FXML
    private void btnGuardarCambio(ActionEvent event) {
    }
    
}
