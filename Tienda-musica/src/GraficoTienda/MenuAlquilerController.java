/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficoTienda;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import tienda.musica.*;


/**
 * FXML Controller class
 *
 * @author Adrián
 */
public class MenuAlquilerController implements Initializable {

    @FXML
    private TextField tf_id;
    @FXML
    private DatePicker cb_fecha;
    @FXML
    private ComboBox<?> cb_producto;
    @FXML
    private ComboBox<?> cb_cliente;
    @FXML
    private Button but_eliminar;
    @FXML
    private Button but_modificar;
    @FXML
    private TextField tf_precio;
    @FXML
    private Button but_anadir;
    @FXML
    private Button but_borrar;
    @FXML
    private Button but_vaciar;
    @FXML
    private Button b_volver;
    @FXML
    private Button but_guardar;
    @FXML
    private Button but_actualizar;
    @FXML
    private TableView<?> tv_ventas;
    @FXML
    private TableColumn<?, ?> col_id;
    @FXML
    private TableColumn<?, ?> col_producto;
    @FXML
    private TableColumn<?, ?> col_cliente;
    @FXML
    private TableColumn<?, ?> col_fecha;
    @FXML
    private TableColumn<?, ?> col_precio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void eliminarVenta(ActionEvent event) {
    }

    @FXML
    private void modificarVenta(ActionEvent event) {
    }

    @FXML
    private void nuevoAlquiler(ActionEvent event) {
    }

    @FXML
    private void confirmarEliminar(ActionEvent event) {
    }

    @FXML
    private void vaciarFormulario(ActionEvent event) {
    }

    @FXML
    private void volver(ActionEvent event) {
    }

    @FXML
    private void confirmarGuardar(ActionEvent event) {
    }

    @FXML
    private void confirmarActualizar(ActionEvent event) {
    }
    
    private void rellenarInstrumentos()
    {
        ObservableList <Instrumento> instrumento = FXCollections.observableArrayList();
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try
        {
            stmt = con.prepareStatement("SELECT * FROM Cuerda;");
            rs = stmt.executeQuery();
            
            while (rs.next())                

            {
                Instrumento ins1 = new Cuerda (rs.getInt("Id"), rs.getString("Nombre"), rs.getString("Fabricante"), rs.getDouble("Precio"), rs.getInt("CalibreCuerda"), rs.getString("TipoPuente"));
                instrumento.add(ins1);
            }
            
            stmt = con.prepareStatement ("SELECT * FROM Viento;");
            rs = stmt.executeQuery();
            
            while (rs.next())
            {
                Instrumento ins2 = new Viento (rs.getInt("Id"), rs.getString("Nombre"), rs.getString("Fabricante"), rs.getDouble("Precio"), rs.getString("ModoExcitacion"), rs.getString("TipoBoquilla")); 
                instrumento.add(ins2);
            }
            
            stmt = con.prepareStatement ("SELECT * FROM Percusion;");
            rs = stmt.executeQuery();
            
            while (rs.next())
            {
                Instrumento ins3 = new Percusion (rs.getInt("Id"), rs.getString("Nombre"), rs.getString("Fabricante"), rs.getDouble("Precio"), rs.getString("MaterialMembrana"), rs.getInt("NoPiezas")); 
                instrumento.add(ins3);
            }
            
            cb_producto.setItems(instrumento);
        }
        
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }     
    }
    
    private void rellenarClientes()
    {
        ObservableList <Cliente> cliente = FXCollections.observableArrayList();
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try
        {
            stmt = con.prepareStatement("SELECT * FROM Clientes;");
            rs = stmt.executeQuery();
            
            while (rs.next())
            {
                Cliente c1 = new Cliente (rs.getInt("Id"), rs.getString("Nombre"), rs.getString("Apellido1"), rs.getString("Apellido2"));
                cliente.add(c1);
            }
        }
        
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        } 
        
        cb_cliente.setItems(cliente);
    }
    
}
