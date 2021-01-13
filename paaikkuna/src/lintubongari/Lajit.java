package lintubongari;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author Jemina Kotkajuuri
 * @version 21.4.2019
 *
 */
public class Lajit {
    private String tiedostonPerusNimi = "lajit";
    private boolean muutettu = false;
    private final ArrayList<Laji> alkiot = new ArrayList<Laji>();
    
    
    /**
     * Lajien konstruktori
     */
    public Lajit() {
        //
    }
    
    
    /**
     * Asettaa muutetulle arvon
     */
    public void setMuutettu() {
        muutettu = true;
    }
    
    
    /**
     * Palauttaa lajin id:tä vastaavan lajin.
     * @param id lajitunnus, jonka perusteella haetaan
     * @return laji tekstinä
     */
    public String lajinNimi(int id) {
        for (int i = 0; i<alkiot.size(); i++) {
            if ( alkiot.get(i).getId() == id ) {
                return alkiot.get(i).getLaji();
            }
        }
        return null;
    }
    
    
    /**
     * Lisää uuden lajin.
     * @param l lisättävä laji
     */
    public void lisaa(Laji l) {
        alkiot.add(l);
        muutettu = true;
    }
    
    
    /**
     * Poistaa lajin annetun lajiId:n perusteella
     * @param lajiId annettu id
     * @example
     * <pre name="test"> 
     * #THROWS SailoException  
     * Lajit lajit = new Lajit(); 
     * Laji l1 = new Laji(); Laji l2 = new Laji(); Laji l3 = new Laji(); 
     * l1.rekisteroi(); l2.rekisteroi(); l3.rekisteroi();
     * int id = l1.getId();
     * lajit.lisaa(l1); lajit.lisaa(l2); lajit.lisaa(l3); 
     * lajit.getLkm() === 3;
     * lajit.poista(id);
     * lajit.getLkm() === 2;
     * </pre> 
     */
    public void poista(int lajiId) {
        for (int i = 0; i<alkiot.size(); i++) {
            if (lajiId == alkiot.get(i).getId()) {
                alkiot.remove(i);
                i--;
            }
        }
    }
    
    
    /**
     * Lukee lajit tiedostosta.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos ei toimi
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Lajit lajit = new Lajit();
     *  Laji l1 = new Laji(), l2 = new Laji();
     *  l1.rekisteroi(); l2.rekisteroi();
     *  l1.taytaLaji(); l2.taytaLaji();
     *  String hakemisto = "testi";
     *  String tiedNimi = hakemisto+"/havainnot";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  lajit.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  lajit.lisaa(l1);
     *  lajit.lisaa(l2);
     *  lajit.tallenna();
     *  lajit = new Lajit();  
     *  lajit.lueTiedostosta(tiedNimi);  
     *  lajit.get(0).toString().equals(l1.toString()) === true;
     *  lajit.get(1).toString().equals(l2.toString()) === true;
     *  lajit.lisaa(l2);
     *  lajit.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        alkiot.clear();
        setTiedostonPerusNimi(hakemisto);
        try ( Scanner s = new Scanner(new FileInputStream(getTiedostonNimi()))) {
            while (s.hasNext()) {
                String rivi = s.nextLine();
                rivi = rivi.trim();
                if ( "".equals(rivi) ) continue;
                Laji laji = new Laji();
                laji.parse(rivi);
                lisaa(laji);
            }
            muutettu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedostoa ei löydy!");
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
     * Tallentaa lajit tiedostoon.
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
            for (int i = 0; i < alkiot.size(); i++) {
                pw.println(alkiot.get(i).toString());
            }
        } catch (IOException e) {
            throw new SailoException("Tallentamisessa ongelmia: " + e.getMessage());
        }
        muutettu = false;
    }
    
    
    /**
     * Antaa lajien lukumäärän
     * @return lajien määrä
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    
    /**
     * Palauttaa halutut lajit
     * @param id etsittävä laji
     * @return halutut lajit
     * @example
     * <pre name="test">
     * Lajit lajit = new Lajit();
     * Laji l = new Laji();
     * l.rekisteroi(); l.taytaLaji(); lajit.lisaa(l);
     * l.equals(lajit.anna(1)) === true;
     * </pre>
     */
    public Laji anna(int id) {
        for (Laji l : alkiot) {
            if (l.getId() == id) return l;
        }
        return null;
    }
    
    
    /**
     * Palauttaa lajin sijainnin indeksin mukaan
     * @param i sijainti
     * @return haluttu laji
     */
    public Laji get(int i) {
        return alkiot.get(i);
    }
    
    
    /**
     * Käy läpi lajilistan ja kertoo, löytyykö etsittävää listasta
     * @param etsittava etsittävä laji
     * @return etsityn lajin indeksi tai -1 jos ei löydy
     */
    public int loytyyko(String etsittava) {
        for (int i = 0; i<alkiot.size(); i++) {
            if ( alkiot.get(i).getLaji().equalsIgnoreCase(etsittava) )  return i;
        }
        return -1;
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Lajit lajitiedot = new Lajit();
        Laji l1 = new Laji();
        l1.rekisteroi(); l1.taytaLaji(); 
        Laji l2 = new Laji();
        l2.rekisteroi(); l2.taytaLaji();
        Laji l3 = new Laji();
        l3.rekisteroi(); l3.taytaLaji();
        Laji l4 = new Laji();
        l4.rekisteroi(); l4.taytaLaji();
    
        lajitiedot.lisaa(l1);
        lajitiedot.lisaa(l2);
        lajitiedot.lisaa(l3);
        lajitiedot.lisaa(l2);
        lajitiedot.lisaa(l4);
    
        System.out.println("============= Lajien testi =================");
        for (int i = 1; i<lajitiedot.getLkm(); i++) {
            lajitiedot.anna(i).tulosta(System.out);
        }
    }
}
