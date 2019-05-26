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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
    private TableColumn<Cliente, Integer> col_id;
    @FXML
    private TableColumn<Cliente, String> col_nombre;
    @FXML
    private TableColumn<Cliente, String> col_apellido1;
    @FXML
    private TableColumn<Cliente, String> col_apellido2;
    @FXML
    private TableColumn<Cliente, String> col_descrip;
    @FXML
    private Button b_modificar;
    @FXML
    private Button b_anadir;
    @FXML
    private Button b_borrar;
    @FXML
    private Button b_vaciar;
    @FXML
    private Button b_guardar;
    @FXML
    private Button b_eliminar;
    @FXML
    private Button b_guardarCambios;
    @FXML
    private Button b_volver;
    @FXML
    private TableView<Cliente> tablaClientes;
    private ObservableList <Cliente> tvClientes;
    
    
    private final ListChangeListener <Cliente> selectorClientes = new ListChangeListener <Cliente>()
    {
        @Override
        public void onChanged (ListChangeListener.Change<? extends Cliente> c)
        {
            escribirClienteSel();
        }
    };

    /**
     * Inicializa la clase controladora.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        tvClientes = FXCollections.observableArrayList(); 
        Cliente.rellenarTabla(tvClientes);
        tablaClientes.setItems(tvClientes);
        asociarValores(); 
        
        tvClientes = tablaClientes.getSelectionModel().getSelectedItems();
        tvClientes.addListener(selectorClientes);
    }
           
    /**
     * Escribe en el TableView los datos de los clientes obtenidos en la base de datos.
     * @since 04/05/2019
     */
    public void escribirClienteSel(){
        
        final Cliente cliente = obtenerTabla();
        Integer posicionCliente = tvClientes.indexOf(cliente);
        
        if (cliente != null)
        {
            String idCliente = Integer.toString(cliente.getId());
            
            text_id.setText(idCliente);
            text_nombre.setText(cliente.getNombre());
            text_apellido1.setText(cliente.getApellido1());
            text_apellido2.setText(cliente.getApellido2());
        }   
    }  
    
    /**
     * Obtiene los valores para escribir en el TableView los datos de los clientes obtenidos en la base de datos.
     * @since 04/05/2019
     */
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
            
    /**
     * Asocia los valores obtenidos de la base de datos a las diferentes columnas del TableView.
     * @since 04/05/2019
     */
    public void asociarValores()
    {
        col_id.setCellValueFactory(new PropertyValueFactory <Cliente, Integer>("id"));
        col_nombre.setCellValueFactory(new PropertyValueFactory <Cliente, String>("nombre"));
        col_apellido1.setCellValueFactory(new PropertyValueFactory <Cliente, String>("apellido1"));
        col_apellido2.setCellValueFactory(new PropertyValueFactory <Cliente, String>("apellido2"));
    }
    
    /**
     * Metodo que vuelve a llamara al metodo rellenarTabla del cliente para realizar una actualización de la pantalla.
     * @since 04/05/2019
     */
    public void actualizarTableView()
    {
        tvClientes = FXCollections.observableArrayList(); 
        Cliente.rellenarTabla(tvClientes);
        tablaClientes.setItems(tvClientes);
        asociarValores();
    }
    
    /**
     * Nos permite modificar aquel cliente seleccionado en el TableView. 
     * Desaparecerán los botones principales y aparecerá el botón de guardar cambios y vaciar formulario.
     * @param event Se ejecutará cuando el usuario hago click sobre el botón de modificar.
     * @since 04/05/2019
     */
    @FXML
    private void modificar(ActionEvent event) 
    {
        botonesPrinInvisibles();
        b_guardarCambios.setVisible(true);
        b_guardarCambios.setDisable(false);
        b_vaciar.setVisible(true);
        b_vaciar.setDisable(false);
        b_volver.setVisible(true);
        b_volver.setDisable(false);
    }
    
    /**
     * Guarda los cambios de aquel cliente seleccionado en el TableView. 
     * Realiza un UPDATE a la base de datos con todos los valores del formulario.
     * Actualizará el TableView para ver los cambios y  vacia formulario.
     * @param event Se ejecutará cuando el usuario hago click sobre el botón de guardar.
     * @since 04/05/2019
     */
    @FXML
    private void guardarCambiosActualizar(ActionEvent event) 
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        text_id.setEditable(false);
        String nombre = text_nombre.getText();
        String apellido1 = text_apellido1.getText();
        String apellido2 = text_apellido2.getText();
        
        try
        {
            stmt = con.prepareStatement("UPDATE Clientes SET Nombre = ?, Apellido1 = ?, Apellido2 = ? WHERE Id = ?");
            stmt.setString(1, nombre);
            stmt.setString(2, apellido1);
            stmt.setString(3, apellido2);
            stmt.setInt(4, Integer.parseInt(text_id.getText()));
            stmt.executeUpdate();
            actualizarTableView();
            vaciarFormularioAuto();
        }
        
        catch (SQLException ex)
        {
            alertaErrorInsercion();
        }
    }
    
    /**
     * Nos permite eliminar aquel cliente seleccionado en el TableView. 
     * Desaparecerán los botones principales y aparecerá el botón de eliminar cambios y vaciar formulario.
     * @param event Se ejecutará cuando el usuario hago click sobre el botón de Eliminar.
     * @since 04/05/2019
     */
    @FXML
    private void eliminarCliente(ActionEvent event) 
    {
        botonesPrinInvisibles();
        b_eliminar.setVisible(true);
        b_eliminar.setDisable(false);
        b_volver.setVisible(true);
        b_volver.setDisable(false);
        b_vaciar.setVisible(true);
        b_vaciar.setDisable(false);
    }
    
    /**
     * Elimina aquel cliente seleccionado en el TableView. 
     * Realiza un DELETE a la base de datos con todos los valores del formulario.
     * Actualizará el TableView para ver los cambios y  vacia formulario.
     * @param event Se ejecutará cuando el usuario hago click sobre el botón de guardar.
     * @since 04/05/2019
     */
    @FXML
    private void guardarCambiosBorrar(ActionEvent event) 
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Integer id = Integer.parseInt(text_id.getText());
        try
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de eliminación de registros.");
            alert.setHeaderText("Esta apunto de eliminar el registro seleccionado.");
            alert.setContentText("¿Desea continuar?");
            Optional <ButtonType> result = alert.showAndWait();

            if ((result.isPresent())&& result.get() == ButtonType.OK)
            {
                System.out.println("Operación realizada.");
                
                stmt = con.prepareStatement("DELETE FROM Clientes WHERE Id = ?");
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
        
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Nos permite añador un cliente con los valores que indicamos en el formulario. 
     * Desaparecerán los botones principales y aparecerá el botón de guardar cambios y vaciar formulario.
     * @param event Se ejecutará cuando el usuario hago click sobre el botón de Añadir.
     * @since 04/05/2019
     */
    @FXML
    private void anadirCliente(ActionEvent event) 
    {
        botonesPrinInvisibles();
        text_id.setEditable(false);
        b_guardar.setVisible(true);
        b_guardar.setDisable(false);
        b_vaciar.setVisible(true);
        b_vaciar.setDisable(false);
        b_volver.setVisible(true);
        b_volver.setDisable(false);
        
        try
        {
            String idMax = Integer.toString(idMaximo());
            text_id.setText(idMax);
            text_nombre.setText(null);
            text_apellido1.setText(null);
            text_apellido2.setText(null);    
            
        }
        
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Añade el cliente asignandole un id automatico. 
     * Realiza un INSERT a la base de datos con todos los valores del formulario.
     * Actualizará el TableView para ver los cambios y vacia formulario.
     * @param event Se ejecutará cuando el usuario hago click sobre el botón de Añadir.
     * @since 04/05/2019
     */
    @FXML
    private void guardarCambiosModificar(ActionEvent event) 
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try
        {
            Integer id = idMaximo();
            String nombre = text_nombre.getText();
            String apellido1 = text_apellido1.getText();
            String apellido2 = text_apellido2.getText();

            stmt = con.prepareStatement("INSERT INTO Clientes (Id, Nombre, Apellido1, Apellido2) VALUES (?, ?, ?, ?)");
            stmt.setInt (1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, apellido1);
            stmt.setString(4, apellido2);
            stmt.executeUpdate();
            
            actualizarTableView();
            alertaInsercionCompletada();
            vaciarFormularioAuto(); 
        }
        
        catch (SQLException ex)
        {
            alertaErrorInsercion();
        }
    }
    
    /**
     * Muestra los botones principales. 
     * @since 04/05/2019
     */
    public void botonesPrinVisibles()
    {
        b_modificar.setVisible(true);
        b_anadir.setVisible(true);
        b_borrar.setVisible(true);
    }
    
    /**
     * Convierte en invisibles los botones principales.
     * @since 04/05/2019
     */
    public void botonesPrinInvisibles()
    {
        b_modificar.setVisible(false);
        b_anadir.setVisible(false);
        b_borrar.setVisible(false);
    }
    
    /**
     * Busca en la base de datos el Id más alto y le suma uno para obtener un id automatico.
     * @return Integer con el valor del Id más alto + 1.
     * @since 04/05/2019
     */
    public Integer idMaximo()
    {
        Integer idMax = null;
        try
        {
            Conexion conexion = new Conexion();
            Connection con = conexion.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = con.prepareStatement ("SELECT MAX(Id) FROM Clientes");
            rs = stmt.executeQuery();
            rs.next();
            
            idMax = rs.getInt(1) +1;
        }
        
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        return idMax;
    }

    /**
     * Deja en blanco los valores escritos en el formulario.
     * @param event Cuando se haga click en el botón vaciar formulario se ejecutará el metodo.
     * @since 04/05/2019
     */
    @FXML
    private void vaciarFormulario(ActionEvent event) 
    {        
        String idMax = Integer.toString(idMaximo());
        text_id.setText(idMax);
        text_nombre.setText(null);
        text_apellido1.setText(null);
        text_apellido2.setText(null);
    }
    
    /**
    * Metodo que muestra una alerta de información indicando que se han insertado los datos correctamente en la base de datos.
    * @since 02/05/2019
    */
    public void alertaInsercionCompletada()
    {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("INSERCIÓN COMPLETADA");
        alert.setHeaderText(null);
        alert.setContentText("La inserción a la base de datos se ha realizado correctamente.");
        alert.showAndWait();
    }
    
    /**
    * Metodo que muestra una alerta de información indicando que ha habido un error en al insertar datos en la base de datos.
    * @since 02/05/2019
    */
    public void alertaErrorInsercion()
    {
        Alert alert = new Alert (Alert.AlertType.ERROR);
        alert.setTitle("ERROR AL INSERTAR");
        alert.setHeaderText(null);
        alert.setContentText("Ha ocurrido un error al insertar los datos.");
        alert.showAndWait();
    }
    
    /**
     * Muestra los botones principales y desabilita y convierte en invisible los demás botones.
     * @since 04/05/2019
     */
    @FXML
    private void volver(ActionEvent event) {
        botonesPrinVisibles();
        b_guardar.setVisible(false);
        b_guardar.setDisable(true);
        b_vaciar.setVisible(false);
        b_vaciar.setDisable(true);
        b_eliminar.setVisible(false);
        b_eliminar.setDisable(true);
        b_volver.setDisable(true);
        b_volver.setVisible(false);
        b_guardarCambios.setDisable(true);
        b_guardarCambios.setVisible(false);
    }
    
    private void vaciarFormularioAuto() 
    {        
        String idMax = Integer.toString(idMaximo());
        text_id.setText(idMax);
        text_nombre.setText(null);
        text_apellido1.setText(null);
        text_apellido2.setText(null);
    }

    
}
