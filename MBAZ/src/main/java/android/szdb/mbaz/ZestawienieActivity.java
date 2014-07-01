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
    private List<Integer> kolory;
    private PieGraph pg;
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
                updateGraph();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(kontekst, "Wybierz okres", Toast.LENGTH_SHORT).show();
            }
        });

        adapterZestawienie = new ArrayZestawienieAdapter(this, R.layout.textview2_adapter, kwoty, KDO, KWY, SUB);
        zestawienie.setAdapter(adapterZestawienie);
        Resources resources = getResources();
        kolory = new ArrayList<Integer>();
        kolory.add(resources.getColor(R.color.Tomato));
        kolory.add(resources.getColor(R.color.blue));
        kolory.add(resources.getColor(R.color.transparent_blue));
        kolory.add(resources.getColor(R.color.green));
        kolory.add(resources.getColor(R.color.green_light));
        kolory.add(resources.getColor(R.color.orange));
        kolory.add(resources.getColor(R.color.transparent_orange));
        kolory.add(resources.getColor(R.color.purple));
        kolory.add(resources.getColor(R.color.yellow));
        kolory.add(resources.getColor(R.color.fushia));
        kolory.add(resources.getColor(R.color.lime));
        kolory.add(resources.getColor(R.color.navy));
        kolory.add(resources.getColor(R.color.IndianRed));
        kolory.add(resources.getColor(R.color.SeaGreen));
        kolory.add(resources.getColor(R.color.Wheat));
        kolory.add(resources.getColor(R.color.Violet));
        kolory.add(resources.getColor(R.color.red));
        kolory.add(resources.getColor(R.color.SlateGray));
        kolory.add(resources.getColor(R.color.PaleGreen));
        kolory.add(resources.getColor(R.color.DodgerBlue));

        pg = (PieGraph) findViewById(R.id.pieGraphZestawienie);
        updateGraph();
        pg.setInnerCircleRatio(120);
        pg.setPadding(5);
        pg.setOnSliceClickedListener(new PieGraph.OnSliceClickedListener() {

            @Override
            public void onClick(int index) {
                int max = KDO.size();
                int i = 0;
                int j = 0;
                for (; i < max; i++) {
                    if (kwoty.get(i) == 0.0f)
                        continue;
                    if (index == j)
                        Toast.makeText(getApplicationContext(),KDO.get(i).toString() + " " + String.valueOf(kwoty.get(i)),Toast.LENGTH_SHORT).show();
                    j++;
                }
                max += KWY.size();
                for (; i < max; i++) {
                    if (kwoty.get(i) == 0.0f)
                        continue;
                    if (index == j)
                        Toast.makeText(getApplicationContext(),KWY.get(i - KDO.size()).toString() + " -" + String.valueOf(kwoty.get(i)),Toast.LENGTH_SHORT).show();
                    j++;
                }
                /*
                max += SUB.size();
                for (; i < max; i++) {
                    if (kwoty.get(i) == 0.0f)
                        continue;
                    if (index == i)
                        Toast.makeText(getApplicationContext(), SUB.get(i).toString() + " -" + String.valueOf(kwoty.get(i)), Toast.LENGTH_SHORT).show();
                    j++;
                }*/
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

    public void updateGraph() {
        Resources resources = getResources();
        pg.removeSlices();
        for (int i = 0; i < KDO.size() + KWY.size(); i++) {
            PieSlice slice = new PieSlice();
            slice.setColor(kolory.get(i % 20));
            slice.setSelectedColor(resources.getColor(R.color.highlight));
            slice.setValue(kwoty.get(i));
            if (slice.getValue() != 0.0f)
                pg.addSlice(slice);
        }
    }
}
