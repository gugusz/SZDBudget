package android.szdb.mbaz;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class ArrayDochodyAdapter extends ArrayAdapter{
    private Context mContext;
    private int mResource;
    private List<CDochody> dochody;
    private List<CKat_doch> kategorie;

    public ArrayDochodyAdapter(Context context, int resource, List objects, List objects2) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.dochody = objects;
        this.kategorie = objects2;
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
        text.setText(dochody.get(position).toString() + ", kategorii:  " + kategorie.get(dochody.get(position).getKDO_Id() - 1).toString());
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
