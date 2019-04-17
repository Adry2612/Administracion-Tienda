/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda.musica;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.*;

/**
 *
 * @author Adrian Vidal Lopez
 */
public class pantallaLoginController implements Initializable {

    @FXML
    private TextField tf_usuario;
    @FXML
    private Button but_inicioSesion;
    @FXML
    private PasswordField tf_contrasenya;
    
    private ResultSet rs = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    

    @FXML
    private void iniciarSesion(MouseEvent event) {
        
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        
        try
        {
             
        }
        
        catch (Exception ex)
        {
            
        }
        
        
        
    }
    
}
