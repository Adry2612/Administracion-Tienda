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
import tienda.musica.Cliente;
import tienda.musica.Conexion;
import tienda.musica.Cuerda;

/**
 * FXML Controller class
 *
 * @author Adrián
 */
public class MenuCuerdaController implements Initializable {

    @FXML
    private TextField tf_id;
    @FXML
    private TextField tf_nombre;
    @FXML
    private TextField tf_marca;
    @FXML
    private TextField tf_calibre;
    @FXML
    private TextField tf_puente;
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
    private TableView<Cuerda> tv_cuerda;
    @FXML
    private TableColumn<Cuerda, Integer> col_id;
    @FXML
    private TableColumn<Cuerda, String> col_nombre;
    private TableColumn<Cuerda, String> col_marca;
    private TableColumn<Cuerda, String> col_puente;
    private TableColumn<Cuerda, Integer> col_calibre;
    private TableColumn<Cuerda, Integer> col_precio;
    private ObservableList <Cuerda> ol_cuerda;

    private final ListChangeListener <Cuerda> selectorInstrumento= new ListChangeListener <Cuerda>()
    {
        @Override
        public void onChanged (ListChangeListener.Change<? extends Cuerda> c)
        {
            escribirInstrumentoSel();
        }
    };
    @FXML
    private Button b_modificar;
    @FXML
    private Button b_anadir;
    @FXML
    private Button b_borrar;
    @FXML
    private Button b_volver;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ol_cuerda = FXCollections.observableArrayList(); 
        Cuerda.rellenarTabla(ol_cuerda);
        tv_cuerda.setItems(ol_cuerda);
        asociarValores(); 
        
        ol_cuerda = tv_cuerda.getSelectionModel().getSelectedItems();
        ol_cuerda.addListener(selectorInstrumento);
    }    
    
    public void escribirInstrumentoSel(){
        
        final Cuerda cuerda = obtenerTabla();
        Integer posicionCliente = ol_cuerda.indexOf(cuerda);
        
        if (cuerda != null)
        {
            String idCliente = Integer.toString(cuerda.getId());
            String calibre = Integer.toString(cuerda.getCalibreCuerda());
            
            tf_id.setText(idCliente);
            tf_nombre.setText(cuerda.getNombre());
            tf_marca.setText(cuerda.getMarca());
            tf_calibre.setText(calibre);
            tf_puente.setText(cuerda.getTipoPuente());
        }   
    } 
    
    public Cuerda obtenerTabla()
    {
       if (tv_cuerda != null)
       {
           List <Cuerda> tabla = tv_cuerda.getSelectionModel().getSelectedItems();
           
           if (tabla.size() == 1)
           {
               final Cuerda instrumentoSel = tabla.get(0);
               return instrumentoSel;
           }
       }

        return null; 
    }
            
    public void asociarValores()
    {
        col_id.setCellValueFactory(new PropertyValueFactory <Cuerda, Integer>("id"));
        col_nombre.setCellValueFactory(new PropertyValueFactory <Cuerda, String>("nombre"));
        col_marca.setCellValueFactory(new PropertyValueFactory <Cuerda, String>("marca"));
        col_calibre.setCellValueFactory(new PropertyValueFactory <Cuerda, Integer>("calibreCuerda"));
        col_puente.setCellValueFactory(new PropertyValueFactory <Cuerda, String>("tipoPuente"));
        col_precio.setCellValueFactory(new PropertyValueFactory <Cuerda, Integer>("precio"));
    }
    
    @FXML
    private void vaciarFormulario(ActionEvent event) 
    {
        String idMax = Integer.toString(idMaximo());
        tf_id.setText(idMax);
        tf_nombre.setText(null);
        tf_marca.setText(null);
        tf_calibre.setText(null);
        tf_puente.setText(null);
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
        Integer calibre = Integer.parseInt(tf_calibre.getText());
        String puente = tf_puente.getText();
        double precio = Double.valueOf(tf_precio.getText());
        
        try
        {
            stmt = con.prepareStatement("UPDATE Cuerda SET Nombre = ?,  = ?, Fabricante = ?, CalibreCuerda = ?, TipoPuente = ?, Precio = ? WHERE Id = ?");
            stmt.setString(1, nombre);
            stmt.setString(2, marca);
            stmt.setInt(3, calibre);
            stmt.setString(4, puente);
            stmt.setDouble(5, precio);
            stmt.setInt(6, Integer.parseInt(tf_id.getText()));
            stmt.executeUpdate();
            actualizarTableView();
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
        tf_calibre.setText(null);
        tf_puente.setText(null);
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
            Integer calibre = Integer.parseInt(tf_calibre.getText());
            String puente = tf_puente.getText();
            double precio = Double.valueOf(tf_precio.getText());
            stmt = con.prepareStatement("INSERT INTO Instrumento (Id, TipoInstrumento) VALUES (?, 1)");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            stmt = con.prepareStatement("INSERT INTO Cuerda (Id, Nombre, Apellido1, Apellido2) VALUES (?, ?, ?, ?)");
            stmt.setString(1, nombre);
            stmt.setString(2, marca);
            stmt.setInt(3, calibre);
            stmt.setString(4, puente);
            stmt.setDouble(5, precio);
            stmt.setInt(6, Integer.parseInt(tf_id.getText()));
            stmt.executeQuery();
            actualizarTableView();
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
                
                stmt = con.prepareStatement("DELETE FROM Cuerda WHERE Id = ?");
                stmt.setInt(1, id);
                stmt.executeUpdate();
                
                stmt = con.prepareStatement("DELETE FROM Instrumento WHERE Id = ?");
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
        ol_cuerda = FXCollections.observableArrayList(); 
        Cuerda.rellenarTabla(ol_cuerda);
        tv_cuerda.setItems(ol_cuerda);
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

            stmt = con.prepareStatement ("SELECT MAX(Id) FROM Instrumento");
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
