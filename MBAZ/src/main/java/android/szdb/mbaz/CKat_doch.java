package android.szdb.mbaz;

/**
 * Klasa zarzadzajaca kategoriami dochodow
 *@author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 * @see android.szdb.mbaz.CDochody
 */
public class CKat_doch {
    private int KDOk_1_Id; //Primary Key
    private String KDO_Nazwa;

    /**
     * Getter
     * @return Klucz glowny
     */
    public int getKDOk_1_Id() {
        return KDOk_1_Id;
    }

    /**
     * Setter
     * @param kDOk_1_Id Klucz glowny
     */
    public void setKDOk_1_Id(int kDOk_1_Id) {
        KDOk_1_Id = kDOk_1_Id;
    }

    /**
     * Getter
     * @return Nazwa kategorii dochodow
     */
    public String getKDO_Nazwa() {
        return KDO_Nazwa;
    }

    /**
     * Setter
     * @param kDO_Nazwa Nazwa kategorii dochodow
     */
    public void setKDO_Nazwa(String kDO_Nazwa) {
        KDO_Nazwa = kDO_Nazwa;
    }

    /**
     * Przeciazaona metoda toString()
     * @return Sformatowany ciag znakow
     */
    public String toString(){
        return KDO_Nazwa;
    }
}
