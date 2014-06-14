package android.szdb.mbaz;

/**
 * Klasa zarzadzajaca kategoriami wydatkow
 * @author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 * @see android.szdb.mbaz.CWydatki
 */
public class CKat_wyd {
    private int KWYk_1_Id; //Primary Key
    private String KWY_Nazwa;

    /**
     * Getter
     * @return Primary key
     */
    public int getKWYk_1_Id() {
        return KWYk_1_Id;
    }

    /**
     * Setter
     * @param kWYk_1_Id Primary Key
     */
    public void setKWYk_1_Id(int kWYk_1_Id) {
        KWYk_1_Id = kWYk_1_Id;
    }

    /**
     * Getter
     * @return Nazwa kategorii wydatkow
     */
    public String getKWY_Nazwa() {
        return KWY_Nazwa;
    }

    /**
     * Setter
     * @param kWY_Nazwa Nazwa kategorii wydatkow
     */
    public void setKWY_Nazwa(String kWY_Nazwa) {
        KWY_Nazwa = kWY_Nazwa;
    }

    /**
     * Przeciazana metoda toString()
     * @return sformatowany ciag znakow
     */
    public String toString() {
        return KWY_Nazwa;
    }
}
