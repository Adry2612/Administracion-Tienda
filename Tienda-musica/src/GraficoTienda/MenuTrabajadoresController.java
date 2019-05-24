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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tienda.musica.Cliente;
import tienda.musica.Conexion;
import tienda.musica.Trabajador;

/**
 * FXML Controller class
 *
 * @author DAW
 */
public class MenuTrabajadoresController implements Initializable {

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
    private Button but_actualizar;
    @FXML
    private Button but_confirmarEliminar;
    @FXML
    private Button but_guardar;
    @FXML
    private Button but_vaciar;
    @FXML
    private CheckBox cb_administrador;
    @FXML
    private TableView<Trabajador> tableview_trabajadores;
    @FXML
    private TableColumn<Trabajador, Integer> col_id;
    @FXML
    private TableColumn<Trabajador, String> col_nombre;
    @FXML
    private TableColumn<Trabajador, String> col_apellido1;
    @FXML
    private TableColumn<Trabajador, String> col_apellido2;
    @FXML
    private TableColumn<Trabajador, String> col_administrador;
    private ObservableList <Trabajador> tvTrabajadores;
    
    
    private final ListChangeListener <Trabajador> selectorTrabajador = new ListChangeListener <Trabajador>()
    {
        @Override
        public void onChanged (ListChangeListener.Change<? extends Trabajador> c)
        {
            escribirTrabajadorSel();
        }
    };
    @FXML
    private Button b_volver;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvTrabajadores = FXCollections.observableArrayList();
        Trabajador.rellenarTabla(tvTrabajadores);
        tableview_trabajadores.setItems(tvTrabajadores);
        asociarValores();
        tf_id.setEditable(false);
        
        tvTrabajadores = tableview_trabajadores.getSelectionModel().getSelectedItems();
        tvTrabajadores.addListener(selectorTrabajador);
    }
    
    public Trabajador obtenerTabla()
    {
       if (tableview_trabajadores != null)
       {
           List <Trabajador> tabla = tableview_trabajadores.getSelectionModel().getSelectedItems();
           
           if (tabla.size() == 1)
           {
               final Trabajador trabajadorSel = tabla.get(0);
               return trabajadorSel;
           }
       }

        return null; 
    }
    
    public void escribirTrabajadorSel(){
        
        final Trabajador trabajador = obtenerTabla();
        Integer posicionTrabajador = tvTrabajadores.indexOf(trabajador);
        
        if (trabajador != null)
        {
            String idTrabajador = Integer.toString(trabajador.getId());
            
            tf_id.setText(idTrabajador);
            tf_nombre.setText(trabajador.getNombre());
            tf_apellido1.setText(trabajador.getApellido1());
            tf_apellido2.setText(trabajador.getApellido2());
            cb_administrador.setSelected(trabajador.isAdministrador());
        }   
    } 
   
    public void asociarValores()
    {
        col_id.setCellValueFactory(new PropertyValueFactory <Trabajador, Integer>("id"));
        col_nombre.setCellValueFactory(new PropertyValueFactory <Trabajador, String>("nombre"));
        col_apellido1.setCellValueFactory(new PropertyValueFactory <Trabajador, String>("apellido1"));
        col_apellido2.setCellValueFactory(new PropertyValueFactory <Trabajador, String>("apellido2"));
        col_administrador.setCellValueFactory(new PropertyValueFactory <Trabajador, String>("administrador"));
    }
    
    public void actualizarTableView()
    {
       tvTrabajadores = FXCollections.observableArrayList();
        Trabajador.rellenarTabla(tvTrabajadores);
        tableview_trabajadores.setItems(tvTrabajadores);
        asociarValores();
    }

    //Añadir Empleados
    @FXML
    private void anadirEmpleado(ActionEvent event) {
        
        botonesInvisibles();
        String idMax = Integer.toString(idMaximo());
        tf_id.setEditable(false);
        tf_id.setText(idMax);
        tf_nombre.setText(null);
        tf_apellido1.setText(null);
        tf_apellido2.setText(null);
        cb_administrador.setSelected(false);
        
        but_guardar.setVisible(true);
        but_guardar.setDisable(false);
        but_vaciar.setVisible(true);
        but_vaciar.setDisable(false);
        b_volver.setVisible(true);
        b_volver.setDisable(false);
    }
    
    @FXML
    private void guardarAnadir(ActionEvent event) 
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try
        {
            Integer id = idMaximo();
            String nombre = tf_nombre.getText();
            String apellido1 = tf_apellido1.getText();
            String apellido2 = tf_apellido2.getText();
            String password = "Empleado";
            boolean administrador = cb_administrador.isSelected();
            
            stmt = con.prepareStatement("INSERT INTO Empleado (Id, Nombre, Apellido1, Apellido2, Administrador, Password) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setInt (1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, apellido1);
            stmt.setString(4, apellido2);
            stmt.setBoolean(5, administrador);
            stmt.setString(6, password);
            stmt.executeUpdate();
            
            actualizarTableView();
            alertaInsercionCompletada();
            vaciarFormularioAuto(); 
        }
        
        catch (SQLException ex)
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

    //Eliminar Empleados
    @FXML
    private void eliminarEmpleado(ActionEvent event) 
    {
        botonesInvisibles();
        but_confirmarEliminar.setDisable(false);
        but_confirmarEliminar.setVisible(true);
        but_vaciar.setDisable(false);
        but_vaciar.setVisible(true);
        b_volver.setVisible(true);
        b_volver.setDisable(false);
    }
    
    @FXML
    private void guardarEliminar(ActionEvent event) 
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Integer id = Integer.parseInt(tf_id.getText());
        
        try
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de eliminación de registros.");
            alert.setHeaderText("Esta apunto de eliminar el registro seleccionado.");
            alert.setContentText("¿Desea continuar?");
            Optional <ButtonType> result = alert.showAndWait();

            if ((result.isPresent())&& result.get() == ButtonType.OK)
            {
                System.out.println("Operación realizada.");
                
                stmt = con.prepareStatement("DELETE FROM Empleado WHERE Id = ?");
                stmt.setInt(1, id);
                stmt.executeUpdate();
                actualizarTableView();
                vaciarFormularioAuto(); 
            }

            else
            {
                System.out.println("Operación cancelada.");
            }
        }
        
        catch (SQLException ex)
        {
            errorConexion();
        }
        
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                conexion.desconectar(con);
            }
            
            catch (SQLException ex)
            {
                errorConexion();
            }
        }
    }

    @FXML
    private void modificarEmpleado(ActionEvent event) 
    {
        botonesInvisibles();
        but_actualizar.setDisable(false);
        but_actualizar.setVisible(true);
        but_vaciar.setDisable(false);
        but_vaciar.setVisible(true);
        b_volver.setVisible(true);
        b_volver.setDisable(false);
    }
    
    @FXML
    private void guardarModificar(ActionEvent event) 
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String nombre = tf_nombre.getText();
        String apellido1 = tf_apellido1.getText();
        String apellido2 = tf_apellido2.getText();
        boolean administrador = cb_administrador.isSelected();
        
        try
        {
            stmt = con.prepareStatement("UPDATE Empleado SET Nombre = ?, Apellido1 = ?, Apellido2 = ?, Administrador = ? WHERE Id = ?");
            stmt.setString(1, nombre);
            stmt.setString(2, apellido1);
            stmt.setString(3, apellido2);
            stmt.setBoolean(4, administrador);
            stmt.setInt(5, Integer.parseInt(tf_id.getText()));
            stmt.executeUpdate();
            
            actualizarTableView();
            alertaModificacionCompletada();
            vaciarFormularioAuto();
        }
        
        catch (SQLException ex)
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
    
    //Comprobaciones de botones y tableview.
    public void botonesInvisibles()
    {
        but_anadir.setVisible(false);
        but_eliminar.setVisible(false);
        but_modificar.setVisible(false);
    }
    
    public void botonesVisibles()
    {
        but_anadir.setVisible(true);
        but_eliminar.setVisible(true);
        but_modificar.setVisible(true);
    }
    
    @FXML
    private void vaciarFormulario(ActionEvent event) 
    {
        String idMax = Integer.toString(idMaximo());
        tf_id.setText(idMax);
        tf_nombre.setText(null);
        tf_apellido1.setText(null);
        tf_apellido2.setText(null);
        cb_administrador.setSelected(false);
    }
    
    public Integer idMaximo()
    {
        Integer idMax = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
            
        try
        {           
            stmt = con.prepareStatement ("SELECT MAX(Id) FROM Empleado");
            rs = stmt.executeQuery();
            rs.next();
            
            idMax = rs.getInt(1) +1;
        }
        
        catch (SQLException ex)
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
        
        return idMax;
    }
    
    //Alertas
    public void alertaInsercionCompletada()
    {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("INSERCIÓN COMPLETADA");
        alert.setHeaderText(null);
        alert.setContentText("La inserción a la base de datos se ha realizado correctamente.");
        alert.showAndWait();
    }
    
    public void alertaModificacionCompletada()
    {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("Modificación COMPLETADA");
        alert.setHeaderText(null);
        alert.setContentText("La modificación del empleado se ha realizado correctamente.");
        alert.showAndWait();
    }
    
    public void alertaErrorInsercion()
    {
        Alert alert = new Alert (Alert.AlertType.ERROR);
        alert.setTitle("ERROR AL INSERTAR");
        alert.setHeaderText(null);
        alert.setContentText("Ha ocurrido un error al insertar los datos.");
        alert.showAndWait();
    }
    
    public void confirmacionEliminar()
    {
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de eliminación de registros.");
            alert.setHeaderText("Esta apunto de eliminar el registro seleccionado.");
            alert.setContentText("¿Desea continuar?");
            Optional <ButtonType> result = alert.showAndWait();

            if ((result.isPresent())&& result.get() == ButtonType.OK)
            {
                System.out.println("Eliminación de registro completada.");
            }

            else
            {
                System.out.println("Eliminación cancelada");
            } 
    }
    
    public void alertaValorNecesario()
    {
        Alert alert = new Alert (Alert.AlertType.ERROR);
        alert.setTitle("ERROR AL INSERTAR DATOS.");
        alert.setHeaderText(null);
        alert.setContentText("No se puede insertar un cliente sin nombre o apellido.");
        alert.showAndWait();
    }

    @FXML
    private void volver(ActionEvent event) 
    {
        botonesVisibles();
        but_actualizar.setVisible(false);
        but_actualizar.setDisable(true);
        but_confirmarEliminar.setVisible(false);
        but_confirmarEliminar.setDisable(true);
        but_guardar.setVisible(false);
        but_guardar.setDisable(true);
        b_volver.setDisable(true);
        b_volver.setVisible(false);
        but_vaciar.setVisible(false);
        but_vaciar.setDisable(true);
    }
    
    public void errorConexion()
    {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMACIÓN");
        alert.setHeaderText(null);
        alert.setContentText("Parece que ha habido un error de conexión con la base de datos. Intentalo de nuevo más tarde.");
        alert.showAndWait();
    } 
    
    @FXML
    private void vaciarFormularioAuto() 
    {
        String idMax = Integer.toString(idMaximo());
        tf_id.setText(idMax);
        tf_nombre.setText(null);
        tf_apellido1.setText(null);
        tf_apellido2.setText(null);
        cb_administrador.setSelected(false);
    }
}
