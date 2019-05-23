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

    private Button b_clientes;
    private ObservableList <Trabajador> tvEmpleados;
    @FXML
    private Button b_todos;
    @FXML
    private Button b_cuerda;
    @FXML
    private Button b_percusion;
    @FXML
    private Button b_viento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
    }    

    private void ventanaClientes(ActionEvent event) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuClientes.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene (root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            Stage stage2 = (Stage)b_clientes.getScene().getWindow();
            stage2.close();
        }
        
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private void ventanaEmpleados(ActionEvent event) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuTrabajadores.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene (root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            Stage stage2 = (Stage)b_clientes.getScene().getWindow();
            stage2.close();
        }
        
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private void ventanaAlquileres(ActionEvent event) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuAlquileres.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene (root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private void ventanaVentas(ActionEvent event) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuVentas.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene (root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private void ventanaInstrumentos(ActionEvent event) {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuInstrumentos.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene (root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
}
