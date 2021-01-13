package lintubongari;

import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Jemina Kotkajuuri
 * @version 21.4.2019
 *
 */
public class Laji {
    private int lajiId;
    private static int seuraavaId = 1;
    private String laji;

    
    /**
     * Liittää uuteen havaintotietokokonaisuuteen seuraavan havaintotietotunnusluvun.
     * @return havaintotiedon uusi tunnusluku
     * @example
     * <pre name="test">
     * Laji l1 = new Laji();
     * l1.rekisteroi();
     * Laji l2 = new Laji();
     * l2.rekisteroi();
     * int n1 = l1.getId();
     * int n2 = l2.getId();
     * n1 === n2-1; 
     * </pre>
     */
    public int rekisteroi() {
        lajiId = seuraavaId;
        seuraavaId++;
        return lajiId;
    }
    
    
    /**
     * Asettaa lajiId:n ja varmistaa että suuruusjärjestys säilyy
     * @param nro asetettava lajiId
     */
    private void setLajiId(int nro) {
        lajiId = nro;
        if (lajiId >= seuraavaId) {
            seuraavaId = lajiId + 1;
        }
    }
    
    
    /**
     * Palauttaa lajin tunnuksen.
     * @return lajin tunnusluvun
     */
    public int getId() {
        return lajiId;
    }

    
    /**
     * Palauttaa lajin tekstinä
     * @return laji
     */
    public String getLaji() {
        return laji;
    }
    
    
    /**
     * Palauttaa havainnon tiedot merkkijonona
     * @return havainto tolppaeroteltuna merkkijonona
     */
    @Override
    public String toString() {
        return "" + getId() + "|" + laji;
    }


    /**
     * Selvittää jäsenen tiedot rivistä.
     * @param rivi tiedoston rivi joka luetaan
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setLajiId(Mjonot.erota(sb, '|', getId()));
        laji = Mjonot.erota(sb, '|', laji);
    }
    
    
    /**
     * Tulostetaan havainnnon tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(lajiId + " " + laji);
    }

    
    /**
     * Apumetodi, jolla saadaan täytettyä testitiedot havaintoihin
     */
    public void taytaLaji() {
        laji = "Talitiainen";
    }
    

    /**
     * Asettaa uuden lajin
     * @param uusi uusi laji
     */
    public void setLaji(String uusi) {
        laji = uusi;
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Laji l1 = new Laji();
        Laji l2 = new Laji();
        l1.rekisteroi();
        l2.rekisteroi();
        l1.tulosta(System.out);
        l2.tulosta(System.out);
        
        l1.taytaLaji();
        l2.taytaLaji();
          
        l1.tulosta(System.out);
        l2.tulosta(System.out);
    }
}
