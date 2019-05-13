/*
 * El objetivo de esta aplicación es administrar las diferentes ventas y alquileres de una tienda de instrumentos
 * músicales. Habra una lista con cada uno de los clientes, los empleados y los instrumentos músicales que estan
 * o hayan estado en la tienda.
 */
package GraficoTienda;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Adrian Vidal 
 * @since 17/04/2019
 * @version 1.0
 */

public class main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
