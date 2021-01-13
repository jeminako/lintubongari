package fxPaaikkuna;

/**
 * @author Jemina Kotkajuuri
 * @version 21.4.2019
 * Rajapinta auttamaan hakemisen käsittelyssä
 */
public interface HakuehtoRajapinta {
    
    /**
     * @param i indeksi
     * @param haku haettu asia
     * @return toteutuuko ehto
     */
    public boolean ehto(int i, String haku);
}
