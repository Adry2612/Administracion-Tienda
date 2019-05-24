/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficoTienda;

import java.sql.*;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
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
import tienda.musica.*;


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
        
        rellenarClientes();
        rellenarInstrumentos();
        
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
        
        try{
            if (venta != null)
            {
                String idVenta = Integer.toString(venta.getId());

                tf_id.setText(idVenta);
                cb_producto.setValue(venta.getInstrumento());
                cb_cliente.setValue(venta.getCliente());
                cb_fecha.setValue(venta.getFechaCompra().toLocalDate());
                tf_precio.setText(String.valueOf(venta.getPrecio()));


            }   
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public void asociarValores()
    {
        col_id.setCellValueFactory(new PropertyValueFactory <Venta, Integer>("id"));
        col_producto.setCellValueFactory(new PropertyValueFactory <Venta, String>("instrumento"));
        col_cliente.setCellValueFactory(new PropertyValueFactory <Venta, String>("cliente"));
        col_fecha.setCellValueFactory(new PropertyValueFactory <Venta, Date>("fechaCompra"));
        col_precio.setCellValueFactory(new PropertyValueFactory <Venta, Integer> ("precio"));
    }
    
    @FXML
    private void nuevaVenta(ActionEvent event) 
    {
        botonesInvisibles();
        tf_id.setEditable(false);
        but_guardar.setVisible(true);
        but_guardar.setDisable(false);
        but_vaciar.setVisible(true);
        but_vaciar.setDisable(false);
        b_volver.setVisible(true);
        b_volver.setDisable(false);
        
        try
        {
            String idMax = Integer.toString(idMaximo());
            tf_id.setText(idMax);
            cb_producto.setValue(null);
            cb_cliente.setValue(null);
            cb_fecha.setValue(null);
        }
        
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    @FXML
    private void confirmarGuardar(ActionEvent event) 
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try
        {
            Integer id = idMaximo();
            Instrumento ins = cb_producto.getValue();
            Cliente cli = cb_cliente.getValue();
            LocalDate fecha = cb_fecha.getValue();
            Date fechaConv = Date.valueOf(fecha);
            double precio = ins.getPrecio() + (ins.getPrecio()*0.21);
            
            stmt = con.prepareStatement("INSERT INTO Venta (IdVenta, Instrumento, Cliente, FechaVenta, Precio) VALUES (?, ?, ?, ?, ?);");
            stmt.setInt(1, id);
            stmt.setInt(2, ins.getId());
            stmt.setInt(3, cli.getId());
            stmt.setDate(4, fechaConv);
            stmt.setDouble(5, precio);
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
                
                stmt = con.prepareStatement("DELETE FROM Venta WHERE IdVenta = ?");
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
                if (con != null) con.close();  
            }
            
            catch (SQLException ex)
            {
                errorConexion();
            }
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
        
        tf_id.setDisable(true);
        Instrumento ins = cb_producto.getValue();
        Cliente cli = cb_cliente.getValue();
        LocalDate fecha = cb_fecha.getValue();
        Date fechaConv = Date.valueOf(fecha);

        try
        {
            stmt = con.prepareStatement("UPDATE Venta SET Instrumento = ?, Cliente = ?, FechaVenta = ? WHERE IdVenta = ?;");
            stmt.setInt(1, ins.getId());
            stmt.setInt(2, cli.getId());
            stmt.setDate(3, fechaConv);
            stmt.setInt(4, Integer.parseInt(tf_id.getText()));
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
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try
        {
            stmt = con.prepareStatement ("SELECT MAX(IdVenta) FROM Venta");
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
    
    public void alertaValorNecesario()
    {
        Alert alert = new Alert (Alert.AlertType.ERROR);
        alert.setTitle("ERROR AL INSERTAR DATOS.");
        alert.setHeaderText(null);
        alert.setContentText("No se puede insertar un cliente sin nombre o apellido.");
        alert.showAndWait();
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
                Instrumento ins1 = new Viento (rs.getInt("Id"), rs.getString("Nombre"), rs.getString("Fabricante"), rs.getDouble("Precio"), rs.getString("ModoExcitacion"), rs.getString("TipoBoquilla")); 
                instrumento.add(ins1);
            }
            
            stmt = con.prepareStatement ("SELECT * FROM Percusion;");
            rs = stmt.executeQuery();
            
            while (rs.next())
            {
                Instrumento ins1 = new Percusion (rs.getInt("Id"), rs.getString("Nombre"), rs.getString("Fabricante"), rs.getDouble("Precio"), rs.getString("MaterialMembrana"), rs.getInt("NoPiezas")); 
                instrumento.add(ins1);
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
                if (con != null) con.close();  
            }
            
            catch (SQLException ex)
            {
                errorConexion();
            }
        }
        
        cb_producto.setItems(instrumento);
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
        
        cb_cliente.setItems(cliente);
    }
    
    public void errorConexion()
    {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMACIÓN");
        alert.setHeaderText(null);
        alert.setContentText("Parece que ha habido un error de conexión con la base de datos. Intentalo de nuevo más tarde.");
        alert.showAndWait();
    } 

    public void vaciarFormularioAuto()
    {
        String idMax = Integer.toString(idMaximo());
        tf_id.setText(idMax);
        cb_producto.setValue(null);
        cb_cliente.setValue(null);
        cb_fecha.setValue(null);
    }  
}
