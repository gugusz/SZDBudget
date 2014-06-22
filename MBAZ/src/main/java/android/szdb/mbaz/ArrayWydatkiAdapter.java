package android.szdb.mbaz;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class ArrayWydatkiAdapter extends ArrayAdapter{
    private Context mContext;
    private int mResource;
    private List<CWydatki> wydatki;
    private List<CKat_wyd> kategorie;
    private List<CSubkategoria> subkategorie;

    public ArrayWydatkiAdapter(Context context, int resource, List objects, List objects2, List objects3) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.wydatki = objects;
        this.kategorie = objects2;
        this.subkategorie = objects3;
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent) {
        TextView text;
        //LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View view = mInflater.inflate(mResource, parent, false);
        View view = convertView;
        if(view == null)
        {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            view = inflater.inflate(mResource, parent, false);

            text = (TextView) view.findViewById(R.id.txtTitle);

            view.setTag(text);
        }
        else
        {
            text = (TextView)view.getTag();
        }
        text.setText(wydatki.get(position).toString() + ", kategorii:  " + kategorie.get(wydatki.get(position).getKWY_Id() - 1).toString() + ", subkategorii: " + subkategorie.get(wydatki.get(position).getSUB_Id() - 1).toString());
        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent);
    }
}
