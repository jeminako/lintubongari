package lintubongari;

import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Jemina Kotkajuuri
 * @version 21.4.2019
 *
 */
public class Havainto {
    private int havaintoId;
    private static int seuraavaHid = 1;
    private String pvm;
    private String paikkakunta;
    
    
    /**
     * Liittää uuteen havaintoon seuraavan havaintotunnusluvun.
     * @return havainnnon uusi tunnusluku
     * @example
     * <pre name="test">
     * Havainto h1 = new Havainto();
     * h1.rekisteroi();
     * Havainto h2 = new Havainto();
     * h2.rekisteroi();
     * int n1 = h1.getId();
     * int n2 = h2.getId();
     * n1 === n2-1; 
     * </pre>
     */
    public int rekisteroi() {
        havaintoId = seuraavaHid;
        seuraavaHid++;
        return havaintoId;
    }
    
    
    /**
     * Asettaa havaintoId:n ja varmistaa että suuruusjärjestys säilyy
     * @param nro asetettava havaintoId
     */
    private void setHavaintoId(int nro) {
        havaintoId = nro;
        if (havaintoId >= seuraavaHid) {
            seuraavaHid = havaintoId + 1;
        }
    }
    
    
    /**
     * Palauttaa havainnon havaintotunnuksen.
     * @return havainnon tunnusluku
     */
    public int getId() {
        return havaintoId;
    }
    
    
    /**
     * @return havainnon päivämäärä
     */
    public String getPvm() {
        return pvm;
    }
    
    
    /**
     * @return havainnon paikkakunta
     */
    public String getPaikkakunta() {
        return paikkakunta;
    }
    
    
    /**
     * Palauttaa havainnon tiedot merkkijonona
     * @return havainto tolppaeroteltuna merkkijonona
     */
    @Override
    public String toString() {
        return "" + getId() + "|" + pvm + "|" + paikkakunta + "|";
    }


    /**
     * Selvittää jäsenen tiedot rivistä.
     * @param rivi tiedoston rivi joka luetaan
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setHavaintoId(Mjonot.erota(sb, '|', getId()));
        pvm = Mjonot.erota(sb, '|', pvm);
        paikkakunta = Mjonot.erota(sb, '|', paikkakunta);
    }
    
    
    /**
     * Tulostetaan havainnnon tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(havaintoId + " " + pvm + " " + paikkakunta);
    }

    
    /**
     * Apumetodi, jolla saadaan täytettyä testitiedot havaintoihin
     */
    public void taytaHavaintoH() {
        pvm = "10.01.2019";
        paikkakunta = "Jyväskylä";
    }
    
    
    /**
     * Asettaa uuden päivämäärän
     * @param uusi lisättävä päivämäärä
     */
    public void setPvm(String uusi) {
        pvm = uusi;
    }
    

    /**
     * Asettaa uuden paikkakunnan 
     * @param uusi lisättävä paikkakunta
     */
    public void setPaikkakunta(String uusi) {
        paikkakunta = uusi;
    }
    
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Havainto h1 = new Havainto();
        Havainto h2 = new Havainto();
        h1.rekisteroi();
        h2.rekisteroi();
        h1.tulosta(System.out);
        h2.tulosta(System.out);
        
        h1.taytaHavaintoH();
        h2.taytaHavaintoH();
          
        h1.tulosta(System.out);
        h2.tulosta(System.out);
         
    }

}
