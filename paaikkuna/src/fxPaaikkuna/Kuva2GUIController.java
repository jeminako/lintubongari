package fxPaaikkuna;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;

/**
 * @author Jemina Kotkajuuri
 * @version 21.4.2019
 *
 */
public class Kuva2GUIController implements ModalControllerInterface<TiedonHakija> {

    @FXML private Label labelVirhe;
    
    @FXML private TextField textLaji;

    @FXML private TextField textPvm;

    @FXML private TextField textPaikka;

    @FXML private TextField textSukupuoli;
    
    private TiedonHakija tiedot;
    private static final int KPITUUDET[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private static final int KVPITUUDET[] = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };


    @FXML void handleOK() {
        if (labelVirhe.getText() != "") return;
        if (textLaji.getText() == null || textPaikka.getText() == null || textSukupuoli.getText() == null || textPvm.getText() == null) return;
        tiedot.setLaji(textLaji.getText().trim());
        tiedot.setPaikkakunta(textPaikka.getText().trim());
        tiedot.setSukupuoli(textSukupuoli.getText().trim());
        tiedot.setPvm(textPvm.getText().trim());
        getResult();
        ModalController.closeStage(textLaji);
    }

    
    @FXML void handlePeruuta() {
        tiedot = null;
        ModalController.closeStage(textLaji);
    }

    
    @FXML void handleTarkistaSp() {
        onkoVirhe();
    }
    
    
    @FXML void handleTarkistaPvm() {
        onkoVirhe();
    }
    
    
    private void onkoVirhe() {
        String virhePvm = null; String virheSp = null;
        String pvmTeksti = textPvm.getText();
        if (pvmTeksti != null) {
            virhePvm = tarkistaPvm(pvmTeksti);
        }
        String spTeksti = textSukupuoli.getText();
        if (spTeksti != null) {
            virheSp = tarkistaSp(spTeksti);
        }
        if (virhePvm != null) {
            labelVirhe.setText(virhePvm);
            labelVirhe.setStyle("-fx-background-color:#FF0000");
        }
        else if (virheSp != null) {
            labelVirhe.setText(virheSp);
            labelVirhe.setStyle("-fx-background-color:#FF0000");
        }
        else {
            labelVirhe.setText("");
            labelVirhe.setStyle("-fx-background-color:#30FF00");
        }
    }
    
    
    @Override
    public TiedonHakija getResult() {
        return tiedot;
    }

    
    @Override
    public void handleShown() {
        //
    }

    
    @Override
    public void setDefault(TiedonHakija arg0) {
        tiedot = arg0;
        naytaBongauksenTiedot(tiedot);
        
    }
    
    private void naytaBongauksenTiedot(TiedonHakija tieto) {
        if (tieto == null) return;
        textLaji.setText(tieto.getLaji());
        textPvm.setText(tieto.getPvm());
        textPaikka.setText(tieto.getPaikkakunta());
        textSukupuoli.setText(tieto.getSukupuoli());
    }
    
    
    /**
    * Tarkistaa että päivämäärä on oikeaa muotoa pp.kk.vvvv
    * @param pvm tutkittava mahdollinen päivämäärä
    * @return null jos oikein, muuten virhe
    */
    private static String tarkistaPvm(String pvm) {
        if (!pvm.matches("([0-2][0-9]|[3][0-1])\\.((1[0-2])|(0[1-9]))\\.[0-9]{4}")) return "Päivämäärän pitää olla muotoa pp.kk.vvvv!";
        String[] osat = pvm.split("\\.");
        if (osat.length != 3) return "Päivämäärän pitää olla muotoa pp.kk.vvvv!";
        int paiva = Integer.parseInt(osat[0]);
        int kk = Integer.parseInt(osat[1]);
        int vuosi = Integer.parseInt(osat[2]);
        if (vuosi % 4 == 0) {
            if (paiva > KVPITUUDET[kk]) return "Tätä päivämäärää ei ole olemassa!";
        }
        if (vuosi % 4 != 0) {
            if (paiva > KPITUUDET[kk]) return "Tätä päivämäärää ei ole olemassa!";
        }
        return null;
    }
    
    
    /**
     * Tarkistaa sukupuolen
     * @param sp tarkistettava sukupuoli
     * @return null jos oikein, muuten virhe
     */
    private static String tarkistaSp(String sp) {
        if (sp.equalsIgnoreCase("Koiras") || sp.equalsIgnoreCase("Naaras") || sp.equalsIgnoreCase("Ei tietoa")) return null;
        return "Sukupuoli saa olla 'Koiras', 'Naaras' tai 'Ei tietoa'";
    }
}
