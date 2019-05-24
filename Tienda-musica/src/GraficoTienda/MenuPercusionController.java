/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficoTienda;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tienda.musica.Conexion;
import tienda.musica.Percusion;
import tienda.musica.Viento;

/**
 * FXML Controller class
 *
 * @author Adrián
 */
public class MenuPercusionController implements Initializable {

    @FXML
    private TextField tf_id;
    @FXML
    private TextField tf_nombre;
    @FXML
    private TextField tf_marca;
    @FXML
    private TextField tf_membrana;
    @FXML
    private TextField tf_piezas;
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
    private TableView<Percusion> tv_percusion;
    @FXML
    private TableColumn<Percusion, Integer> col_id;
    @FXML
    private TableColumn<Percusion, String> col_nombre;
    @FXML
    private TableColumn<Percusion, String> col_marca;
    @FXML
    private TableColumn<Percusion, String> col_membrana;
    @FXML
    private TableColumn<Percusion, Integer> col_piezas;
    @FXML
    private TableColumn<Percusion, Double> col_precio;
    @FXML
    private Button b_modificar;
    @FXML
    private Button b_anadir;
    @FXML
    private Button b_borrar;
    @FXML
    private Button b_volver;
    private ObservableList <Percusion> ol_percusion;

    private final ListChangeListener <Percusion> selectorInstrumento= new ListChangeListener <Percusion>()
    {
        @Override
        public void onChanged (ListChangeListener.Change<? extends Percusion> c)
        {
            escribirInstrumentoSel();
        }
    };
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem mi_menyPrincipal;
    @FXML
    private MenuItem mi_cerrarSesion;
    @FXML
    private MenuItem mi_lista;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ol_percusion = FXCollections.observableArrayList(); 
        Percusion.rellenarTabla(ol_percusion);
        tv_percusion.setItems(ol_percusion);
        asociarValores(); 
        
        ol_percusion = tv_percusion.getSelectionModel().getSelectedItems();
        ol_percusion.addListener(selectorInstrumento);
    }    
    
    public void escribirInstrumentoSel(){
        
        final Percusion percusion = obtenerTabla();
        Integer posicionInstrumento = ol_percusion.indexOf(percusion);
        
        if (percusion != null)
        {
            String idInstrumento = Integer.toString(percusion.getId());
            String piezas = Integer.toString(percusion.getNumPiezas());
            
            tf_id.setText(idInstrumento);
            tf_nombre.setText(percusion.getNombre());
            tf_marca.setText(percusion.getMarca());
            tf_membrana.setText(percusion.getMaterialMembrana());
            tf_piezas.setText(piezas);
            tf_precio.setText(String.valueOf(percusion.getPrecio()));
        }   
    } 
    
    public Percusion obtenerTabla()
    {
       if (tv_percusion != null)
       {
           List <Percusion> tabla = tv_percusion.getSelectionModel().getSelectedItems();
           
           if (tabla.size() == 1)
           {
               final Percusion instrumentoSel = tabla.get(0);
               return instrumentoSel;
           }
       }

        return null; 
    }
            
    public void asociarValores()
    {
        col_id.setCellValueFactory(new PropertyValueFactory <Percusion, Integer>("id"));
        col_nombre.setCellValueFactory(new PropertyValueFactory <Percusion, String>("nombre"));
        col_marca.setCellValueFactory(new PropertyValueFactory <Percusion, String>("marca"));
        col_membrana.setCellValueFactory(new PropertyValueFactory <Percusion, String>("materialMembrana"));
        col_piezas.setCellValueFactory(new PropertyValueFactory <Percusion, Integer>("numPiezas"));
        col_precio.setCellValueFactory(new PropertyValueFactory <Percusion, Double>("precio"));
    }
    
    @FXML
    private void vaciarFormulario(ActionEvent event) 
    {
        String idMax = Integer.toString(idMaximo());
        tf_id.setText(idMax);
        tf_nombre.setText(null);
        tf_marca.setText(null);
        tf_membrana.setText(null);
        tf_piezas.setText(null);
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
        Integer piezas = Integer.parseInt(tf_piezas.getText());
        String membrana = tf_membrana.getText();
        double precio = Double.valueOf(tf_precio.getText());
        
        try
        {
            stmt = con.prepareStatement("UPDATE Percusion SET Nombre = ?, Fabricante = ?, MaterialMembrana = ?, NoPiezas = ?, Precio = ? WHERE Id = ?");
            stmt.setString(1, nombre);
            stmt.setString(2, marca);
            stmt.setString(3, membrana);
            stmt.setInt(4, piezas);
            stmt.setDouble(5, precio);
            stmt.setInt(6, Integer.parseInt(tf_id.getText()));
            stmt.executeUpdate();
            actualizarTableView();
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
        tf_membrana.setText(null);
        tf_piezas.setText(null);
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
            Integer id = idMaximo();
            tf_id.setEditable(false);
            tf_id.setText(Integer.toString(id));
            String nombre = tf_nombre.getText();
            String marca = tf_marca.getText();
            Integer piezas = Integer.parseInt(tf_piezas.getText());
            String membrana = tf_membrana.getText();
            double precio = Double.valueOf(tf_precio.getText());
            
            stmt = con.prepareStatement("INSERT INTO Instrumentos (Id, TipoInstrumento) VALUES (?, 3)");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
            stmt = con.prepareStatement("INSERT INTO Percusion (Id, Nombre, Fabricante, MaterialMembrana, NoPiezas, Precio) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, marca);
            stmt.setString(4, membrana);
            stmt.setInt(5, piezas);
            stmt.setDouble(6, precio);
            
            stmt.executeUpdate();
            actualizarTableView();
            vaciaCampos();
        }
        
        catch(SQLException ex)
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
                
                stmt = con.prepareStatement("DELETE FROM Percusion WHERE Id = ?");
                stmt.setInt(1, id);
                stmt.executeUpdate();
                
                stmt = con.prepareStatement("DELETE FROM Instrumentos WHERE Id = ?");
                stmt.setInt(1, id);
                stmt.executeUpdate();
                actualizarTableView();
                vaciaCampos();
            }

            else
            {
                System.out.println("Operación cancelada.");
            }
        }
        
        catch (Exception ex)
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
        ol_percusion = FXCollections.observableArrayList(); 
        Percusion.rellenarTabla(ol_percusion);
        tv_percusion.setItems(ol_percusion);
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
            stmt = con.prepareStatement ("SELECT MAX(Id) FROM Instrumentos");
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
    
    public void vaciaCampos()
    {
        String idMax = Integer.toString(idMaximo());
        tf_id.setText(idMax);
        tf_nombre.setText(null);
        tf_marca.setText(null);
        tf_membrana.setText(null);
        tf_piezas.setText(null);
        tf_precio.setText(null);
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
    private void volverMenuPrincipal(ActionEvent event) 
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
            Stage stage2 = (Stage)b_agregar.getScene().getWindow();
            stage2.close();

        }
        catch(Exception ex)
        {
            ex.getMessage();
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) 
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root1 = (Parent)fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Login");
            stage.show();
            Stage stage2 = (Stage)b_agregar.getScene().getWindow();
            stage2.close();

        }
        catch(IOException ex)
        {
            ex.getMessage();
        }
    }

    @FXML
    private void generarLista(ActionEvent event) 
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;       
        ResultSet rs = null;
        
        File archivo = null;
        FileWriter fw = null;
        
        try
        {
            archivo = new File ("listadoInstrumentosPercusion.txt");
            
            if (archivo.exists())
            {
                archivo.delete();
            }
                
            stmt = con.prepareStatement("SELECT * FROM Percusion;");
            rs = stmt.executeQuery();
            
            while (rs.next())
            {
                try
                {
                    fw = new FileWriter(archivo, true);
                    Percusion p = new Percusion (rs.getInt("Id"), rs.getString("Nombre"), rs.getString("Fabricante"), rs.getDouble("Precio"), rs.getString("MaterialMembrana"), rs.getInt("NoPiezas"));
                    fw.write(p.info());
                }
                
                catch (IOException ex)
                {
                    errorConexion();
                }
                
                finally
                {
                    try
                    {
                        if (fw != null) fw.close();
                    }

                    catch (IOException ex)
                    {
                        errorConexion();
                    }
                }   
            }
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
                conexion.desconectar(con);
            }
            
            catch (SQLException ex2)
            {
                errorConexion();
            }
        }
    }
    
    
    
}
