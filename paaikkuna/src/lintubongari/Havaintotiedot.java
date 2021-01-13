package lintubongari;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Jemina Kotkajuuri
 * @version 21.4.2019
 *
 */
public class Havaintotiedot {
    private static final int MAX_HAVAINTOTIEDOT = 10;
    private int              lkm            = 0;
    private String           tiedostonPerusNimi = "havaintotiedot";
    private Havaintotieto    alkiot[]       = new Havaintotieto[MAX_HAVAINTOTIEDOT];
    private boolean          muutettu       = false;
    
    
    /**
     * Muodostaja
     */
    public Havaintotiedot() {
        // Attribuutit alustettu alussa
    }
    
    
    /**
     * Lisää uuden havainnon lintubongariin
     * @param havaintotieto lisättävän havainnon viite
     * @throws SailoException jos tietorakenne on täynnä
     */
    public void lisaa(Havaintotieto havaintotieto) throws SailoException {
        if (lkm >= alkiot.length) {
            Havaintotieto uusi[] = new Havaintotieto[lkm*2];
            for (int i = 0; i<lkm; i++) {
                uusi[i] = alkiot[i];
            }
            alkiot = uusi;
        }
        alkiot[lkm] = havaintotieto;
        lkm++;
        muutettu = true;
    }
    
    
    /**
     * Poistaa havaintotiedon
     * @param indeksi poistettavan indeksi
     * @example
     * <pre name="test"> 
     * #THROWS SailoException  
     * Havaintotiedot h = new Havaintotiedot(); 
     * Havaintotieto h1 = new Havaintotieto(); Havaintotieto h2 = new Havaintotieto(); Havaintotieto h3 = new Havaintotieto();
     * h1.setHavaintoId(1); h2.setHavaintoId(2); h3.setHavaintoId(3);
     * h.lisaa(h1); h.lisaa(h2); h.lisaa(h3); 
     * h.getLkm() === 3;
     * h.poista(1);
     * h.getLkm() === 2;
     * </pre> 
     */
    public void poista(int indeksi) {
        lkm--; 
        for (int i = indeksi; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null; 
        muutettu = true; 
    }
    
    
    /**
     * Kertoo, montako kertaa lajia on käytetty
     * @param id lajiId
     * @return monta kertaa lajia käytetty
     */
    public int lajiaKaytetty(int id) {
        int maara = 0;
        for (int i = 0; i<lkm; i++) {
            if (alkiot[i].getLajiId() == id) maara++;
        }
        return maara;
    }
    
    
    /**
     * Asettaa muutetulle arvon
     */
    public void setMuutettu() {
        muutettu = true;
    }
    
    
    /**
     * Palauttaa havaintotietojen lukumäärän
     * @return havaintotietojen lukumäärän
     */
    public int getLkm() {
        return lkm;
    }
       
    
    /**
     * Lukee havainnot tiedostosta.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos ei toimi
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Havaintotiedot havaintotiedot = new Havaintotiedot();
     *  Havaintotieto h1 = new Havaintotieto(), h2 = new Havaintotieto();
     *  h1.taytaHavaintotieto(1); h2.taytaHavaintotieto(1);
     *  String hakemisto = "testi";
     *  String tiedNimi = hakemisto+"/havainnot";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  havaintotiedot.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  havaintotiedot.lisaa(h1);
     *  havaintotiedot.lisaa(h2);
     *  havaintotiedot.tallenna();
     *  havaintotiedot = new Havaintotiedot();  
     *  havaintotiedot.lueTiedostosta(tiedNimi);  
     *  havaintotiedot.anna(0).toString().equals(h1.toString()) === true;
     *  havaintotiedot.anna(1).toString().equals(h2.toString()) === true;
     *  havaintotiedot.lisaa(h2);
     *  havaintotiedot.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        setTiedostonPerusNimi(hakemisto);
        try ( Scanner s = new Scanner(new FileInputStream(getTiedostonNimi()))) {
            while (s.hasNext()) {
                String rivi = s.nextLine();
                rivi = rivi.trim();
                if ( "".equals(rivi) ) continue;
                Havaintotieto havaintotieto = new Havaintotieto();
                havaintotieto.parse(rivi);
                lisaa(havaintotieto);
            }
            muutettu = false;
        } catch (IOException e) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    
    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    
    /**
     * Asettaa tiedoston perusnimen
     * @param tiedostonNimi annettava nimi
     */
    public void setTiedostonPerusNimi(String tiedostonNimi) {
        tiedostonPerusNimi = tiedostonNimi;
    }
    
    
    /**
     * Palauttaa tiedoston nimen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    
    
    /**
     * Palauttaa tiedoston nimen tallennusta varten
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }
    
    
    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }
    
    
    /**
     * Tallentaa havainnot tiedostoon.
     * @throws SailoException jos ei toimi
     */
    public void tallenna() throws SailoException {
        if (!muutettu) return;
        
        File fbak = new File(getBakNimi());
        File ftiedosto = new File(getTiedostonNimi());
        fbak.delete();
        ftiedosto.renameTo(fbak);
        
        try (PrintWriter pw = new PrintWriter(ftiedosto.getCanonicalPath())) {
            pw.println();
            for (int i = 0; i < lkm; i++) {
                pw.println(alkiot[i].toString());
            }
        } catch (IOException e) {
            throw new SailoException("Tallentamisessa ongelmia: " + e.getMessage());
        }
        muutettu = false;
    }
    
    
    /**
     * Palauttaa viitteen i:teen havaintoon.
     * @param i monennenko havaintoon viite halutaan
     * @return viite havaintoon, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Havaintotiedot ht = new Havaintotiedot();
     * Havaintotieto h1 = new Havaintotieto();
     * Havaintotieto h2 = new Havaintotieto();
     * ht.lisaa(h1); ht.lisaa(h2);
     * ht.anna(0).equals(h1) === true;
     * </pre>  
     */
    public Havaintotieto anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Havaintotiedot havaintotiedot = new Havaintotiedot();

        Havaintotieto ht1 = new Havaintotieto(), ht2 = new Havaintotieto();
        ht1.taytaHavaintotieto(1);
        ht2.taytaHavaintotieto(2);

        try {
            havaintotiedot.lisaa(ht1);
            havaintotiedot.lisaa(ht2);

            System.out.println("========== Havaintotiedon testi ==============");

            for (int i=0; i<havaintotiedot.getLkm(); i++) {
                Havaintotieto havaintotieto = havaintotiedot.anna(i);
                System.out.println("Havaintotieto paikassa: " + i);
                havaintotieto.tulosta(System.out);
            }

        } catch ( SailoException ex ) {
            System.out.println(ex.getMessage());
        }
    }

}
