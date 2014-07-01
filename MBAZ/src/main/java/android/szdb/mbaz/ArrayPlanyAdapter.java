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
 * Wlasny adapter dla aktywnosci Plany
 * Laczy dwie listy - okresy z kwotami
 * @author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 * @see android.szdb.mbaz.PlanyActivity
 */
public class ArrayPlanyAdapter extends ArrayAdapter<COkres> {

    private Context mContext;
    private int mResource;
    private List kwoty;
    private List okr;

    /**
     * Konstruktor klasy
     * @param context kontekst
     * @param resource wymagany parametr
     * @param objects Lista z kwotami
     * @param objects2 Lista z okresami
     */
    public ArrayPlanyAdapter(Context context, int resource, List objects, List objects2) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.kwoty = objects2;
        this.okr = objects;
    }

    /**
     * Metoda tworząca widok ktory będzie sie wyswietlal wewnatrz Spinnera
     * @param position pozycja
     * @param convertView widok,ktory storzylismy na potrzeba adaptera
     * @param parent rodzic widoku
     * @return Nowo utworzony widok, ktory bedzie widnial w spinnerze
     */
    private View createViewFromResource(int position, View convertView, ViewGroup parent) {
        TextView text;
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
        text.setText("W okresie " + okr.get(position).toString() + " musisz oszczędzić " + kwoty.get(position) + "zł");
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
     * Przeciazana metoda, ktora pobiera widok po rozwiniecu spinnera
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
