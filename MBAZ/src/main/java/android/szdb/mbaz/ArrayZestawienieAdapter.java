package android.szdb.mbaz;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import javax.security.auth.Subject;

/**
 * Created by Michał & Adrian on 2014-06-22.
 */
public class ArrayZestawienieAdapter extends ArrayAdapter {
    private Context mContext;
    private int mResource;
    private List<Float> kwoty;
    private List<CKat_doch> KDO;
    private List<CKat_wyd> KWY;
    private List<CSubkategoria> SUB;

    public ArrayZestawienieAdapter(Context context, int resource, List objects, List objects2, List objects3, List objects4) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.kwoty = objects;
        this.KDO = objects2;
        this.KWY = objects3;
        this.SUB = objects4;
    }

    private class Kontener {
        TextView text1;
        TextView text2;
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent) {
        Kontener kontener = null;
        View view = convertView;
        if(view == null)
        {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            view = inflater.inflate(mResource, parent, false);
            kontener = new Kontener();
            kontener.text1 = (TextView) view.findViewById(R.id.txt2Title1);
            kontener.text2 = (TextView) view.findViewById(R.id.txt2Title2);
            view.setTag(kontener);
        }
        else
        {
            kontener = (Kontener)view.getTag();
        }

        if (position < KDO.size()) {
            kontener.text1.setText(KDO.get(position).getKDO_Nazwa().toString());
            kontener.text2.setText("+" + kwoty.get(position).toString());
        }
        else if (position < KDO.size() + KWY.size()) {
            kontener.text1.setText(KWY.get(position - KDO.size()).getKWY_Nazwa().toString());
            kontener.text2.setText("-" + kwoty.get(position).toString());
        }
        else if (position < KDO.size() + KWY.size() + SUB.size()) {
            kontener.text1.setText(SUB.get(position - KDO.size() - KWY.size()).getSUB_Nazwa().toString());
            kontener.text2.setText("-" + kwoty.get(position).toString());
        }
        return  view;
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
