package android.szdb.mbaz;

public class CWydatki {
    private int WYDk_1_Id; //Primary Key
    private float WYD_Kwota;
    private String WYD_Data;
    private int KWY_Id; //Foreign Key do Kategorie Wydatków
    private int SUB_Id; //Foreign Key do Subkategoria

    public int getWYDk_1_Id() {
        return WYDk_1_Id;
    }
    public void setWYDk_1_Id(int wYDk_1_Id) {
        WYDk_1_Id = wYDk_1_Id;
    }
    public float getWYD_Kwota() {
        return WYD_Kwota;
    }
    public void setWYD_Kwota(float wYD_Kwota) {
        WYD_Kwota = wYD_Kwota;
    }
    public String getWYD_Data() {
        return WYD_Data;
    }
    public void setWYD_Data(String wYD_Data) {
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
}
