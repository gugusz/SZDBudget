package android.szdb.mbaz;

/**
 * Klasa zarzadzajaca dochodami
 * @author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 */
public class CDochody {
    private int DOCk_1_Id; //Primary Key
    private float DOC_Kwota;
    private String DOC_Data;
    private int KDO_Id; //Foreign Key do Kategorie Dochodów

    /**
     * Getter
     * @return Primary Key tabeli DOCHODY
     */
    public int getDOCk_1_Id() {
        return DOCk_1_Id;
    }

    /**
     * Setter
     * @param dOCk_1_Id Primary Key tabeli DOCHODY
     */
    public void setDOCk_1_Id(int dOCk_1_Id) {
        DOCk_1_Id = dOCk_1_Id;
    }

    /**
     * Getter
     * @return Kwota dochodu
     */
    public float getDOC_Kwota() {
        return DOC_Kwota;
    }

    /**
     * Setter
     * @param dOC_Kwota Kwota dochodu
     */
    public void setDOC_Kwota(float dOC_Kwota) {
        DOC_Kwota = dOC_Kwota;
    }

    /**
     * Getter
     * @return Data otrzymania dochodu
     */
    public String getDOC_Data() {
        return DOC_Data;
    }

    /**
     * Setter
     * @param dOC_Data Data otrzymania dochodu
     */
    public void setDOC_Data(String dOC_Data) {
        DOC_Data = dOC_Data;
    }

    /**
     * Getter
     * @return Klucz obcy do tabeli KATEGORIE_DOCHODOW
     */
    public int getKDO_Id() {
        return KDO_Id;
    }

    /**
     * Setter
     * @param kDO_Id Klucz obcy do tabeli KATEGORIE_DOCHODOW
     */
    public void setKDO_Id(int kDO_Id) {
        KDO_Id = kDO_Id;
    }

    /**
     * Przeciazana metoda toString()
     * @return Sformatowany lancuch znakow
     */
    public String toString() {
        return "Dodano " + DOC_Kwota + "zł, Dnia " + DOC_Data;
    }
}
