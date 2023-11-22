/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package eminus5.viewController.responsableProyecto.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author mhken
 */
public class FXMLBitacoraController implements Initializable {

    @FXML
    private TableColumn<?, ?> colBitacora;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicRegresar(MouseEvent event) {
        /*Stage escenario = (Stage) colBitacora.getScene().getWindow();
        escenario.close();*/
    }
    
}
