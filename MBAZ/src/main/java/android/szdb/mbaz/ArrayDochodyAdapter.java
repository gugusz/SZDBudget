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
 * Wlasny adapter dla aktywnosci Dochody
 * Tlumaczy klucz obcy na ciag znakow czytelny dla czlowieka
 * @author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 * @see android.szdb.mbaz.DochodyActivity
 */
public class ArrayDochodyAdapter extends ArrayAdapter{
    private Context mContext;
    private int mResource;
    private List<CDochody> dochody;
    private List<CKat_doch> kategorie;

    /**
     * Konstruktor klasy
     * @param context kontekst
     * @param resource wymagany parametr
     * @param objects Lista z dochodami
     * @param objects2 Lista z kategoriami dochodow
     */
    public ArrayDochodyAdapter(Context context, int resource, List objects, List objects2) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.dochody = objects;
        this.kategorie = objects2;
    }

    /**
     * Metoda tworząca widok ktory będzie sie wyswietlal wewnatrz ListView
     * @param position pozycja
     * @param convertView widok,ktory storzylismy na potrzeba adaptera
     * @param parent rodzic widoku
     * @return Nowo utworzony widok, ktory bedzie widnial w ListView
     */
    private View createViewFromResource(int position, View convertView, ViewGroup parent) {
        TextView text;
        //LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View view = mInflater.inflate(mResource, parent, false);
        View view = convertView;
        if(view == null)
        {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            view = inflater.inflate(mResource, parent, false);

            assert view != null;
            text = (TextView) view.findViewById(R.id.txtTitle);

            view.setTag(text);
        }
        else
        {
            text = (TextView)view.getTag();
        }
        text.setText(dochody.get(position).toString() + ", kategorii:  " + kategorie.get(dochody.get(position).getKDO_Id() - 1).toString());
        return view;
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
