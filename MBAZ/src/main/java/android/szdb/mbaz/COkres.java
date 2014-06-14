package android.szdb.mbaz;

/**
 * Klasa zarzadajaca okresami rozliczeniowymi
 * @author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 */
public class COkres {
    private int OKRk_1_Id; //Primary Key
    private String OKR_Od;
    private String OKR_Do;

    //gettery i settery

    /**
     * Getter
     * @return Primary key
     */
    public int getOKRk_1_Id() {
        return OKRk_1_Id;
    }

    /**
     * Setter
     * @param oKRk_1_Id Primary key
     */
    public void setOKRk_1_Id(int oKRk_1_Id) {
        OKRk_1_Id = oKRk_1_Id;
    }

    /**
     * Getter
     * @return Data rozpoczecia okresu rozliczeniowego
     */
    public String getOKR_Od() {
        return OKR_Od;
    }

    /**
     * Setter
     * @param oKR_Od Data rozpoczecia okresu rozliczeniowego
     */
    public void setOKR_Od(String oKR_Od) {
        OKR_Od = oKR_Od;
    }

    /**
     * Getter
     * @return Data zakonczenia okresu rozliczeniowego
     */
    public String getOKR_Do() {
        return OKR_Do;
    }

    /**
     * Setter
     * @param oKR_Do Data zakonczenia okresu rozliczeniowego
     */
    public void setOKR_Do(String oKR_Do) {
        OKR_Do = oKR_Do;
    }

    //przeciażenie metody toString()

    /**
     * Przeciazana metoda toString
     * @return sformatowy ciag znakow
     */
    public String toString(){
        return "OD: " + OKR_Od + " DO: " + OKR_Do;
    }
}
