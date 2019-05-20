/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficoTienda;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import tienda.musica.*;
import java.sql.*;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

/**
 * FXML Controller class
 *
 * @author Adrián
 */
public class MenuInstrumentosController implements Initializable {

    @FXML
    private TextField tf_id;
    @FXML
    private TextField tf_nombre;
    @FXML
    private TextField tf_marca;
    @FXML
    private Button b_anadir;
    @FXML
    private Button b_borrar;
    @FXML
    private TextField tf_puente;
    @FXML
    private TextField tf_precio;
    @FXML
    private ComboBox<String> cb_tipo;
    @FXML
    private TextField tf_calibre;
    @FXML
    private TextField tf_boquilla;
    @FXML
    private TextField tf_membrana;
    @FXML
    private TextField tf_excitacion;
    @FXML
    private TextField tf_piezas;
    @FXML
    private Button b_modificar;
    @FXML
    private Button b_guardar;
    @FXML
    private Button b_vaciar;
    @FXML
    private TableColumn<Instrumento, ?> col_id;
    @FXML
    private TableColumn<?, ?> col_nombre;
    @FXML
    private TableColumn<?, ?> col_apellido1;
    @FXML
    private TableColumn<?, ?> col_descrip;
    @FXML
    private TableColumn<?, ?> col_descrip1;
    @FXML
    private TableView<Viento> tableview_viento;
    @FXML
    private TableColumn<?, ?> col_calibre;
    @FXML
    private TableColumn<?, ?> col_puente;
    @FXML
    private TableView<Cuerda> tableview_cuerda;
    @FXML
    private TableColumn<?, ?> col_boquilla;
    @FXML
    private TableColumn<?, ?> col_excitacion;
    @FXML
    private TableView<Percusion> tableview_percusion;
    @FXML
    private TableView<Instrumento> tableview_instrumentos;
    @FXML
    private TableColumn<?, ?> col_membrana;
    @FXML
    private TableColumn<?, ?> col_piezas;
    private ObservableList <Instrumento> instrumento;
    private ObservableList <Cuerda> cuerda;
    private ObservableList <Viento> viento;
    private ObservableList <Percusion> percusion;
    
    /*
    private final ListChangeListener <Cliente> selectorClientes = new ListChangeListener <Cliente>()
    {
        @Override
        public void onChanged (ListChangeListener.Change<? extends Cliente> c)
        {
            escribirInstrumentoSel();
        }
    };
    

    */
    
    /**
     * Inicializa la clase controladora.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        rellenarComboBox();
        
        if (cb_tipo.getSelectionModel().getSelectedItem() == "Cuerda")
        {
            visibleCuerda();
        } 
        
        else
        {
            if (cb_tipo.getSelectionModel().getSelectedItem() == "Viento")
            {
                visibleViento();
            }
            
            else
            {
                visiblePercusion();
            }
        }
    }

    public Instrumento obtenerTablaInstrumentos()
    {
        if (tableview_instrumentos != null)
        {
            List <Instrumento> tabla = tableview_instrumentos.getSelectionModel().getSelectedItems();
            
            if (tabla.size() == 1)
            {
                final Instrumento instrumentoSel = tabla.get(0);
                return instrumentoSel;
            }
        }
        
        return null;
    }
    
    public void asociarValores()
    {
        
    }
   

    @FXML
    private void anadirInstrumento(ActionEvent event) {
    }

    @FXML
    private void borrarInstrumento(ActionEvent event) {
    }
    
    private void rellenarComboBox() 
    {
        ObservableList <String> tipoInstrumento = FXCollections.observableArrayList();
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try
        {
            stmt = con.prepareStatement("SELECT * FROM TIpoInstrumento");
            rs = stmt.executeQuery();
            
            while (rs.next())
            {
                tipoInstrumento.add(rs.getString(2));
            }
            
            cb_tipo.setItems(tipoInstrumento);
            
        }
        
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public void parametrosInvisibles()
    {
        tf_puente.setVisible(false);
        tf_puente.setDisable(true);
        tf_calibre.setVisible(false);
        tf_calibre.setDisable(true);
        tableview_cuerda.setVisible(false);
        tableview_cuerda.setDisable(true);
        tf_boquilla.setVisible(false);
        tf_boquilla.setDisable(true);
        tf_excitacion.setVisible(false);
        tf_excitacion.setDisable(true);
        tableview_viento.setVisible(false);
        tableview_viento.setDisable(true);
        tf_membrana.setVisible(false);
        tf_membrana.setDisable(true);
        tf_piezas.setVisible(false);
        tf_piezas.setDisable(true);
        tableview_percusion.setVisible(false);
        tableview_percusion.setDisable(true);
    }
    
    public void visibleCuerda()
    {
        tf_puente.setVisible(true);
        tf_puente.setDisable(false);
        tf_calibre.setVisible(true);
        tf_calibre.setDisable(false);
        tableview_cuerda.setVisible(true);
        tableview_cuerda.setDisable(false);
    }
    
    public void visibleViento()
    {
        tf_boquilla.setVisible(true);
        tf_boquilla.setDisable(false);
        tf_excitacion.setVisible(true);
        tf_excitacion.setDisable(false);
        tableview_viento.setVisible(true);
        tableview_viento.setDisable(false);
    }
    
    public void visiblePercusion()
    {
        tf_membrana.setVisible(true);
        tf_membrana.setDisable(false);
        tf_piezas.setVisible(true);
        tf_piezas.setDisable(false);
        tableview_percusion.setVisible(true);
        tableview_percusion.setDisable(false);
    }

    @FXML
    private void modificarInstrumento(ActionEvent event) {
    }

    @FXML
    private void guardarCambiosNuevo(ActionEvent event) {
    }

    @FXML
    private void vaciarFormulario(ActionEvent event) {
    }

    
}
