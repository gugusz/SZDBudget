package android.szdb.mbaz;

/**
 * Klasa zarzadzajaca danymi odnosnie planowanie inwestycji
 * @author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 */
public class CPlanowanie {
    private int PLAk_1_Id; //Primary Key
    private String PLA_NazwaPrzedmiotu;
    private String PLA_Od;
    private String PLA_DataZakupu;
    private float PLA_Cena;

    /**
     * Getter
     * @return Primary Key
     */
    public int getPLAk_1_Id() {
        return PLAk_1_Id;
    }

    /**
     * Getter
     * @return Nazwa przedmiotu ktore planowanie jest do kupienia
     */
    public String getPLA_NazwaPrzedmiotu() {
        return PLA_NazwaPrzedmiotu;
    }

    /**
     * Getter
     * @return Data rozpoczecia oszczedzania
     */
    public String getPLA_Od() {
        return PLA_Od;
    }

    /**
     * Getter
     * @return Planowana data zakupu
     */
    public String getPLA_DataZakupu() {
        return PLA_DataZakupu;
    }

    /**
     * Setter
     * @param PLAk_1_Id Primary Key
     */
    public void setPLAk_1_Id(int PLAk_1_Id) {
        this.PLAk_1_Id = PLAk_1_Id;
    }

    /**
     * Setter
     * @param PLA_NazwaPrzedmiotu Nazwa przedmiotu ktore planowanie jest do kupienia
     */
    public void setPLA_NazwaPrzedmiotu(String PLA_NazwaPrzedmiotu) {
        this.PLA_NazwaPrzedmiotu = PLA_NazwaPrzedmiotu;
    }

    /**
     * Setter
     * @param PLA_Od Data rozpoczecia oszczedzania
     */
    public void setPLA_Od(String PLA_Od) {
        this.PLA_Od = PLA_Od;
    }

    /**
     * Setter
     * @param PLA_DataZakupu Planowana data zakupu
     */
    public void setPLA_DataZakupu(String PLA_DataZakupu) {
        this.PLA_DataZakupu = PLA_DataZakupu;
    }

    /**
     * Getter
     * @return Cena planowanego przedmitu do kupienia
     */
    public float getPLA_Cena() {
        return PLA_Cena;
    }

    /**
     * Setter
     * @param PLA_Cena Cena planowanego przedmitu do kupienia
     */
    public void setPLA_Cena(float PLA_Cena) {
        this.PLA_Cena = PLA_Cena;
    }

    /**
     * Przeciazna metoda toString()
     * @return sformatowany ciag znakow
     */
    public String toString(){
        return "Nazwa: " + PLA_NazwaPrzedmiotu + ", Od: " + PLA_Od + ", Do: " + PLA_DataZakupu + ", Cena: " + PLA_Cena;
    }
}