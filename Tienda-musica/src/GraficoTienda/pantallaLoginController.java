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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.*;
import tienda.musica.Conexion;

/**
 * FXML Controller class
 *
 * @author DAW
 */
public class pantallaLoginController implements Initializable {

    @FXML
    private TextField tf_usuario;
    @FXML
    private PasswordField tf_contrasenya;
    @FXML
    private Button but_inicioSesion;

    /**
     * Initializes the controller class.
     */
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
            
            stmt = con.prepareStatement("SELECT Password FROM Empleado WHERE Id = ?");
            stmt.setString (1, usuario);
            rs = stmt.executeQuery();
            
            while (rs.next())
            {
                if (rs.getString("Password").equals(pass))
                {
                    abrirVentana();
                }

                else
                {
                    errorContrasena();
                }
            }
              
        }
        
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
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
    
    public void errorContrasena()
    {
        Alert alert = new Alert (AlertType.ERROR);
        alert.setTitle("INFORMACIÓN");
        alert.setHeaderText(null);
        alert.setContentText("La contraseña que ha introducido es erronea. Vuelve a intentarlo.");
        alert.showAndWait();
    }

   
}
