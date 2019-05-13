/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficoTienda;

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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tienda.musica.Conexion;

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
        ResultSet rs = null;
        
        try
        {
            String usuario = tf_usuario.getText();
            String pass = tf_contrasenya.getText();
            
            stmt = con.prepareStatement("SELECT Contrasenya FROM Empleado WHERE nombre = ?");
            stmt.setString (1, usuario);
            stmt.executeQuery();
            rs.next();
            
            if (rs.getString("Contrasenya").equals pass)
            {
                abrirVentana();
                
            }
            
            else
            {
                    
            }
              
        }
        
        catch (Exception ex)
        {
            
        }
        
        
        
    }

    public void abrirVentana()
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        catch(Exception ex)
        {
            ex.getMessage();
        }
}
}
