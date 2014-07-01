package android.szdb.mbaz;

/**
 * Klasa przechowywyuaca daty
 * Specjalnie utworzona do wspolpray z baza danych
 * @author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 */
public class CData {
    private String Data;

    /**
     * Konstruktor
     * @param data ciag znakow z data
     */
    public CData(String data) {
        Data = data;
    }

    /**
     * getter
     * @return data
     */
    public String getData() {
        return Data;
    }

    /**
     * Setter
     * @param data ciag znakow z data
     */
    public void setData(String data) {
        Data = data;
    }

    /**
     * getter
     * @return Rok
     */
    public int getRok() {
        return Integer.valueOf(this.Data.substring(0,4));
    }

    /**
     * getter
     * @return Miesiac
     */
    public int getMiesiac() {
        return Integer.valueOf(this.Data.substring(5,7));
    }

    /**
     * getter
     * @return Dzien
     */
    public int getDzien() {
        return Integer.valueOf(this.Data.substring(8,10));
    }

    /**
     * getter
     * @return Miesiac
     */
    public String getStringMiesiac() {
        return this.Data.substring(5,7);
    }

    /**
     * getter
     * @return Dzien
     */
    public String getStringDzien() {
        return this.Data.substring(8,10);
    }

    /**
     * Setter
     * @param rok rok
     */
    public void setRok(int rok) {
        if (rok >= 2000 && rok <= 2500)
            this.Data = String.valueOf(rok) + this.Data.substring(4);
    }

    /**
     * Setter
     * @param miesiac miesiac
     */
    public void setMiesiac(int miesiac) {
        if (miesiac >= 10 && miesiac <= 12)
            this.Data = this.Data.substring(0, 4) + String.valueOf(miesiac) + this.Data.substring(7);
        else if (miesiac >= 1 && miesiac <= 9)
            this.Data = this.Data.substring(0, 4) + "0" + String.valueOf(miesiac) + this.Data.substring(7);
    }

    /**
     * Setter
     * @param dzien dzien
     */
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

    /**
     * Metoda porownujaca 2 daty.
     * Jesli metoda zwroci -1 to data na rzecz ktorej wywolywana jest metoda jest mniejsza
     * Jesli metoda zwroci 1 to data na rzecz ktorej wywolywana jest metoda jest wieksza
     * Jesli metoda zwroci 0 to obie daty sa rowne
     * @param druga druga data do porownania
     */
    public int compareTo (CData druga) {
        if (this.getDzien() == druga.getDzien() && this.getMiesiac() == druga.getMiesiac() && this.getRok() == druga.getRok()) {
            return 0;
            //Daty są równe
        }
        else if (this.getRok() < druga.getRok()) {
            return -1;
            //Data pierwsza jest mniejsza od drugiej
        }
        else if (this.getRok() > druga.getRok())
            return 1;
        else if (this.getMiesiac() < druga.getMiesiac())
            return -1;
        else if (this.getMiesiac() > druga.getMiesiac())
            return 1;
        else if (this.getDzien() < druga.getDzien())
            return -1;
        else if (this.getDzien() > druga.getDzien())
            return 1;
        else {
            System.out.println("Dupa");
            return 2;
        }
    }
}
