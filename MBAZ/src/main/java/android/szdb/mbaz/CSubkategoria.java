package android.szdb.mbaz;

public class CSubkategoria {
    private int SUBk_1_Id; //Primary Key
    private String SUB_Nazwa;

    public int getSUBk_1_Id() {
        return SUBk_1_Id;
    }
    public void setSUBk_1_Id(int sUBk_1_Id) {
        SUBk_1_Id = sUBk_1_Id;
    }
    public String getSUB_Nazwa() {
        return SUB_Nazwa;
    }
    public void setSUB_Nazwa(String sUB_Nazwa) {
        SUB_Nazwa = sUB_Nazwa;
    }
    public String toString() {
        return SUB_Nazwa;
    }
}
