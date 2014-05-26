package android.szdb.mbaz;

public class CPlanowanie {
    private int PLAk_1_Id; //Primary Key
    private String PLA_NazwaPrzedmiotu;
    private String PLA_Od;
    private String PLA_DataZakupu;

    public int getPLAk_1_Id() {
        return PLAk_1_Id;
    }

    public String getPLA_NazwaPrzedmiotu() {
        return PLA_NazwaPrzedmiotu;
    }

    public String getPLA_Od() {
        return PLA_Od;
    }

    public String getPLA_DataZakupu() {
        return PLA_DataZakupu;
    }

    public void setPLAk_1_Id(int PLAk_1_Id) {
        this.PLAk_1_Id = PLAk_1_Id;
    }

    public void setPLA_NazwaPrzedmiotu(String PLA_NazwaPrzedmiotu) {
        this.PLA_NazwaPrzedmiotu = PLA_NazwaPrzedmiotu;
    }

    public void setPLA_Od(String PLA_Od) {
        this.PLA_Od = PLA_Od;
    }

    public void setPLA_DataZakupu(String PLA_DataZakupu) {
        this.PLA_DataZakupu = PLA_DataZakupu;
    }

    public String toString(){
        return "Nazwa: " + PLA_NazwaPrzedmiotu + ", Od: " + PLA_Od + ", Do: " + PLA_DataZakupu;
    }
}