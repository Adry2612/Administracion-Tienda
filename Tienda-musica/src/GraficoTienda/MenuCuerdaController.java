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
import tienda.musica.Cliente;
import tienda.musica.Conexion;
import tienda.musica.Cuerda;
import tienda.musica.Viento;

/**
 * Clase controladora de FXML de instrumentos de cuerda.
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
    @FXML
    private TableColumn<Cuerda, String> col_marca;
    @FXML
    private TableColumn<Cuerda, String> col_puente;
    @FXML
    private TableColumn<Cuerda, Integer> col_calibre;
    @FXML
    private TableColumn<Cuerda, Double> col_precio;
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
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem mi_menyPrincipal;
    @FXML
    private MenuItem mi_cerrarSesion;
    @FXML
    private MenuItem mi_lista;
    
    /**
     * Inicializa la clase controladora y se llama a varios metodos para rellenar el TableView y el formulario.
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
    
    /**
     * Escribe en el TableView los datos del instrumento de cuerda obtenidos en la base de datos.
     * @since 04/05/2019
     */
    public void escribirInstrumentoSel(){
        
        final Cuerda cuerda = obtenerTabla();
        Integer posicionInstrumento = ol_cuerda.indexOf(cuerda);
        
        if (cuerda != null)
        {
            String idInstrumento = Integer.toString(cuerda.getId());
            String calibre = Integer.toString(cuerda.getCalibreCuerda());
            
            tf_id.setText(idInstrumento);
            tf_nombre.setText(cuerda.getNombre());
            tf_marca.setText(cuerda.getMarca());
            tf_calibre.setText(calibre);
            tf_puente.setText(cuerda.getTipoPuente());
            tf_precio.setText(String.valueOf(cuerda.getPrecio()));
        }   
    } 
    
    /**
     * Obtiene los valores para escribir en el TableView los datos del instrumento de cuerda obtenidos en la base de datos.
     * @since 04/05/2019
     */
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
     
    /**
     * Asocia los valores obtenidos de la base de datos a las diferentes columnas del TableView.
     * @since 04/05/2019
     */
    public void asociarValores()
    {
        col_id.setCellValueFactory(new PropertyValueFactory <Cuerda, Integer>("id"));
        col_nombre.setCellValueFactory(new PropertyValueFactory <Cuerda, String>("nombre"));
        col_marca.setCellValueFactory(new PropertyValueFactory <Cuerda, String>("marca"));
        col_calibre.setCellValueFactory(new PropertyValueFactory <Cuerda, Integer>("calibreCuerda"));
        col_puente.setCellValueFactory(new PropertyValueFactory <Cuerda, String>("tipoPuente"));
        col_precio.setCellValueFactory(new PropertyValueFactory <Cuerda, Double>("precio"));
    }
    
    /**
     * Deja en blanco los valores escritos en el formulario.
     * @param event Se ejecutará cuando el usuario hago click sobre el botón de vaciar formulario.
     * @since 04/05/2019
     */
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
    
    /**
     * Deja en blanco los valores escritos en el formulario. A diferencia del anterior, este no necesita la acción del boton para ejecutarse,
     * ya que se ejecutará cuando se añada, modifique o elimine un instrumento.
     * @since 04/05/2019
     */
    public void vaciaCampos()
    {
        String idMax = Integer.toString(idMaximo());
        tf_id.setText(idMax);
        tf_nombre.setText(null);
        tf_marca.setText(null);
        tf_calibre.setText(null);
        tf_puente.setText(null);
        tf_precio.setText(null);
    }
    
    /**
     * Nos permite modificar aquel instrumento seleccionado en el TableView. 
     * Desaparecerán los botones principales y aparecerá el botón de guardar cambios y vaciar formulario.
     * @param event Se ejecutará cuando el usuario hago click sobre el botón de modificar.
     * @since 04/05/2019
     */
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
    
    /**
     * Guarda los cambios de aquel instrumento seleccionado en el TableView. 
     * Realiza un UPDATE a la base de datos con todos los valores del formulario.
     * Actualizará el TableView para ver los cambios y  vacia formulario.
     * @param event Se ejecutará cuando el usuario hago click sobre el botón de guardar.
     * @since 04/05/2019
     */
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
            stmt = con.prepareStatement("UPDATE Cuerda SET Nombre = ?, Fabricante = ?, CalibreCuerda = ?, TipoPuente = ?, Precio = ? WHERE Id = ?");
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
     * Nos permite añador un instrumento con los valores que indicamos en el formulario. 
     * Desaparecerán los botones principales y aparecerá el botón de guardar cambios y vaciar formulario.
     * @param event Se ejecutará cuando el usuario hago click sobre el botón de Añadir.
     * @since 04/05/2019
     */
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

    /**
     * Añade el instrumento asignandole un id automatico. 
     * Realiza un INSERT a la base de datos con todos los valores del formulario.
     * Actualizará el TableView para ver los cambios y vacia formulario.
     * @param event Se ejecutará cuando el usuario hago click sobre el botón de Añadir.
     * @since 04/05/2019
     */
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
            stmt = con.prepareStatement("INSERT INTO Instrumentos (Id, TipoInstrumento) VALUES (?, 1)");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
            stmt = con.prepareStatement("INSERT INTO Cuerda (Id, Nombre, Fabricante, CalibreCuerda, TipoPuente, Precio) VALUES (?, ?, ?, ?, ?, ?);");
            stmt.setInt(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, marca);
            stmt.setInt(4, calibre);
            stmt.setString(5, puente);
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
    
    /**
     * Nos permite eliminar aquel instrumento seleccionado en el TableView. 
     * Desaparecerán los botones principales y aparecerá el botón de eliminar cambios y vaciar formulario.
     * @param event Se ejecutará cuando el usuario hago click sobre el botón de Eliminar.
     * @since 04/05/2019
     */
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
    
    /**
     * Elimina aquel instrumento seleccionado en el TableView. 
     * Realiza un DELETE a la base de datos con todos los valores del formulario.
     * Actualizará el TableView para ver los cambios y  vacia formulario.
     * @param event Se ejecutará cuando el usuario hago click sobre el botón de guardar.
     * @since 04/05/2019
     */
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
     * Metodo que vuelve a llamara al metodo rellenarTabla del instrumento para realizar una actualización de la pantalla.
     * @since 04/05/2019
     */
    public void actualizarTableView()
    {
        ol_cuerda = FXCollections.observableArrayList(); 
        Cuerda.rellenarTabla(ol_cuerda);
        tv_cuerda.setItems(ol_cuerda);
        asociarValores();
    }
    
    /**
     * Busca en la base de datos el Id más alto y le suma uno para obtener un id automatico.
     * @return Integer con el valor del Id más alto + 1.
     * @since 04/05/2019
     */
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

    /**
     * Muestra los botones principales y desabilita y convierte en invisible los demás botones.
     * @since 04/05/2019
     */
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
    
     /**
     * Metodo que abre la ventana correspondiente a los menu principal.
     * @param event Se ejecutará cuando el usuario hago click sobre el botón del item de menu del menu principal cerrando la ventana anterior.
     * @since 04/05/2019
     */
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

    /**
     * Metodo que abre la ventana correspondiente al Login.
     * @param event Se ejecutará cuando el usuario hago click sobre el botón del item de menu del cerrar sesión cerrando la ventana anterior.
     * @since 04/05/2019
     */
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

    /**
     * Metodo que genera un .txt con todos los intrumentos.
     * @param event Se ejecutará cuando el usuario hago click sobre el botón del item de generar lista.
     * @since 04/05/2019
     */
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
            archivo = new File ("listadoInstrumentosCuerda.txt");
            
            if (archivo.exists())
            {
                archivo.delete();
            }
                
            stmt = con.prepareStatement("SELECT * FROM Cuerda;");
            rs = stmt.executeQuery();
            
            while (rs.next())
            {
                try
                {
                    fw = new FileWriter(archivo, true);
                    Cuerda c = new Cuerda (rs.getInt("Id"), rs.getString("Nombre"), rs.getString("Fabricante"), rs.getDouble("Precio"), rs.getInt("CalibreCuerda"), rs.getString("TipoPuente"));
                    fw.write(c.info());
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
