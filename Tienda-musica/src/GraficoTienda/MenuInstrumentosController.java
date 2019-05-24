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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import tienda.musica.*;
import java.sql.*;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Adrián
 */
public class MenuInstrumentosController implements Initializable {

    @FXML
    private Button b_cuerda;
    @FXML
    private Button b_percusion;
    @FXML
    private Button b_viento;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem mi_menyPrincipal;
    @FXML
    private MenuItem mi_cerrarSesion;
   
    
    /**
     * Inicializa la clase controladora.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void ventanaCuerda(ActionEvent event) 
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuCuerda.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene (root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Instrumentos de cuerda");
            stage.show();
            Stage stage2 = (Stage)b_viento.getScene().getWindow();
            stage2.close();
        }
        
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void ventanaPercusion(ActionEvent event) {
        
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuPercusion.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene (root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Instrumentos de percusión");
            stage.show();
            Stage stage2 = (Stage)b_viento.getScene().getWindow();
            stage2.close();
        }
        
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    
    }

    @FXML
    private void ventanaViento(ActionEvent event) 
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuViento.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene (root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Instrumentos de viento");
            stage.show();
            Stage stage2 = (Stage)b_viento.getScene().getWindow();
            stage2.close();
        }
        
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void volverMenuPrincipal(ActionEvent event) 
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Menu Principal");
            stage.show();
            Stage stage2 = (Stage)b_cuerda.getScene().getWindow();
            stage2.close();

        }
        catch(Exception ex)
        {
            ex.getMessage();
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) 
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Login");
            stage.show();
            Stage stage2 = (Stage)b_cuerda.getScene().getWindow();
            stage2.close();

        }
        catch(IOException ex)
        {
            ex.getMessage();
        }
    }
}
