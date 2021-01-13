package fxPaaikkuna;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Jemina Kotkajuuri
 * @version 21.4.2019
 *
 */
public class AlkuikkunaGUIController implements ModalControllerInterface<String> {

    @FXML private TextField textAnnaKayttaja; private String annettuKayttaja = null;

    @FXML private Button buttonOK;

    @FXML private Button buttonPeruuta;

    @FXML void handleOK() {
        annettuKayttaja = textAnnaKayttaja.getText();
        ModalController.closeStage(textAnnaKayttaja);
    }

    @FXML void handlePeruuta() {
        ModalController.closeStage(textAnnaKayttaja);
    }

    @Override
    public String getResult() {
        return annettuKayttaja;
    }

    @Override
    public void handleShown() {
        textAnnaKayttaja.requestFocus();
    }

    @Override
    public void setDefault(String oletus) {
        textAnnaKayttaja.setText(oletus);
    }
    
    /**
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus  mitä nimeä näytetään oletuksena
     * @return null jos painetaan Peruuta, muuten kirjoitettu käyttäjä
     */
    public static String kysyKayttaja(Stage modalityStage, String oletus) {
        return ModalController.showModal(AlkuikkunaGUIController.class.getResource("AlkuikkunaGUIView.fxml"),"Lintubongari",modalityStage, oletus);
        }
    
    //==========================================================

}

