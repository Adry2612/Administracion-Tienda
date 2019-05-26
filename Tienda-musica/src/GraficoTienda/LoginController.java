package GraficoTienda;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tienda.musica.Conexion;

/**
 * Clase controladora del menu de Login del proyecto de Java-FX
 * @author Adrián
 * @version 1.0
 * @since 
 */
public class LoginController implements Initializable {

    @FXML
    private TextField tf_usuario;
    @FXML
    private PasswordField tf_contrasenya;
    @FXML
    private Button but_inicioSesion;

    /**
     * Inicializa la clase controladora del proyecto.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /**
     * Metodo que se conecta a la base de datos y verifica si la contraseña pertenece al empleado introducido.
     * @param event Se ejecutará cuando el usuario hago click sobre el botón de iniciar sesión.
     * @since 02/05/2019
     */
    @FXML
    private void iniciarSesion(ActionEvent event) {
    
        
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
        
        catch (NumberFormatException ex)
        {
            errorContrasena();
        }
        
        catch (SQLException ex2)
        {
            errorConexion();
        }
        
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();  
            }
            
            catch (SQLException ex)
            {
                errorConexion();
            }
        }
    }

    /**
     * Abre la ventana del menu principal de la aplicación. 
     * @since 02/05/2019
     */
    public void abrirVentana()
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
            Stage stage2 = (Stage)but_inicioSesion.getScene().getWindow();
            stage2.close();
        }
        catch(Exception ex)
        {
            ex.getMessage();
        }
    }
    
    /**
     * Metodo que muestra una alerta de error indicando que el usuario o la contraseña son erroneas.
     * @since 02/05/2019
     */
    public void errorContrasena()
    {
        Alert alert = new Alert (Alert.AlertType.ERROR);
        alert.setTitle("INFORMACIÓN");
        alert.setHeaderText(null);
        alert.setContentText("Alguno de los parametros instroducidos no es valido. Vuelve a intentarlo.");
        alert.showAndWait();
    }
    
    /**
    * Metodo que muestra una alerta de información indicando que ha habido un error en la conexión con la base de datos.
    * @since 02/05/2019
    */
    public void errorConexion()
    {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMACIÓN");
        alert.setHeaderText(null);
        alert.setContentText("Parece que ha habido un error de conexión con la base de datos. Intentalo de nuevo más tarde.");
        alert.showAndWait();
    } 

}
