package android.szdb.mbaz;

public class CDochody {
    private int DOCk_1_Id; //Primary Key
    private float DOC_Kwota;
    private String DOC_Data;
    private int OKR_Id; //Foreign Key do Okres
    private int KDO_Id; //Foreign Key do Kategorie Dochodów

    public int getDOCk_1_Id() {
        return DOCk_1_Id;
    }
    public void setDOCk_1_Id(int dOCk_1_Id) {
        DOCk_1_Id = dOCk_1_Id;
    }
    public float getDOC_Kwota() {
        return DOC_Kwota;
    }
    public void setDOC_Kwota(float dOC_Kwota) {
        DOC_Kwota = dOC_Kwota;
    }
    public String getDOC_Data() {
        return DOC_Data;
    }
    public void setDOC_Data(String dOC_Data) {
        DOC_Data = dOC_Data;
    }

    public int getOKR_Id() {
        return OKR_Id;
    }
    public void setOKR_Id(int oKR_Id) {
        OKR_Id = oKR_Id;
    }

    public int getKDO_Id() {
        return KDO_Id;
    }
    public void setKDO_Id(int kDO_Id) {
        KDO_Id = kDO_Id;
    }
}
