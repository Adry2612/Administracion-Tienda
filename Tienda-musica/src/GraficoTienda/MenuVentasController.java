/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficoTienda;

import java.sql.*;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tienda.musica.Cliente;
import tienda.musica.Conexion;
import tienda.musica.Instrumento;
import tienda.musica.Venta;

/**
 * FXML Controller class
 *
 * @author DAW
 */
public class MenuVentasController implements Initializable {

    @FXML
    private TextField tf_id;
    @FXML
    private DatePicker cb_fecha;
    @FXML
    private ComboBox<Instrumento> cb_producto;
    @FXML
    private ComboBox<Cliente> cb_cliente;
    @FXML
    private TextField tf_precio;
    @FXML
    private Button but_eliminar;
    @FXML
    private Button but_modificar;
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
    private TableView<Venta> tv_ventas;
    @FXML
    private TableColumn<Venta, Integer> col_id;
    @FXML
    private TableColumn<Venta, String> col_producto;
    @FXML
    private TableColumn<Venta, String> col_cliente;
    @FXML
    private TableColumn<Venta, Date> col_fecha;
    @FXML
    private TableColumn<Venta, Integer> col_precio;
    private ObservableList <Venta> olVentas;
    
     private final ListChangeListener <Venta> selectorVenta = new ListChangeListener <Venta>()
    {
        @Override
        public void onChanged (ListChangeListener.Change<? extends Venta> c)
        {
            escribirVentaSel();
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
        
        olVentas = tv_ventas.getSelectionModel().getSelectedItems();
        olVentas.addListener(selectorVenta);
    }   

    public Venta obtenerTabla()
    {
       if (tv_ventas != null)
       {
           List <Venta> tabla = tv_ventas.getSelectionModel().getSelectedItems();
           
           if (tabla.size() == 1)
           {
               final Venta ventaSel = tabla.get(0);
               return ventaSel;
           }
       }

        return null; 
    }
    
    public void escribirVentaSel(){
        
        final Venta venta = obtenerTabla();
        Integer posicionVenta = olVentas.indexOf(venta);
        
        if (venta != null)
        {
            String idCliente = Integer.toString(venta.getId());
            
            tf_id.setText(idCliente);
            col_producto.setText(venta.getInstrumento().getNombre());
            col_cliente.setText(venta.getCliente().getNombre() +" "+ venta.getCliente().getApellido1());
            col_fecha.setText(venta.getFechaCompra().toString());
        }   
    }
    
    public void asociarValores()
    {
        col_id.setCellValueFactory(new PropertyValueFactory <Venta, Integer>("id"));
        col_producto.setCellValueFactory(new PropertyValueFactory <Venta, String>("instrumento"));
        col_cliente.setCellValueFactory(new PropertyValueFactory <Venta, String>("cliente"));
        col_fecha.setCellValueFactory(new PropertyValueFactory <Venta, Date>("fechaCompra"));
    }
    
    

    @FXML
    private void eliminarVenta(ActionEvent event) 
    {
        botonesInvisibles();
        but_borrar.setVisible(true);
        but_borrar.setDisable(false);
        but_vaciar.setVisible(true);
        but_vaciar.setDisable(false);
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
                
                stmt = con.prepareStatement("DELETE FROM Ventas WHERE Id = ?");
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
    private void modificarVenta(ActionEvent event) 
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
    private void confirmarActualizar(ActionEvent event) 
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null; 
        
        try
        {
            
        }
        
        catch (Exception ex)
        {
            
        }
    }

    
    @FXML
    private void confirmarGuardar(ActionEvent event) {
    }
    
    @FXML
    private void vaciarFormulario(ActionEvent event) {
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
    private void seleccionarProducto(ActionEvent event) 
    {
        olVentas = FXCollections.observableArrayList();
       
    }

    @FXML
    private void seleccionarCliente(ActionEvent event) {
    }

    @FXML
    private void nuevaVenta(ActionEvent event) {
    }
    
    private void rellenarInstrumentos()
    {
        ObservableList <String> instrumento = FXCollections.observableArrayList();
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try
        {
            stmt = con.prepareStatement("SELECT * FROM Instrumentos I, Cuerda C, Viento V, Percusion P WHERE I.Id = C.Id OR I.Id = V.Id OR I.Id = P.Id;");
            rs = stmt.executeQuery();
            
            while (rs.next())
            {
                Instrumento instrumento = new Cuerda ();
                instrumento.add(rs.getString("Nombre"));
            }
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
