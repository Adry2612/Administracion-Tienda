/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficoTienda;

import java.sql.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tienda.musica.Conexion;
import tienda.musica.Trabajador;

/**
 * FXML Controller class
 *
 * @author DAW
 */
public class MenuTrabajadoresController implements Initializable {

    @FXML
    private MenuBar menu;
    @FXML
    private TextField tf_id;
    @FXML
    private TextField tf_nombre;
    @FXML
    private TextField tf_apellido1;
    @FXML
    private TextField tf_apellido2;
    @FXML
    private Button but_anadir;
    @FXML
    private Button but_eliminar;
    @FXML
    private Button but_modificar;
    @FXML
    private CheckBox cb_administrador;
    @FXML
    private TableView<Trabajador> tableview_trabajadores;
    @FXML
    private TableColumn<Trabajador, String> col_id;
    @FXML
    private TableColumn<Trabajador, String> col_nombre;
    @FXML
    private TableColumn<Trabajador, String> col_apellido1;
    @FXML
    private TableColumn<Trabajador, String> col_apellido2;
    @FXML
    private TableColumn<Trabajador, String> col_administrador;
    private ObservableList <Trabajador> tvTrabajadores;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvTrabajadores = FXCollections.observableArrayList();
        Trabajador.rellenarTabla(tvTrabajadores);
        tableview_trabajadores.setItems(tvTrabajadores);
        asociarValores();
    }

    public void asociarValores()
    {
        col_id.setCellValueFactory(new PropertyValueFactory <Trabajador, String>("id"));
        col_nombre.setCellValueFactory(new PropertyValueFactory <Trabajador, String>("nombre"));
        col_apellido1.setCellValueFactory(new PropertyValueFactory <Trabajador, String>("apellido1"));
        col_apellido2.setCellValueFactory(new PropertyValueFactory <Trabajador, String>("apellido2"));
        col_administrador.setCellValueFactory(new PropertyValueFactory <Trabajador, String>("administrador"));
        
    }

    @FXML
    private void anadirEmpleado(ActionEvent event) {
        
        tf_id.setText(null);
        tf_nombre.setText(null);
        tf_apellido1.setText(null);
        tf_apellido2.setText(null);
        cb_administrador.setSelected(false);
                
    }

    @FXML
    private void eliminarEmpleado(ActionEvent event) {
    }

    @FXML
    private void modificarEmpleado(ActionEvent event) {
    }
    
    public void botonesInvisibles()
    {
        but_anadir.setVisible(false);
        but_eliminar.setVisible(false);
        but_modificar.setVisible(false);
    }
}
