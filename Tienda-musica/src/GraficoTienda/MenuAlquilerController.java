/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficoTienda;

import java.net.URL;
import java.sql.*;
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
    private ComboBox<Instrumento> cb_producto;
    @FXML
    private ComboBox<Cliente> cb_cliente;
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
    private ObservableList <Venta> olVentas;
    
     private final ListChangeListener <Alquiler> selectorAlquiler = new ListChangeListener <Alquiler>()
    {
        @Override
        public void onChanged (ListChangeListener.Change<? extends Alquiler> c)
        {
            escribirAlquilerSel();
        }
    };

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        olVentas = FXCollections.observableArrayList(); 
        Venta.rellenarTabla(olVentas);
        tv_ventas.setItems(olVentas);
        asociarValores(); 
        rellenarClientes();
        rellenarInstrumentos();
        
        olVentas = tv_ventas.getSelectionModel().getSelectedItems();
        olVentas.addListener(selectorVenta);
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
    private void confirmarEliminar(ActionEvent event) 
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
                
                stmt = con.prepareStatement("DELETE FROM Alquiler WHERE Id = ?");
                stmt.setInt(1, id);
                stmt.executeUpdate();
                actualizarTableView();
            }

            else
            {
                System.out.println("Operación cancelada.");
            }
        }
        
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void confirmarGuardar(ActionEvent event) {
    }

    @FXML
    private void confirmarActualizar(ActionEvent event) {
    }
    
    @FXML
    private void vaciarFormulario(ActionEvent event) 
    {
        String idMax = Integer.toString(idMaximo());
        tf_id.setText(idMax);
        cb_producto.setValue(null);
        cb_cliente.setValue(null);
        cb_fecha.setValue(null);
    }

    @FXML
    private void volver(ActionEvent event) 
    {
        botonesVisibles();
        but_actualizar.setVisible(false);
        but_actualizar.setDisable(true);
        but_borrar.setVisible(false);
        but_borrar.setDisable(true);
        but_guardar.setVisible(false);
        but_guardar.setDisable(true);
        b_volver.setDisable(true);
        b_volver.setVisible(false);
        but_vaciar.setVisible(false);
        but_vaciar.setDisable(true);
    }
    
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
    
    public void actualizarTableView()
    {
        olVentas = FXCollections.observableArrayList();
        Venta.rellenarTabla(olVentas);
        tv_ventas.setItems(olVentas);
        asociarValores();
    }
    
    public Integer idMaximo()
    {
        Integer idMax = null;
        try
        {
            Conexion conexion = new Conexion();
            Connection con = conexion.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = con.prepareStatement ("SELECT MAX(Id) FROM Ventas");
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
