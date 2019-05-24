/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficoTienda;

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
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tienda.musica.Trabajador;

/**
 * FXML Controller class
 *
 * @author Adrián
 */
public class MenuController implements Initializable {

    @FXML
    private Button b_clientes;
    private ObservableList <Trabajador> tvEmpleados;
    @FXML
    private Button b_empleado;
    @FXML
    private Button b_ventas;
    @FXML
    private Button b_instrumentos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
    }    

    @FXML
    private void ventanaClientes(ActionEvent event) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuClientes.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene (root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Clientes");
            stage.show();
        }
        
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void ventanaEmpleados(ActionEvent event) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuTrabajadores.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene (root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Trabajadores");
            stage.show();
            
        }
        
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void ventanaVentas(ActionEvent event) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuVentas.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene (root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Ventas");
            stage.show();
            Stage stage2 = (Stage)b_ventas.getScene().getWindow();
            stage2.close();
        }
        
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void ventanaInstrumentos(ActionEvent event) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuInstrumentos.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene (root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Instrumentos");
            stage.show();
        }
        
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
    
}
