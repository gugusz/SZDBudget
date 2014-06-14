package android.szdb.mbaz;

/**
 * Klasa do zarzadzania danymi Wydatkow
 * @author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 */
public class CWydatki {
    private int WYDk_1_Id; //Primary Key
    private float WYD_Kwota;
    private String WYD_Data;
    private int KWY_Id; //Foreign Key do Kategorie Wydatków
    private int SUB_Id; //Foreign Key do Subkategoria

    /**
     * Getter
     * @return priamry Key
     */
    public int getWYDk_1_Id() {
        return WYDk_1_Id;
    }

    /**
     * Setter
     * @param wYDk_1_Id Priamry Key
     */
    public void setWYDk_1_Id(int wYDk_1_Id) {
        WYDk_1_Id = wYDk_1_Id;
    }

    /**
     * Getter
     * @return Kwota jaka zostala wydana
     */
    public float getWYD_Kwota() {
        return WYD_Kwota;
    }

    /**
     * Setter
     * @param wYD_Kwota Kwota jaka zostala wydana
     */
    public void setWYD_Kwota(float wYD_Kwota) {
        WYD_Kwota = wYD_Kwota;
    }

    /**
     * Getter
     * @return Data kiedy dokonano wydatek
     */
    public String getWYD_Data() {
        return WYD_Data;
    }

    /**
     * Setter
     * @param wYD_Data Data kiedy dokonao wydatek
     */
    public void setWYD_Data(String wYD_Data) {
        WYD_Data = wYD_Data;
    }

    /**
     * Getter
     * @return Klucz obcy do tabeli KAT_WYD
     */
    public int getKWY_Id() {
        return KWY_Id;
    }

    /**
     * Setter
     * @param kWY_Id Klucz obcy do tabeli KAT_WYD
     */
    public void setKWY_Id(int kWY_Id) {
        KWY_Id = kWY_Id;
    }

    /**
     * Getter
     * @return Klucz obcy do tabeli SUBKATEGORIA
     */
    public int getSUB_Id() {
        return SUB_Id;
    }

    /**
     * Setter
     * @param sUB_Id Klucz obcy do tabeli SUBKATEGORIA
     */
    public void setSUB_Id(int sUB_Id) {
        SUB_Id = sUB_Id;
    }

    /**
     * Przeciazana metoda toString()
     * @return sformatowany ciag znakow
     */
    public String toString() {
        return "Wydano " + WYD_Kwota + " Dnia " + WYD_Data + " Kategorii " + KWY_Id + " Subkategorii " + SUB_Id;

    }
}
