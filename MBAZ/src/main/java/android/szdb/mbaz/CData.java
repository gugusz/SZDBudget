package android.szdb.mbaz;

/**
 * Created by Michał & Adrian on 2014-06-21.
 */
public class CData {
    private String Data;

    public CData(String data) {
        Data = data;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public int getRok() {
        return Integer.valueOf(this.Data.substring(0,4));
    }

    public int getMiesiac() {
        return Integer.valueOf(this.Data.substring(5,7));
    }

    public int getDzien() {
        return Integer.valueOf(this.Data.substring(8,10));
    }

    public String getStringRok() {
        return this.Data.substring(0,4);
    }

    public String getStringMiesiac() {
        return this.Data.substring(5,7);
    }

    public String getStringDzien() {
        return this.Data.substring(8,10);
    }

    public void setRok(int rok) {
        if (rok >= 2000 && rok <= 2500)
            this.Data = String.valueOf(rok) + this.Data.substring(4);
    }

    public void setMiesiac(int miesiac) {
        if (miesiac >= 10 && miesiac <= 12)
            this.Data = this.Data.substring(0, 4) + String.valueOf(miesiac) + this.Data.substring(7);
        else if (miesiac >= 1 && miesiac <= 9)
            this.Data = this.Data.substring(0, 4) + "0" + String.valueOf(miesiac) + this.Data.substring(7);
    }

    public void setDzien(int dzien) {
        if (dzien >=1 && dzien <= 9)
            this.Data = this.Data.substring(0, 7) + "0" + String.valueOf(dzien);
        else if (dzien > 9 && dzien < 29)
            this.Data = this.Data.substring(0, 7) + String.valueOf(dzien);
        else if (dzien == 29 && this.getMiesiac() == 2 && ((this.getRok()%4 == 0 && this.getRok()%100 != 0) || this.getRok()%400 == 0))
            this.Data = this.Data.substring(0, 7) + String.valueOf(dzien);
        else if ((dzien > 28 && dzien < 31) && this.getMiesiac() != 2)
            this.Data = this.Data.substring(0, 7) + String.valueOf(dzien);
        else if (dzien == 31 && (this.getMiesiac() == 1 || this.getMiesiac() == 3 || this.getMiesiac() == 5 || this.getMiesiac() == 7 || this.getMiesiac() == 8 || this.getMiesiac() == 10 || this.getMiesiac() == 12))
            this.Data = this.Data.substring(0, 7) + String.valueOf(dzien);
    }

    public int compareTo (CData druga) {
        if (this.getDzien() == druga.getDzien() && this.getMiesiac() == druga.getMiesiac() && this.getRok() == druga.getRok()) {
            return 0;
            //Daty są równe
        }

        else if (this.getRok() < druga.getRok()) {
            return -1;
            //Data pierwsza jest mniejsza od drugiej
        }
        else if (this.getMiesiac() < druga.getMiesiac())
            return -1;
        else if (this.getDzien() < druga.getDzien())
            return -1;
        else
            return 1;
    }
}
