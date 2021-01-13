package lintubongari;

import java.io.File;

/**
 * @author Jemina Kotkajuuri
 * @version 21.4.2019
 *
 */
public class Lintubongari {
    private Havainnot havainnot = new Havainnot();
    private Lajit lajit = new Lajit();
    private Havaintotiedot havaintotiedot = new Havaintotiedot();

 
    /**
     * Palauttaa annetun havaintokerran laji- ja sukupuolitiedot (havaintotiedot)
     * @param hid kyseisen havainnon id
     * @return kyseisen havainnon muut havaintotiedot
     * <pre name="test">
     * #THROWS SailoException
     * #import java.util.*; 
     * Lintubongari lintubongari = new Lintubongari();
     * Havaintotieto ht = new Havaintotieto(); Havaintotieto ht2 = new Havaintotieto();
     * ht.taytaHavaintotieto(1); ht2.taytaHavaintotieto(2);
     * lintubongari.lisaa(ht); lintubongari.lisaa(ht2);
     * Havaintotieto havainnonTiedot = lintubongari.haeHavainnonTiedot(1);
     * havainnonTiedot.equals(ht) === true;
     * </pre>    
     */
    public Havaintotieto haeHavainnonTiedot(int hid) {
        for (int i = 0; i<getHavaintotietoja(); i++) {
            Havaintotieto tutkittava = havaintotiedot.anna(i);
            if ( hid == tutkittava.getHavaintoId() ) {
                return tutkittava;
            }
        }
        return null;
    }
    
    
    /**
     * Asettaa muutetulle arvon
     */
    public void setMuutettu() {
        lajit.setMuutettu();
        havainnot.setMuutettu();
        havaintotiedot.setMuutettu();
    }
    
    
    /**
     * Palautaa lintubongarin havaintojen määrän
     * @return havaintojen määrä
     */
    public int getHavaintoja() {
        return havainnot.getLkm();
    }
    
    
    /**
     * Palautaa lintubongarin lajien määrän
     * @return lajien määrä
     */
    public int getLajeja() {
        return lajit.getLkm();
    }
    

    /**
     * Antaa lajin nimien
     * @param id tunnusluku, jonka perusteella haetaan
     * @return laji
     */
    public String lajinNimi(int id) {
        return lajit.lajinNimi(id);
    }
    
    
    /**
     * Palautaa lintubongarin havaintotietojen määrän
     * @return havaintotietojen määrä
     */
    public int getHavaintotietoja() {
        return havaintotiedot.getLkm();
    }
    
    
    /**
     * @param havainto lisattava havainto
     * @throws SailoException virhe kun lisäys ei onnistu
     */
    public void lisaa(Havainto havainto) throws SailoException {
        havainnot.lisaa(havainto);
    }
    
    
    /**
     * @param laji lisattava laji
     * @throws SailoException virhe kun lisäys ei onnistu
     */
    public void lisaa(Laji laji) throws SailoException {
        lajit.lisaa(laji);
    }
    
    
    /**
     * @param havaintotieto lisattava havaintotieto
     * @throws SailoException virhe kun lisäys ei onnistu
     */
    public void lisaa(Havaintotieto havaintotieto) throws SailoException {
        havaintotiedot.lisaa(havaintotieto);
    }
    
    
    /**
     * Poistaa havainnnoista sen, jolla on nro.
     * @param nro viitenumero, jonka mukaan poistetaan
     * @example
     * <pre name="test">
     * #THROWS SailoException
     *   Lintubongari lb = new Lintubongari();
     *   Havainto h1 = new Havainto(); Havainto h2 = new Havainto();
     *   h1.rekisteroi(); h2.rekisteroi();
     *   Laji l1 = new Laji(); Laji l2 = new Laji();
     *   l1.rekisteroi(); l2.rekisteroi();
     *   Havaintotieto ht1 = new Havaintotieto(); Havaintotieto ht2 = new Havaintotieto();
     *   ht1.taytaHavaintotieto(1); ht2.setLajiId(2); ht2.setHavaintoId(2);
     *   lb.lisaa(h1); lb.lisaa(h2); lb.lisaa(l1); lb.lisaa(l2); lb.lisaa(ht1); lb.lisaa(ht2); 
     *   lb.getLajeja() === 2;
     *   lb.getHavaintoja() === 2;
     *   lb.getHavaintotietoja() === 2;
     *   lb.poista(0);
     *   lb.getLajeja() === 1;
     *   lb.getHavaintoja() === 1;
     *   lb.getHavaintotietoja() === 1;
     * </pre>
     */
    public void poista(int nro) {
        Havaintotieto ht = havaintotiedot.anna(nro);
        int lajiId = ht.getLajiId();
        if (havaintotiedot.lajiaKaytetty(lajiId) <= 1) {
            lajit.poista(lajiId);
        }
        havainnot.poista(nro);
        havaintotiedot.poista(nro);
        setMuutettu();
    }
    
    
    /**
     * Poistaa lajin annetun lajiId:n perusteella
     * @param lajiId annettu id
     */
    public void poistaLaji(int lajiId) {
        lajit.poista(lajiId);
    }
    
    
    /**
     * Palauttaa lajin id:tä vastaavan lajin.
     * @param id lajitunnus, jonka perusteella haetaan
     * @return laji tekstinä
     */
    public int lajiaKaytetty(int id) {
        return havaintotiedot.lajiaKaytetty(id);
    }
    
    
    /**
     * Palauttaa i:n havainnon
     * @param i monesko havainto palautetaan
     * @return viite i:teen havaintoon
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Havainto annaHavainto(int i) throws IndexOutOfBoundsException {
        return havainnot.anna(i);
    }
    
    
    /**
     * Palauttaa i:n lajin
     * @param i monesko laji palautetaan
     * @return viite i:teen lajiin
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Laji annaLaji(int i) throws IndexOutOfBoundsException {
        return lajit.get(i);
    }
    
    
    /**
     * Käy läpi lajilistan ja kertoo, löytyykö etsittävää listasta
     * @param etsittava etsittävä laji
     * @return etsityn lajin indeksi tai -1 jos ei löydy
     */
    public int loytyyko(String etsittava) {
        return lajit.loytyyko(etsittava);
    }
    
    
    /**
     * Palauttaa i:n havainnon
     * @param i monesko havainto palautetaan
     * @return viite i:teen havaintoon
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Havaintotieto annaHavaintotieto(int i) throws IndexOutOfBoundsException {
        return havaintotiedot.anna(i);
    }
    
    
    /**
     * Lukee bongarin tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen ei onnistu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.*;
     * #import java.util.*;
     * 
     *  Lintubongari lintubongari = new Lintubongari();
     *  
     *  Havainto h1 = new Havainto(); h1.taytaHavaintoH(); h1.rekisteroi();
     *  Havainto h2 = new Havainto(); h2.taytaHavaintoH(); h2.rekisteroi();
     *  Laji l1 = new Laji(); l1.taytaLaji(); l1.rekisteroi();
     *  Laji l2 = new Laji(); l2.taytaLaji(); l2.rekisteroi();
     *  Havaintotieto ht1 = new Havaintotieto();
     *  Havaintotieto ht2 = new Havaintotieto();
     *  ht1.taytaHavaintotieto(h1.getId()); ht2.taytaHavaintotieto(h2.getId());
     *   
     *  String hakemisto = "testi";
     *  File dir = new File(hakemisto);
     *  File ftied  = new File(hakemisto+"/havainnot.dat");
     *  File fltied = new File(hakemisto+"/lajit.dat");
     *  File fhttied = new File(hakemisto+"/havaintotiedot.dat");
     *  dir.mkdir();  
     *  ftied.delete();
     *  fltied.delete();
     *  fhttied.delete();
     *  lintubongari.lueTiedostostaHavainnot(hakemisto); #THROWS SailoException
     *  lintubongari.lisaa(h1);
     *  lintubongari.lisaa(h2);
     *  lintubongari.lisaa(l1);
     *  lintubongari.lisaa(l2);
     *  lintubongari.lisaa(ht1);
     *  lintubongari.lisaa(ht2);
     *  lintubongari.tallenna();
     *  lintubongari = new Lintubongari();
     *  lintubongari.lueTiedostostaHavainnot(hakemisto);
     *  lintubongari.annaHavainto(0).toString().equals(h1.toString());
     *  lintubongari.annaHavaintotieto(0).toString().equals(ht1.toString());
     *  lintubongari.annaHavainto(1).toString().equals(h2.toString());
     *  lintubongari.annaHavaintotieto(1).toString().equals(ht2.toString());
     *  lintubongari.annaLaji(0).toString().equals(l1.toString());
     *  lintubongari.annaLaji(1).toString().equals(l2.toString());
     *  lintubongari.lisaa(l2);
     *  lintubongari.lisaa(ht2);
     *  lintubongari.lisaa(h2);
     *  lintubongari.tallenna();
     *  ftied.delete()  === true;
     *  fltied.delete()  === true;
     *  fhttied.delete() === true;
     *  File fbak = new File(hakemisto+"/havainnot.bak");
     *  File flbak = new File(hakemisto+"/lajit.bak");
     *  File fhtbak = new File(hakemisto+"/havaintotiedot.bak");
     *  fbak.delete() === true;
     *  flbak.delete() === true;
     *  fhtbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostostaHavainnot(String nimi) throws SailoException {
        havainnot = new Havainnot();
        lajit = new Lajit();
        havaintotiedot = new Havaintotiedot();
        
        setTiedosto(nimi);
        
        havainnot.lueTiedostosta();
        lajit.lueTiedostosta();
        havaintotiedot.lueTiedostosta();
    }

    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File hakemisto = new File(nimi);
        hakemisto.mkdirs();
        String hakemistonNimi = "";
        if (!nimi.isEmpty()) hakemistonNimi = nimi + "/";
        havainnot.setTiedostonPerusNimi(hakemistonNimi + "havainnot");
        lajit.setTiedostonPerusNimi(hakemistonNimi + "lajit");
        havaintotiedot.setTiedostonPerusNimi(hakemistonNimi + "havaintotiedot");
    }
    
    
    /**
     * Tallettaa lintubongarin tiedot tiedostoon
     * @throws SailoException jos ongelmia tallentamisessa
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            havainnot.tallenna();
        } catch (SailoException e) {
            virhe = e.getMessage();
        }
        
        try {
            lajit.tallenna();
        } catch (SailoException e) {
            virhe = e.getMessage();
        }
        
        try {
            havaintotiedot.tallenna();
        } catch (SailoException e) {
            virhe += e.getMessage();
        }
        if (!"".equals(virhe)) throw new SailoException(virhe);
        
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Lintubongari lintubongari = new Lintubongari();

        try {
            lintubongari.setTiedosto("jemina");
            lintubongari.tallenna();

            Havainto h1 = new Havainto(), h2 = new Havainto();
            h1.rekisteroi();
            h1.taytaHavaintoH();
            h2.rekisteroi();
            h2.taytaHavaintoH();

            lintubongari.lisaa(h1);
            lintubongari.lisaa(h2);

            System.out.println("============= Lintubongarin testi =================");

            for (int i = 0; i < lintubongari.getHavaintoja(); i++) {
                Havainto havainto = lintubongari.annaHavainto(i);
                System.out.println("Havainto paikassa: " + i);
                havainto.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}