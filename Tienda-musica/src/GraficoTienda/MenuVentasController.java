/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraficoTienda;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private ComboBox<?> cb_producto;
    @FXML
    private ComboBox<?> cb_cliente;
    @FXML
    private TextField tf_precio;
    @FXML
    private Button but_eliminar;
    @FXML
    private Button but_modificar;
    @FXML
    private Button but_anadir;
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
        olVentas.addListener(selectorVentas);
    }   
    
    public void escribirClienteSel(){
        
        final Venta venta = obtenerTabla();
        Integer posicionVenta = olVentas.indexOf(venta);
        
        if (venta != null)
        {
            String idCliente = Integer.toString(venta.getId());
            
            tf_id.setText(idCliente);
            cb_producto.setText(venta.getInstrumento().getNombre());
            text_apellido1.setText(venta.getApellido1());
            text_apellido2.setText(venta.getApellido2());
        }   
    } 

    @FXML
    private void eliminarVenta(ActionEvent event) {
    }

    @FXML
    private void modificarVenta(ActionEvent event) {
    }

    @FXML
    private void nuevaVenta(ActionEvent event) {
    }
    
}
