/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficoTienda;

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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Adrián
 */
public class MenuInstrumentosController implements Initializable {

    private TextField tf_puente;
    private ComboBox<String> cb_tipo;
    private TextField tf_calibre;
    private TextField tf_boquilla;
    private TextField tf_membrana;
    private TextField tf_excitacion;
    private TextField tf_piezas;
    private TableView<Viento> tableview_viento;
    private TableView<Cuerda> tableview_cuerda;
    private TableView<Percusion> tableview_percusion;
    private TableView<Instrumento> tableview_instrumentos;
    private ObservableList <Instrumento> instrumento;
    private ObservableList <Cuerda> cuerda;
    private ObservableList <Viento> viento;
    private ObservableList <Percusion> percusion;
    @FXML
    private Button b_todos;
    @FXML
    private Button b_cuerda;
    @FXML
    private Button b_percusion;
    @FXML
    private Button b_viento;
   
    
    /**
     * Inicializa la clase controladora.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void ventanaTodos(ActionEvent event) 
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menuTodosInstrumentos.fxml"));
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
            stage.show();
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
            stage.show();
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
            stage.show();
        }
        
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
