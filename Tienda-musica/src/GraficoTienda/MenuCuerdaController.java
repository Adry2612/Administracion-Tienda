/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficoTienda;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tienda.musica.Cliente;
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
    private TableView<?> tv_cuerda;
    @FXML
    private TableColumn<?, ?> col_id;
    @FXML
    private TableColumn<?, ?> col_nombre;
    @FXML
    private TableColumn<?, ?> col_marca;
    @FXML
    private TableColumn<?, ?> col_puente;
    @FXML
    private TableColumn<?, ?> col_calibre;
    @FXML
    private TableColumn<?, ?> col_precio;
    private ObservableList <Cuerda> ol_cuerda;

    private final ListChangeListener <Cliente> selectorClientes = new ListChangeListener <Cliente>()
    {
        @Override
        public void onChanged (ListChangeListener.Change<? extends Cuerda> c)
        {
            escribirInstrumentoSel();
        }
    };
    
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
        ol_cuerda.addListener(selectorClientes);
    }    
    
    public void escribirInstrumentoSel(){
        
        final Cuerda cuerda = obtenerTabla();
        Integer posicionCliente = ol_cuerda.indexOf(cuerda);
        
        if (cuerda != null)
        {
            String idCliente = Integer.toString(cuerda.getId());
            
            tf_id.setText(idCliente);
            tf_nombre.setText(cuerda.getNombre());
            tf_marca.setText(cuerda.getMarca());
            tf_calibre.setText(cuerda.getCalibreCuerda());
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
        col_marca.setCellValueFactory(new PropertyValueFactory <Cuerda, String>("apellido1"));
        col_calibre.setCellValueFactory(new PropertyValueFactory <Cuerda, String>("apellido2"));
        col_calibre.setCellValueFactory(new PropertyValueFactory <Cuerda, String>("apellido2"));
        col_calibre.setCellValueFactory(new PropertyValueFactory <Cuerda, String>("apellido2"));
    }
    
    

    @FXML
    private void actualizarInstrumento(ActionEvent event) {
    }

    @FXML
    private void vaciarFormulario(ActionEvent event) {
    }

    @FXML
    private void agregarInstrumento(ActionEvent event) {
    }

    @FXML
    private void eliminarInstrumento(ActionEvent event) {
    }
    
}
