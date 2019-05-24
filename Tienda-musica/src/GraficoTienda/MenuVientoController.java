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
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tienda.musica.*;

/**
 * FXML Controller class
 *
 * @author Adrián
 */
public class MenuVientoController implements Initializable {

    @FXML
    private TextField tf_id;
    @FXML
    private TextField tf_nombre;
    @FXML
    private TextField tf_marca;
    @FXML
    private TextField tf_excitacion;
    @FXML
    private TextField tf_boquilla;
    @FXML
    private TextField tf_precio;
    @FXML
    private Button b_actualizar;
    @FXML
    private Button b_vaciar;
    @FXML
    private Button b_agregar;
    @FXML
    private Button b_eliminar;
    @FXML
    private TableView<Viento> tv_viento;
    @FXML
    private TableColumn<Viento, Integer> col_id;
    @FXML
    private TableColumn<Viento, String> col_nombre;
    @FXML
    private TableColumn<Viento, String> col_marca;
    @FXML
    private TableColumn<Viento, String> col_excitacion;
    @FXML
    private TableColumn<Viento, String> col_boquilla;
    @FXML
    private TableColumn<Viento, Double> col_precio;
    @FXML
    private Button b_volver;
    @FXML
    private Button b_modificar;
    @FXML
    private Button b_anadir;
    @FXML
    private Button b_borrar;
    private ObservableList <Viento> ol_viento;
    
    private final ListChangeListener <Viento> selectorInstrumento = new ListChangeListener <Viento>()
    {
        @Override
        public void onChanged (ListChangeListener.Change<? extends Viento> c)
        {
            escribirInstrumentoSel();
        }
    };
       
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ol_viento = FXCollections.observableArrayList(); 
        Viento.rellenarTabla(ol_viento);
        tv_viento.setItems(ol_viento);
        asociarValores(); 
        
        ol_viento = tv_viento.getSelectionModel().getSelectedItems();
        ol_viento.addListener(selectorInstrumento);
    }    
    
    public void escribirInstrumentoSel(){
        
        final Viento viento = obtenerTabla();
        Integer posicionInstrumento = ol_viento.indexOf(viento);
        
        if (viento != null)
        {
            String idInstrumento = Integer.toString(viento.getId());
            String precio = Double.toString(viento.getPrecio());
            tf_id.setText(idInstrumento);
            tf_nombre.setText(viento.getNombre());
            tf_marca.setText(viento.getMarca());
            tf_excitacion.setText(viento.getModoExcitacion());
            tf_boquilla.setText(viento.getTipoBoquilla());
        }   
    } 
    
    public void vaciaCampos()
    {
        String idMax = Integer.toString(idMaximo());
        tf_id.setText(idMax);
        tf_nombre.setText(null);
        tf_marca.setText(null);
        tf_boquilla.setText(null);
        tf_excitacion.setText(null);
        tf_precio.setText(null);
    }
    
    public Viento obtenerTabla()
    {
       if (ol_viento != null)
       {
           List <Viento> tabla = tv_viento.getSelectionModel().getSelectedItems();
           
           if (tabla.size() == 1)
           {
               final Viento instrumentoSel = tabla.get(0);
               return instrumentoSel;
           }
       }

        return null; 
    }
            
    public void asociarValores()
    {
        col_id.setCellValueFactory(new PropertyValueFactory <Viento, Integer>("id"));
        col_nombre.setCellValueFactory(new PropertyValueFactory <Viento, String>("nombre"));
        col_marca.setCellValueFactory(new PropertyValueFactory <Viento, String>("marca"));
        col_excitacion.setCellValueFactory(new PropertyValueFactory <Viento, String>("modoExcitacion"));
        col_boquilla.setCellValueFactory(new PropertyValueFactory <Viento, String>("tipoBoquilla"));
        col_precio.setCellValueFactory(new PropertyValueFactory <Viento, Double>("precio"));
    }
    
    @FXML
    private void vaciarFormulario(ActionEvent event) 
    {
        String idMax = Integer.toString(idMaximo());
        tf_id.setText(idMax);
        tf_nombre.setText(null);
        tf_marca.setText(null);
        tf_boquilla.setText(null);
        tf_excitacion.setText(null);
        tf_precio.setText(null);
    }
    
    @FXML
    private void modificarInstrumento(ActionEvent event) 
    {
        botonesPrinInvisibles();
        b_actualizar.setVisible(true);
        b_actualizar.setDisable(false);
        b_vaciar.setVisible(true);
        b_vaciar.setDisable(false);
        b_volver.setVisible(true);
        b_volver.setDisable(false);
    }
    
    @FXML
    private void actualizarInstrumento(ActionEvent event) 
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        tf_id.setEditable(false);
        String nombre = tf_nombre.getText();
        String marca = tf_marca.getText();
        String excitacion = tf_excitacion.getText();
        String boquilla = tf_boquilla.getText();
        double precio = Double.valueOf(tf_precio.getText());
        
        try
        {
            stmt = con.prepareStatement("UPDATE Viento SET Nombre = ?, Fabricante = ?, ModoExcitacion = ?, TipoBoquilla = ?, Precio = ? WHERE Id = ?");
            stmt.setString(1, nombre);
            stmt.setString(2, marca);
            stmt.setString(3, excitacion);
            stmt.setString(4, boquilla);
            stmt.setDouble(5, precio);
            stmt.setInt(6, Integer.parseInt(tf_id.getText()));
            stmt.executeUpdate();
            actualizarTableView();
            vaciaCampos();
        }
        
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void anadirInstrumento(ActionEvent event) 
    {
        botonesPrinInvisibles();
        b_agregar.setVisible(true);
        b_agregar.setDisable(false);
        b_vaciar.setVisible(true);
        b_vaciar.setDisable(false);
        b_volver.setVisible(true);
        b_volver.setDisable(false);
        
        String idMax = Integer.toString(idMaximo());
        tf_id.setText(idMax);
        tf_nombre.setText(null);
        tf_marca.setText(null);
        tf_boquilla.setText(null);
        tf_excitacion.setText(null);
        tf_precio.setText(null);
    }

    @FXML
    private void agregarInstrumento(ActionEvent event) 
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try
        {
            tf_id.setEditable(false);
            Integer id = idMaximo();
            String nombre = tf_nombre.getText();
            String marca = tf_marca.getText();
            String excitacion = tf_excitacion.getText();
            String boquilla = tf_boquilla.getText();
            double precio = Double.valueOf(tf_precio.getText());
            
            stmt = con.prepareStatement("INSERT INTO Instrumentos (Id, TipoInstrumento) VALUES (?, 2)");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
            stmt = con.prepareStatement("INSERT INTO Viento (Id, Nombre, Fabricante, ModoExcitacion, TipoBoquilla, Precio) VALUES (?, ?, ?, ?, ?, ?);");
            stmt.setInt(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, marca);
            stmt.setString(4, excitacion);
            stmt.setString(5, boquilla);
            stmt.setDouble(6, precio);
            stmt.executeUpdate();
            actualizarTableView();
            vaciaCampos();
        }
        
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
                
    }
    
    @FXML
    private void borrarInstrumento(ActionEvent event) 
    {
        botonesPrinInvisibles();
        b_eliminar.setVisible(true);
        b_eliminar.setDisable(false);
        b_vaciar.setVisible(true);
        b_vaciar.setDisable(false);
        b_volver.setVisible(true);
        b_volver.setDisable(false);
    }
    
    @FXML
    private void eliminarInstrumento(ActionEvent event) 
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
                
                stmt = con.prepareStatement("DELETE FROM Viento WHERE Id = ?");
                stmt.setInt(1, id);
                stmt.executeUpdate();
                
                stmt = con.prepareStatement("DELETE FROM Instrumentos WHERE Id = ?");
                stmt.setInt(1, id);
                stmt.executeUpdate();
                actualizarTableView();
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
    
    public void botonesPrinVisibles()
    {
        b_modificar.setVisible(true);
        b_anadir.setVisible(true);
        b_borrar.setVisible(true);
    }
    
    public void botonesPrinInvisibles()
    {
        b_modificar.setVisible(false);
        b_anadir.setVisible(false);
        b_borrar.setVisible(false);
    }
    
    public void actualizarTableView()
    {
        ol_viento = FXCollections.observableArrayList(); 
        Viento.rellenarTabla(ol_viento);
        tv_viento.setItems(ol_viento);
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

            stmt = con.prepareStatement ("SELECT MAX(Id) FROM Instrumentos");
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

    @FXML
    private void volver(ActionEvent event) 
    {
        botonesPrinVisibles();
        b_agregar.setVisible(false);
        b_agregar.setDisable(true);
        b_vaciar.setVisible(false);
        b_vaciar.setDisable(true);
        b_eliminar.setVisible(false);
        b_eliminar.setDisable(true);
        b_volver.setDisable(true);
        b_volver.setVisible(false);
        b_actualizar.setDisable(true);
        b_actualizar.setVisible(false);
    }
}
