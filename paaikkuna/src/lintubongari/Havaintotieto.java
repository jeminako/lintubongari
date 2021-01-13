package lintubongari;

import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Jemina Kotkajuuri
 * @version 21.4.2019
 *
 */
public class Havaintotieto {
    private final static String KOIRAS = "Koiras";
    private final static String NAARAS = "Naaras";
    private final static String EI_TIETOA = "Ei tietoa";
    private int havaintoId;
    private int lajiId;
    private int sukupuoli;
    
    
    /**
     * Palauttaa havaintotiedon havaintotunnuksen.
     * @return havaintotiedon havaintotunnusluvun
     * @example
     * <pre name="test">
     * Havaintotieto h1 = new Havaintotieto();
     * h1.setHavaintoId(3);
     * h1.getHavaintoId() === 3;
     * </pre>
     */
    public int getHavaintoId() {
        return havaintoId;
    }
    
    
    /**
     * Palauttaa havaintotiedon havaintotunnuksen.
     * @return havaintotiedon havaintotunnusluvun
     */
    public int getLajiId() {
        return lajiId;
    }
    
    
    /**
     * Palauttaa havainnon sukupuolen
     * @return sukupuoli
     */
    public int getSukupuoli() {
        return sukupuoli;
    }

    
    /**
     * Palauttaa havainnon sukupuolen tekstinä
     * @return sukupuoli tekstinä
     */
    public String getSukupuoliString() {
        if (sukupuoli == 0) return KOIRAS;  
        if (sukupuoli == 1) return NAARAS;
        return EI_TIETOA;
    }
    
    
    /**
     * Asettaa havainnon sukupuolen
     * @param uusi sukupuoli
     */
    public void setSukupuoli(String uusi) {
        if (uusi.equalsIgnoreCase(KOIRAS)) sukupuoli = 0;  
        if (uusi.equalsIgnoreCase(NAARAS)) sukupuoli = 1;
        if (uusi.equalsIgnoreCase(EI_TIETOA)) sukupuoli = 2;
    }
    
    
    /**
     * asetetaan uusi laji-indeksi
     * @param uusi indeksi
     */
    public void setLajiId(int uusi) {
        lajiId = uusi;
    }

    
    /**
     * asetetaan uusi havaintoindeksi
     * @param uusi indeksi
     */
    public void setHavaintoId(int uusi) {
        havaintoId = uusi;
    }
    
    
    /**
     * Palauttaa havainnon tiedot merkkijonona
     * @return havainto tolppaeroteltuna merkkijonona
     */
    @Override
    public String toString() {
        return "" + havaintoId + "|" + lajiId + "|" + sukupuoli;
    }


    /**
     * Selvittää jäsenen tiedot rivistä.
     * @param rivi tiedoston rivi joka luetaan
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        havaintoId = Mjonot.erota(sb, '|', havaintoId);
        lajiId = Mjonot.erota(sb, '|', lajiId);
        sukupuoli = Mjonot.erota(sb, '|', sukupuoli);
    }
    
    
    /**
     * Tulostetaan havainnnon tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(havaintoId + " " + lajiId + " " + sukupuoli);
    }

    
    /**
     * Apumetodi, jolla saadaan täytettyä testitiedot havaintoihin
     * @param hid havaintoid
     */
    public void taytaHavaintotieto(int hid) {
        havaintoId = hid;
        lajiId = 1;
        sukupuoli = 0;
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Havaintotieto ht1 = new Havaintotieto();
        Havaintotieto ht2 = new Havaintotieto();
        ht1.tulosta(System.out);
        ht2.tulosta(System.out);
        
        ht1.taytaHavaintotieto(1);
        ht2.taytaHavaintotieto(2);
          
        ht1.tulosta(System.out);
        ht2.tulosta(System.out);
    }
}