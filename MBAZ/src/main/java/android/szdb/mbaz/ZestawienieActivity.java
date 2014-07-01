package android.szdb.mbaz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ZestawienieActivity extends Activity{

    private Spinner okresy;
    private ListView zestawienie;
    private List<COkres> listaOkres;
    private ArrayAdapter<COkres> adapterOkres;
    private ArrayZestawienieAdapter adapterZestawienie;
    private CBazaSystem bazaDanych;
    private List<Float> kwoty;
    private List<CKat_doch> KDO;
    private List<CKat_wyd> KWY;
    private List<CSubkategoria> SUB;
    private List<CDochody> Dochody;
    private List<CWydatki> Wydatki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zestawienie);
        okresy = (Spinner) findViewById(R.id.spinnerZestawienie);
        zestawienie = (ListView) findViewById(R.id.listViewZestawienie);
        kwoty = new ArrayList<Float>();
        bazaDanych = new CBazaSystem(this);
        bazaDanych.open();

        okresy.setSelection(0);
        KDO = bazaDanych.zwrocKDO();
        KWY = bazaDanych.zwrocKWY();
        SUB = bazaDanych.zwrocSubkategorie();
        Dochody = bazaDanych.zwrocDochody();
        Wydatki = bazaDanych.zwrocWydatki();
        listaOkres = bazaDanych.zwrocOkresy();
        obliczBilans();
        adapterOkres = new ArrayAdapter<COkres>(this, android.R.layout.simple_list_item_1, listaOkres);
        okresy.setAdapter(adapterOkres);
        final Context kontekst = this;
        okresy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                obliczBilans();
                adapterZestawienie.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(kontekst, "Wybierz okres", Toast.LENGTH_SHORT).show();
            }
        });

        adapterZestawienie = new ArrayZestawienieAdapter(this, R.layout.textview2_adapter, kwoty, KDO, KWY, SUB);
        zestawienie.setAdapter(adapterZestawienie);

        final Resources resources = getResources();
        final PieGraph pg = (PieGraph) findViewById(R.id.pieGraphZestawienie);
        PieSlice slice = new PieSlice();
        slice.setColor(resources.getColor(R.color.green_light));
        slice.setSelectedColor(resources.getColor(R.color.transparent_orange));
        slice.setValue(2);
        pg.addSlice(slice);
        slice = new PieSlice();
        slice.setColor(resources.getColor(R.color.orange));
        slice.setValue(3);
        pg.addSlice(slice);
        slice = new PieSlice();
        slice.setColor(resources.getColor(R.color.purple));
        slice.setValue(8);
        pg.addSlice(slice);
        pg.setInnerCircleRatio(120);
        pg.setPadding(10);
        pg.setOnSliceClickedListener(new PieGraph.OnSliceClickedListener() {

            @Override
            public void onClick(int index) {
                Toast.makeText(getApplicationContext(),"Slice " + index + " clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.zestawienie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        setResult(Activity.RESULT_CANCELED, intent);
        bazaDanych.close();
        finish();
    }

    private void obliczBilans() {
        kwoty.clear();
        CData d1 = new CData(listaOkres.get(okresy.getSelectedItemPosition()).getOKR_Od().toString());
        CData d2 = new CData(listaOkres.get(okresy.getSelectedItemPosition()).getOKR_Do().toString());
        int tmp;
        for (int i = 0; i < KDO.size(); i++) {
            tmp = KDO.get(i).getKDOk_1_Id();
            float tmp2 = 0.0f;
            for (int j = 0; j < Dochody.size(); j++) {
                CData d3 = new CData(Dochody.get(j).getDOC_Data());
                if (Dochody.get(j).getKDO_Id() == tmp && (d1.compareTo(d3) == -1 || d1.compareTo(d3) == 0) && (d2.compareTo(d3) == 1 || d2.compareTo(d3) == 0))
                    tmp2 = tmp2 + Dochody.get(j).getDOC_Kwota();
            }
            kwoty.add(tmp2);
        }
        for (int i = 0; i < KWY.size(); i++) {
            tmp = KWY.get(i).getKWYk_1_Id();
            float tmp2 = 0.0f;
            for (int j = 0; j < Wydatki.size(); j++) {
                CData d3 = new CData(Wydatki.get(j).getWYD_Data());
                if (Wydatki.get(j).getKWY_Id() == tmp && (d1.compareTo(d3) == -1 || d1.compareTo(d3) == 0) && (d2.compareTo(d3) == 1 || d2.compareTo(d3) == 0))
                    tmp2 = tmp2 + Wydatki.get(j).getWYD_Kwota();
            }
            kwoty.add(tmp2);
        }
        for (int i = 0; i < SUB.size(); i++) {
            tmp = SUB.get(i).getSUBk_1_Id();
            float tmp2 = 0.0f;
            for (int j = 0; j < Wydatki.size(); j++) {
                CData d3 = new CData(Wydatki.get(j).getWYD_Data());
                if (Wydatki.get(j).getSUB_Id() == tmp && (d1.compareTo(d3) == -1 || d1.compareTo(d3) == 0) && (d2.compareTo(d3) == 1 || d2.compareTo(d3) == 0))
                    tmp2 = tmp2 + Wydatki.get(j).getWYD_Kwota();
            }
            kwoty.add(tmp2);
        }
    }
}
