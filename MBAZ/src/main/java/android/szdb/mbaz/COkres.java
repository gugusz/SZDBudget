package android.szdb.mbaz;

public class COkres {
    private int OKRk_1_Id; //Primary Key
    private String OKR_Od;
    private String OKR_Do;

    //gettery i settery
    public int getOKRk_1_Id() {
        return OKRk_1_Id;
    }

    public void setOKRk_1_Id(int oKRk_1_Id) {
        OKRk_1_Id = oKRk_1_Id;
    }

    public String getOKR_Od() {
        return OKR_Od;
    }

    public void setOKR_Od(String oKR_Od) {
        OKR_Od = oKR_Od;
    }

    public String getOKR_Do() {
        return OKR_Do;
    }

    public void setOKR_Do(String oKR_Do) {
        OKR_Do = oKR_Do;
    }

    //przeciażenie metody toString()
    public String toString(){
        return "OD: " + OKR_Od + " DO: " + OKR_Do;
    }
}
