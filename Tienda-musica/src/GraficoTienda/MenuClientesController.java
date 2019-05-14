/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficoTienda;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tienda.musica.Cliente;
import tienda.musica.Conexion;

/**
 * FXML Controller class
 *
 * @author DAW
 */
public class MenuClientesController implements Initializable {

    @FXML
    private TextField text_id;
    @FXML
    private TextField text_nombre;
    @FXML
    private TextField text_apellido1;
    @FXML
    private TextField text_apellido2;
    @FXML
    private TableColumn<Cliente, String> col_id;
    @FXML
    private TableColumn<Cliente, String> col_nombre;
    @FXML
    private TableColumn<Cliente, String> col_apellido1;
    @FXML
    private TableColumn<Cliente, String> col_apellido2;
    @FXML
    private TableColumn<Cliente, String> col_valoracion;
    @FXML
    private TableColumn<Cliente, String> col_descrip;
    @FXML
    private Button b_modificar;
    @FXML
    private Button b_anadir;
    @FXML
    private Button b_borrar;
    private ObservableList <Cliente> tvClientes;
    @FXML
    private TableView<Cliente> tablaClientes;
    
    private final ListChangeListener <Cliente> selectorClientes = new ListChangeListener <Cliente>()
    {
        @Override
        public void onChanged (ListChangeListener.Change<? extends Cliente> c)
        {
            escribirClienteSel();
        }
    };

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        tvClientes = FXCollections.observableArrayList();
        tablaClientes.getSelectionModel().getSelectedItems();
        tvClientes.addListener(selectorClientes);   
        Cliente.rellenarTabla(tvClientes);
        tablaClientes.setItems(tvClientes);
        asociarValores();
        
    }
    
    
            
    public void escribirClienteSel(){
        
        final Cliente cliente 
        
        
    }  
    
    public Cliente obtenerTabla()
    {
       if (tablaClientes != null)
       {
           List <Cliente> tabla = tablaClientes.getSelectionModel().getSelectedItems();
           
           if (tabla.size() == 1)
           {
               final Cliente clienteSel = tabla.get(0);
               return clienteSel;
           }
       }

        return null;
       
    }
            
            

    public void asociarValores()
    {
        col_id.setCellValueFactory(new PropertyValueFactory <Cliente, String>("id"));
        col_nombre.setCellValueFactory(new PropertyValueFactory <Cliente, String>("nombre"));
        col_apellido1.setCellValueFactory(new PropertyValueFactory <Cliente, String>("apellido1"));
        col_apellido2.setCellValueFactory(new PropertyValueFactory <Cliente, String>("apellido2"));
    }

    @FXML
    private void modificar(ActionEvent event) {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        botonesPrinInvisibles();
        
        try
        {
             
        }
        
        catch (Exception ex)
        {
            
        }
    }

    @FXML
    private void anadirCliente(ActionEvent event) {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        botonesPrinInvisibles();
        
        //if trabajador.administrador = true; else alert
        try
        {
            //text_id.setText(max+1);
            text_nombre.setText(null);
            text_apellido1.setText(null);
            text_apellido2.setText(null);
            
            String id = text_id.getText();
            String nombre = text_nombre.getText();
            String apellido1 = text_apellido1.getText();
            String apellido2 = text_apellido2.getText();
            
            stmt = con.prepareStatement("INSERT INTO Clientes (Id, Nombre, Apellido1, Apellido2) VALUES (?, ?, ?, ?");
            stmt.setString(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, apellido1);
            stmt.setString(4, apellido2);
            stmt.executeQuery(); 
        }
        
        catch (Exception ex)
        {
            
        }
    }

    @FXML
    private void eliminarCliente(ActionEvent event) {
    }
    
    public void botonesPrinVisibles()
    {
        b_modificar.setVisible(true);
        b_anadir.setVisible(true);
        b_borrar.setVisible(true);
    }
    
    public void botonesPrinInvisibles()
    {
        b_modificar.setVisible(false);
        b_anadir.setVisible(false);
        b_borrar.setVisible(false);
    }
    
}
