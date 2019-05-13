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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
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
    private TableColumn<?, ?> col_id;
    @FXML
    private TableColumn<?, ?> col_nombre;
    @FXML
    private TableColumn<?, ?> col_apellido1;
    @FXML
    private TableColumn<?, ?> col_apellido2;
    @FXML
    private TableColumn<?, ?> col_valoracion;
    @FXML
    private TableColumn<?, ?> col_descrip;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try
        {
            stmt = con.prepareStatement("SELECT * FROM Clientes");
            rs = stmt.executeQuery();
            
            while (rs.next())
            {
                
            }
            
        }
        
        catch (Exception ex)
        {
            
        }
    }    

    @FXML
    private void modificar(ActionEvent event) {
    }

    @FXML
    private void anadirCliente(ActionEvent event) {
    }

    @FXML
    private void eliminarCliente(ActionEvent event) {
    }
    
}
