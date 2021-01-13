package fxPaaikkuna;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import lintubongari.*;


/**
 * @author Jemina Kotkajuuri
 * @version 21.4.2019
 *
 */
public class PaaikkunaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("PaaikkunaGUIView.fxml"));
            final Pane root = ldr.load();
            final PaaikkunaGUIController lintubongariCtrl = (PaaikkunaGUIController)ldr.getController();
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("paaikkuna.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Lintubongari");
            Lintubongari lintubongari = new Lintubongari();
            lintubongariCtrl.setLintubongari(lintubongari);
            
            primaryStage.setOnCloseRequest((event) -> {
                if ( !lintubongariCtrl.voikoSulkea() ) event.consume();
            });
        
        primaryStage.show();
        Application.Parameters params = getParameters();  
        if ( params.getRaw().size() > 0 )   {
            lintubongariCtrl.lueTiedosto(params.getRaw().get(0));
        }
        else 
            if ( !lintubongariCtrl.avaa() ) Platform.exit();             
        
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * @param args ei k�yt�ss�
	 */
	public static void main(String[] args) {
		launch(args);
	}
}