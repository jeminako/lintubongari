package fxPaaikkuna;

/**
 * @author Jemina Kotkajuuri
 * @version 21.4.2019
 * Luokka auttamaan tietojen hakemisessa käyttöliittymään
 */
public class TiedonHakija {
    private String pvm;
    private String laji;
    private String paikkakunta;
    private String sukupuoli;
    private int indeksi;
    
    
    /**
     * @param pvm rivin päivämäärä
     * @param laji rivin laji
     * @param paikkakunta rivin paikkakunta
     * @param sukupuoli rivin sukupuoli
     * @param indeksi vastaavan bongauksen indeksi
     */
    public TiedonHakija(String pvm, String laji, String paikkakunta, String sukupuoli, int indeksi) {
        this.pvm = pvm;
        this.laji = laji;
        this.paikkakunta = paikkakunta;
        this.sukupuoli = sukupuoli;
        this.indeksi = indeksi;
    }
    
    
    /**
     * Palauttaa päivämääärän
     * @return päivämäärä
     */
    public String getPvm() {
        return pvm;
    }

    
    /**
     * Palauttaa lajitiedon
     * @return laji
     */
    public String getLaji() {
        return laji;
    }
    
    
    /**
     * Palauttaa paikkakunnan
     * @return paikkakunta
     */
    public String getPaikkakunta() {
        return paikkakunta;
    }
    
    
    /**
     * Palauttaa sukupuolen
     * @return sukupuoli
     */
    public String getSukupuoli() {
        return sukupuoli;
    }
    
    
    /**
     * Palauttaa kentän indeksin
     * @return indeksi
     */
    public int getIndeksi() {
        return indeksi;
    }    
    
    
    /**
     * Asettaa sukupuolen
     * @param sukupuoli uusi sukupuoli
     */
    public void setSukupuoli(String sukupuoli) {
        this.sukupuoli = sukupuoli;
    }

    
    /**
     * Asettaa päivämäärän
     * @param pvm uusi päivämäärä
     */
    public void setPvm(String pvm) {
        this.pvm = pvm;
    }

    
    /**
     * Asettaa lajin
     * @param laji uusi laji
     */
    public void setLaji(String laji) {
        this.laji = laji;
    }
    
    
    /**
     * Asettaa paikkakunnan
     * @param paikkakunta uusi paikkakunta
     */
    public void setPaikkakunta(String paikkakunta) {
        this.paikkakunta = paikkakunta;
    }
    
}
