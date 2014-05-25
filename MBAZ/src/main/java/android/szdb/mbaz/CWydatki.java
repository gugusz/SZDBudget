package android.szdb.mbaz;

public class CWydatki {
    private int WYDk_1_Id; //Primary Key
    private int WYD_Kwota;
    private int WYD_Data;
    private int KWY_Id; //Foreign Key do Kategorie Wydatków
    private int SUB_Id; //Foreign Key do Subkategoria
    private int OKR_Id; //Foreign Key do Okres

    public int getWYDk_1_Id() {
        return WYDk_1_Id;
    }
    public void setWYDk_1_Id(int wYDk_1_Id) {
        WYDk_1_Id = wYDk_1_Id;
    }
    public int getWYD_Kwota() {
        return WYD_Kwota;
    }
    public void setWYD_Kwota(int wYD_Kwota) {
        WYD_Kwota = wYD_Kwota;
    }
    public int getWYD_Data() {
        return WYD_Data;
    }
    public void setWYD_Data(int wYD_Data) {
        WYD_Data = wYD_Data;
    }
    public int getKWY_Id() {
        return KWY_Id;
    }
    public void setKWY_Id(int kWY_Id) {
        KWY_Id = kWY_Id;
    }
    public int getSUB_Id() {
        return SUB_Id;
    }
    public void setSUB_Id(int sUB_Id) {
        SUB_Id = sUB_Id;
    }
    public int getOKR_Id() {
        return OKR_Id;
    }
    public void setOKR_Id(int oKR_Id) {
        OKR_Id = oKR_Id;
    }
}
