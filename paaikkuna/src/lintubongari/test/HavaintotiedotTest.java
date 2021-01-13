package lintubongari.test;
// Generated by ComTest BEGIN
import java.io.File;
import static org.junit.Assert.*;
import org.junit.*;
import lintubongari.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2019.04.23 10:10:54 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class HavaintotiedotTest {



  // Generated by ComTest BEGIN
  /** 
   * testPoista53 
   * @throws SailoException when error
   */
  @Test
  public void testPoista53() throws SailoException {    // Havaintotiedot: 53
    Havaintotiedot h = new Havaintotiedot(); 
    Havaintotieto h1 = new Havaintotieto(); Havaintotieto h2 = new Havaintotieto(); Havaintotieto h3 = new Havaintotieto(); 
    h1.setHavaintoId(1); h2.setHavaintoId(2); h3.setHavaintoId(3); 
    h.lisaa(h1); h.lisaa(h2); h.lisaa(h3); 
    assertEquals("From: Havaintotiedot line: 59", 3, h.getLkm()); 
    h.poista(1); 
    assertEquals("From: Havaintotiedot line: 61", 2, h.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta109 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta109() throws SailoException {    // Havaintotiedot: 109
    Havaintotiedot havaintotiedot = new Havaintotiedot(); 
    Havaintotieto h1 = new Havaintotieto(), h2 = new Havaintotieto(); 
    h1.taytaHavaintotieto(1); h2.taytaHavaintotieto(1); 
    String hakemisto = "testi"; 
    String tiedNimi = hakemisto+"/havainnot"; 
    File ftied = new File(tiedNimi+".dat"); 
    File dir = new File(hakemisto); 
    dir.mkdir(); 
    ftied.delete(); 
    try {
    havaintotiedot.lueTiedostosta(tiedNimi); 
    fail("Havaintotiedot: 122 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    havaintotiedot.lisaa(h1); 
    havaintotiedot.lisaa(h2); 
    havaintotiedot.tallenna(); 
    havaintotiedot = new Havaintotiedot(); 
    havaintotiedot.lueTiedostosta(tiedNimi); 
    assertEquals("From: Havaintotiedot line: 128", true, havaintotiedot.anna(0).toString().equals(h1.toString())); 
    assertEquals("From: Havaintotiedot line: 129", true, havaintotiedot.anna(1).toString().equals(h2.toString())); 
    havaintotiedot.lisaa(h2); 
    havaintotiedot.tallenna(); 
    assertEquals("From: Havaintotiedot line: 132", true, ftied.delete()); 
    File fbak = new File(tiedNimi+".bak"); 
    assertEquals("From: Havaintotiedot line: 134", true, fbak.delete()); 
    assertEquals("From: Havaintotiedot line: 135", true, dir.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testAnna231 
   * @throws SailoException when error
   */
  @Test
  public void testAnna231() throws SailoException {    // Havaintotiedot: 231
    Havaintotiedot ht = new Havaintotiedot(); 
    Havaintotieto h1 = new Havaintotieto(); 
    Havaintotieto h2 = new Havaintotieto(); 
    ht.lisaa(h1); ht.lisaa(h2); 
    assertEquals("From: Havaintotiedot line: 237", true, ht.anna(0).equals(h1)); 
  } // Generated by ComTest END
}