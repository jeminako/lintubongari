package fxPaaikkuna;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import lintubongari.Havainto;
import lintubongari.Havaintotieto;
import lintubongari.Laji;
import lintubongari.Lintubongari;
import lintubongari.SailoException;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;

/**
 * @author Jemina Kotkajuuri
 * @version 21.4.2019
 *
 */
public class PaaikkunaGUIController implements Initializable {

    private String kayttaja = "jemina";
    
    @FXML
    private StringGrid<?> gridHavainnot;
    
    @FXML
    private TextField textHakukentta;
    
    @FXML
    private ComboBoxChooser<?> chooserHaku;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
        
    }
    

    @FXML private void handleApua() {
        apua();
    }

    @FXML private void handleAvaa() {
        avaa();
    }

    @FXML private void handleHaku() {
        haku();
    }
    
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }

    @FXML private void handleMuokkaaHavaintoa() {
        muokkaa();
    }

    @FXML private void handlePoistaHavainto() {
        poista();
    }

    @FXML private void handleTallenna() {
        tallenna();
    }

    @FXML private void handleTietoja() {
        ModalController.showModal(PaaikkunaGUIController.class.getResource("TiedotGUIView.fxml"), "Tiedot", null, "");
    }

    @FXML private void handleUusiHavainto() {
        uusiHavainto();
    }
    
    //=======================================================================
    
    private Lintubongari lintubongari;
    
    
    private void alusta() {
        //
    }
    
    
    /**
     * Avaa ohjelman suunnitelman selaimessa
     */
    private void apua() {
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2019k/ht/jelakotk");
            desktop.browse(oURL);
          } catch (Exception e) {
            e.printStackTrace();
          }
    }
    
    
    /**
     * Alustaa lintubongarin käyttäjän tiedostosta
     * @param nimi tiedosto josta lintubongarin tiedot luetaan
     * @return virheen epäonnistuessa
     */
    protected String lueTiedosto(String nimi) {
        kayttaja = nimi;
        try {
            lintubongari.lueTiedostostaHavainnot(nimi);
            hae();
            return null;
        } catch (SailoException e) {
            hae();
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }
    
    
    /**
    * Kysytään tiedoston nimi ja luetaan se
    * @return true jos onnistui, false jos ei
    */
    public boolean avaa() {
        String uusinimi = AlkuikkunaGUIController.kysyKayttaja(null, kayttaja);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }
    
    
    /**
     * Tallentaa ohjelman tiedostot
     */
    private String tallenna() {
        try {
            lintubongari.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
    }
    
    
    /**
     * Muokkaa ohjelman bongausta
     */
    private void muokkaa() {
        int riviNro = gridHavainnot.getRowNr();
        if (riviNro < 0) return;
        String pvm = gridHavainnot.get(riviNro, 0);
        String laji = gridHavainnot.get(riviNro, 1);
        int vanhaLajiIndeksi = lintubongari.annaLaji(lintubongari.loytyyko(laji)).getId();
        String paikkakunta = gridHavainnot.get(riviNro, 2);
        String sp = gridHavainnot.get(riviNro, 3);
        TiedonHakija th = new TiedonHakija(pvm, laji, paikkakunta, sp, riviNro);
        th = ModalController.showModal(PaaikkunaGUIController.class.getResource("Kuva2GUIView.fxml"), "Muokkaa havaintoa", null, th);
        if (th == null) return;
        pvm = th.getPvm(); laji = th.getLaji(); paikkakunta = th.getPaikkakunta(); sp = th.getSukupuoli();
        Havainto h = lintubongari.annaHavainto(riviNro);
        Havaintotieto ht = lintubongari.annaHavaintotieto(riviNro);
        h.setPvm(pvm);
        h.setPaikkakunta(paikkakunta);
        ht.setSukupuoli(sp);
        lajinTarkistus(laji, ht);
        if (lintubongari.lajiaKaytetty(vanhaLajiIndeksi) < 1) lintubongari.poistaLaji(vanhaLajiIndeksi);
        lintubongari.setMuutettu();
        hae();
    }
    
    
    /**
     * Poistaa bongauksen ohjelmasta
     */
    private void poista() {
        int riviNro = gridHavainnot.getRowNr();
        if (riviNro < 0) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko valittu bongaus?", "Kyllä", "Ei") )
            return;
        lintubongari.poista(riviNro);
        int havaintoja = gridHavainnot.getItems().size();
        if ( riviNro >= havaintoja ) riviNro = havaintoja -1;
        gridHavainnot.getFocusModel().focus(riviNro);
        gridHavainnot.getSelectionModel().select(riviNro);
        hae();
    }
    
    
    /**
     * Lisää uuden bongauksen ohjelmaan
     */
    private void uusiHavainto() {
        TiedonHakija th = new TiedonHakija(null, null, null, null, lintubongari.getHavaintoja());
        th = ModalController.showModal(PaaikkunaGUIController.class.getResource("Kuva2GUIView.fxml"), "Uusi havainto", null, th);
        if (th == null) return;
        String pvm = th.getPvm(); String laji = th.getLaji(); String paikkakunta = th.getPaikkakunta(); String sp = th.getSukupuoli();
        Havainto uusi = new Havainto(); uusi.rekisteroi(); uusi.setPvm(pvm); uusi.setPaikkakunta(paikkakunta);
        Havaintotieto uusiTieto = new Havaintotieto(); uusiTieto.setHavaintoId(uusi.getId()); uusiTieto.setSukupuoli(sp);
        lajinTarkistus(laji, uusiTieto);
        try {
            lintubongari.lisaa(uusi);
            lintubongari.lisaa(uusiTieto);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        lintubongari.setMuutettu();
        hae();
        
    }
    
    
    /**
     * Ohjelma hakemiseen
     */
    public void haku() {
        String haku = textHakukentta.getText().toLowerCase();
        int hakuehto = chooserHaku.getSelectedIndex();
        HakuehtoRajapinta hr = null;
        switch (hakuehto) {
            case 0:
                hr = new LajiHakuehto();
                break;
            case 1:
                hr = new PvmHakuehto();
                break;
            case 2:
                hr = new PaikkaHakuehto();
                break;
            default:
                hr = new SukupuoliHakuehto();
                break;
            }
        hae(hr, haku);
    }

    
    class LajiHakuehto implements HakuehtoRajapinta {
        @Override
        public boolean ehto(int i, String haku) {
            String ehto = lintubongari.lajinNimi(lintubongari.annaHavaintotieto(i).getLajiId()).toLowerCase();
            return ehto.contains(haku);
        }
    }
    
    
    class PvmHakuehto implements HakuehtoRajapinta {
        @Override
        public boolean ehto(int i, String haku) {
            String ehto = lintubongari.annaHavainto(i).getPvm().toLowerCase();
            return ehto.contains(haku);
        }
    }
    
    
    class PaikkaHakuehto implements HakuehtoRajapinta {
        @Override
        public boolean ehto(int i, String haku) {
            String ehto = lintubongari.annaHavainto(i).getPaikkakunta().toLowerCase();
            return ehto.contains(haku);
        }
    }
    
   
    class SukupuoliHakuehto implements HakuehtoRajapinta {
        @Override
        public boolean ehto(int i, String haku) {
            String ehto = lintubongari.annaHavaintotieto(i).getSukupuoliString().toLowerCase();
            return ehto.contains(haku);
        }
    }
    
    
    /**
    * Tarkistetaan onko tallennus tehty
    * @return saako sovelluksen sulkea?
    */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    
    /**
     * Asetetaan oma viite käytettävään lintubongariin
     * @param lintubongari käytettävä bongari
     */
    public void setLintubongari(Lintubongari lintubongari) {
        this.lintubongari = lintubongari;
    }


    /**
     * Antaa havaintokerran lajit
     * @param tieto tutkittava havaintotieto
     * @return havainnon lajit
     */
    public String taytaLajitiedot(Havaintotieto tieto) {
        String lajitiedot = "";
        int lajiId = tieto.getLajiId();
        lajitiedot = lajitiedot + lintubongari.lajinNimi(lajiId);
        return lajitiedot;
    }
    
    
    /**
     * Tarkistaa, löytyykö annettu laji jo käytetyistä lajeista. Jos ei löydy, lisää uuden lajin.
     * @param laji annettu laji
     * @param ht bongauksen havaintotieto
     */
    private void lajinTarkistus(String laji, Havaintotieto ht) {
        int lajiNro = lintubongari.loytyyko(laji);
        if (lajiNro != -1) {
            Laji l = lintubongari.annaLaji(lajiNro);
            int lajiId = l.getId();
            ht.setLajiId(lajiId);
        }
        else {
            Laji uusil = new Laji();
            uusil.rekisteroi();
            uusil.setLaji(laji);
            ht.setLajiId(uusil.getId());
            try {
                lintubongari.lisaa(uusil);
            } catch (SailoException e) {
                Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
                return;
            }
        }
    }
    
    
    /**
     * Antaa havaintokerran lajit
     * @param tieto tutkittava havaintotieto
     * @return havainnon lajit
     */
    public String taytaSukupuoli(Havaintotieto tieto) {
        String sukupuoliString = "";
        int sukupuoli = tieto.getSukupuoli();
        switch(sukupuoli) {
            case 0: 
                sukupuoliString = "Koiras";
                break;
            case 1:
                sukupuoliString = "Naaras";
                break;
            default:
                sukupuoliString = "Ei tietoa";
        }
        return sukupuoliString;
    }
    
    
    /**
     * Lisää havainnot ohjelmaan
     * @param hr Hakuehto
     * @param haku haku
     */
    protected void hae(HakuehtoRajapinta hr, String haku) {
        gridHavainnot.clear();
        
        for (int i = 0; i < lintubongari.getHavaintoja(); i++ ) {
            if (hr == null || haku == null || hr.ehto(i, haku)) {
                Havainto havainto = lintubongari.annaHavainto(i);
                Havaintotieto tieto = lintubongari.haeHavainnonTiedot(havainto.getId());
                gridHavainnot.add(havainto.getPvm(), taytaLajitiedot(tieto), havainto.getPaikkakunta(), taytaSukupuoli(tieto));
            }
        }
    }
    
    
    /**
     * Lisää havainnot ohjelmaan
     */
    protected void hae() {
        hae(null, null);
    }
}

