package android.szdb.mbaz;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Wlasny adapter dla aktywnosci Zestawienie
 * W listView wyswietla 2 kulumny
 * @author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 * @see android.szdb.mbaz.ZestawienieActivity
 */
public class ArrayZestawienieAdapter extends ArrayAdapter {
    private Context mContext;
    private int mResource;
    private List<Float> kwoty;
    private List<CKat_doch> KDO;
    private List<CKat_wyd> KWY;
    private List<CSubkategoria> SUB;

    /**
     * Konstruktor klasy
     * @param context kontekst
     * @param resource wymagany parametr
     * @param objects Lista z kwotami
     * @param objects2 Lista z kategoriami dochodow
     * @param objects3 Lista z kategoriami wydatkow
     * @param objects4 Lista z Subkategoriami
     */
    public ArrayZestawienieAdapter(Context context, int resource, List objects, List objects2, List objects3, List objects4) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.kwoty = objects;
        this.KDO = objects2;
        this.KWY = objects3;
        this.SUB = objects4;
    }

    /**
     * Wewnetrzna klasa, ktora jest kontenerem widokow
     */
    private class Kontener {
        TextView text1;
        TextView text2;
    }

    /**
     * Metoda tworząca widok ktory będzie sie wyswietlal wewnatrz ListView
     * @param position pozycja
     * @param convertView widok,ktory storzylismy na potrzeba adaptera
     * @param parent rodzic widoku
     * @return Nowo utworzony widok, ktory bedzie widnial w ListView
     */
    private View createViewFromResource(int position, View convertView, ViewGroup parent) {
        Kontener kontener;
        View view = convertView;
        if(view == null)
        {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            view = inflater.inflate(mResource, parent, false);
            kontener = new Kontener();
            assert view != null;
            kontener.text1 = (TextView) view.findViewById(R.id.txt2Title1);
            kontener.text2 = (TextView) view.findViewById(R.id.txt2Title2);
            view.setTag(kontener);
        }
        else
        {
            kontener = (Kontener)view.getTag();
        }

        if (position < KDO.size()) {
            kontener.text1.setText(KDO.get(position).getKDO_Nazwa());
            kontener.text2.setText("+" + kwoty.get(position).toString());
        }
        else if (position < KDO.size() + KWY.size()) {
            kontener.text1.setText(KWY.get(position - KDO.size()).getKWY_Nazwa());
            kontener.text2.setText("-" + kwoty.get(position).toString());
        }
        else if (position < KDO.size() + KWY.size() + SUB.size()) {
            kontener.text1.setText(SUB.get(position - KDO.size() - KWY.size()).getSUB_Nazwa());
            kontener.text2.setText("-" + kwoty.get(position).toString());
        }
        return  view;
    }

    /**
     * Przeciazana metoda, ktora pobiera widok
     * @param position pozycja
     * @param convertView nowy widok
     * @param parent rodzic widoku
     * @return Nowo utworzony widok
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent);
    }

    /**
     * Przeciazana metoda, ktora pobiera widok po rozwiniecu ListView
     * @param position pozycja
     * @param convertView nowy widok
     * @param parent rodzic widoku
     * @return Nowo utworzony widok
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent);
    }
}
