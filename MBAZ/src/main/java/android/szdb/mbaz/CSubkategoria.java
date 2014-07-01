package android.szdb.mbaz;

/**
 * Klasa zarzadzajaca danymi subkategorii wydatkow
 * @author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 */
public class CSubkategoria {
    private int SUBk_1_Id; //Primary Key
    private String SUB_Nazwa;

    /**
     * Getter
     * @return Priary Key
     */
    public int getSUBk_1_Id() {
        return SUBk_1_Id;
    }

    /**
     * Setter
     * @param sUBk_1_Id Priamry Key
     */
    public void setSUBk_1_Id(int sUBk_1_Id) {
        SUBk_1_Id = sUBk_1_Id;
    }

    /**
     * Getter
     * @return Nazwa subkategorii wydatkow
     */
    public String getSUB_Nazwa() {
        return SUB_Nazwa;
    }

    /**
     * Setter
     * @param sUB_Nazwa Nazwa subkategorii wydatkow
     */
    public void setSUB_Nazwa(String sUB_Nazwa) {
        SUB_Nazwa = sUB_Nazwa;
    }

    /**
     * Przeciazana metoda toString()
     * @return sformatowany ciag znakow
     */
    public String toString() {
        return SUB_Nazwa;
    }
}
