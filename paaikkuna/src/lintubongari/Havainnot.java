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
public class Havainnot {
    private static final int MAX_HAVAINTOJA      = 10;
    private int              lkm                 = 0;
    private Havainto         alkiot[]            = new Havainto[MAX_HAVAINTOJA];
    private boolean          muutettu            = false;
    private String           tiedostonPerusNimi  = "havainnot";
    
    
    /**
     * Muodostaja
     */
    public Havainnot() {
        // Attribuutit alustettu alussa
    }
    
    
    /**
     * Lisää uuden havainnon lintubongariin
     * @param havainto lisättävän havainnon viite
     * @throws SailoException jos tietorakenne on täynnä
     */
    public void lisaa(Havainto havainto) throws SailoException {
        if (lkm >= alkiot.length) {
            Havainto uusi[] = new Havainto[lkm*2];
            for (int i = 0; i<lkm; i++) {
                uusi[i] = alkiot[i];
            }
            alkiot = uusi;
        }
        alkiot[lkm] = havainto;
        lkm++;
        muutettu = true;
    }
    
    
    /**
     * Poistaa havainnon
     * @param indeksi poistettavan indeksi
     * @example
     * <pre name="test"> 
     * #THROWS SailoException  
     * Havainnot havainnot = new Havainnot(); 
     * Havainto h1 = new Havainto(); Havainto h2 = new Havainto(); Havainto h3 = new Havainto(); 
     * h1.rekisteroi(); h2.rekisteroi(); h3.rekisteroi();
     * int id = h1.getId();
     * havainnot.lisaa(h1); havainnot.lisaa(h2); havainnot.lisaa(h3); 
     * havainnot.getLkm() === 3;
     * havainnot.poista(id);
     * havainnot.getLkm() === 2;
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
     * Asettaa muutetulle arvon
     */
    public void setMuutettu() {
        muutettu = true;
    }
    
    
    /**
     * Palauttaa havaintojen lukumäärän
     * @return havaintojen lukumäärän
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
     *  Havainnot havainnot = new Havainnot();
     *  Havainto h1 = new Havainto(), h2 = new Havainto();
     *  h1.rekisteroi(); h2.rekisteroi();
     *  h1.taytaHavaintoH(); h2.taytaHavaintoH();
     *  String hakemisto = "testi";
     *  String tiedNimi = hakemisto+"/havainnot";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  havainnot.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  havainnot.lisaa(h1);
     *  havainnot.lisaa(h2);
     *  havainnot.tallenna();
     *  havainnot = new Havainnot();  
     *  havainnot.lueTiedostosta(tiedNimi);  
     *  havainnot.anna(0).toString().equals(h1.toString()) === true;
     *  havainnot.anna(1).toString().equals(h2.toString()) === true;
     *  havainnot.lisaa(h2);
     *  havainnot.tallenna();
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
                Havainto havainto = new Havainto();
                havainto.parse(rivi);
                lisaa(havainto);
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
     * Tallentaa havainnot tiedostoon..
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
     * Havainnot h = new Havainnot();
     * Havainto h1 = new Havainto();
     * Havainto h2 = new Havainto();
     * h.lisaa(h1); h.lisaa(h2);
     * h.anna(0).equals(h1) === true;
     * h.anna(1).equals(h2) === true;
     * </pre>    
     */
    public Havainto anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    
    /**
     * @param args ei käytössä
     * @throws SailoException  virhe
     */
    public static void main(String[] args) throws SailoException {
        Havainnot havainnot = new Havainnot();

        Havainto h1 = new Havainto(), h2 = new Havainto();
        h1.rekisteroi();
        h1.taytaHavaintoH();
        h2.rekisteroi();
        h2.taytaHavaintoH();

        try {
            havainnot.lisaa(h1);
            havainnot.lisaa(h2);

            System.out.println("========== Havainnon testi ==============");

            for (int i=0; i<havainnot.getLkm(); i++) {
                Havainto havainto = havainnot.anna(i);
                System.out.println("Havainto nro: " + i);
                havainto.tulosta(System.out);
            }

        } catch ( SailoException ex ) {
            System.out.println(ex.getMessage());
        }
    }
}