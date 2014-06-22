package android.szdb.mbaz;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Klasa odpowiadajaca za wywwietlanie aktywnosci planowanie
 * @author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 */
public class PlanyActivity extends Activity implements View.OnClickListener{

    private EditText nazwa;
    private EditText od;
    private EditText dataZakupu;
    private EditText cena;
    private Button buttonDodaj;
    private CBazaSystem bazaDanych;
    private ListView listaViewPlany;
    private List<CPlanowanie> lista;
    private ArrayAdapter<CPlanowanie> adapter;
    private Spinner spinnerPlany;
    private ArrayPlanyAdapter adapterPlany;
    private List<COkres> okresy;
    private List<Float> kwoty;
    private Calendar myCalendarOd = Calendar.getInstance();
    private Calendar myCalendarDataZakupu = Calendar.getInstance();

    /**
     * Metoda bedaca w pewnym sensie konstruktorem. Wywolywana jest podczas tworzenia aktywnosci. Przypisuje id kontrolek do pol klasy
     * Otwiera baze danych, tworzy ArrayAdapter w ktorym przechowaywane sa rekordy bazy, ustawia OnClickListenera
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plany);
        nazwa = (EditText)findViewById(R.id.editTextPlanowanieCo);
        od = (EditText)findViewById(R.id.editTextPlanyOd);
        dataZakupu = (EditText)findViewById(R.id.editTextPlanyDo);
        cena = (EditText)findViewById(R.id.editTextPlanyCena);
        buttonDodaj = (Button)findViewById(R.id.buttonPlanyDodaj);
        listaViewPlany = (ListView) findViewById(R.id.listViewPlany);
        spinnerPlany = (Spinner) findViewById(R.id.spinnerPlany);

        bazaDanych = new CBazaSystem(this);
        bazaDanych.open();
        buttonDodaj.setOnClickListener(this);
        od.setOnClickListener(this);
        dataZakupu.setOnClickListener(this);
        registerForContextMenu(listaViewPlany);

        lista = bazaDanych.zwrocPlany();
        okresy = bazaDanych.zwrocOkresy();

        adapter = new ArrayAdapter<CPlanowanie>(this, android.R.layout.simple_list_item_1, lista);
        listaViewPlany.setAdapter(adapter);

        kwoty = new ArrayList<Float>();
        this.obliczKwote();
        //Toast.makeText(this, String.valueOf(kwoty.get(0)), Toast.LENGTH_SHORT).show();

        adapterPlany = new ArrayPlanyAdapter(this, R.layout.textview_adapter, okresy, kwoty);
        spinnerPlany.setAdapter(adapterPlany);
    }

    DatePickerDialog.OnDateSetListener dateOd = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendarOd.set(Calendar.YEAR, year);
            myCalendarOd.set(Calendar.MONTH, monthOfYear);
            myCalendarOd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(1);
        }
    };

    DatePickerDialog.OnDateSetListener dateDataZakupu = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendarDataZakupu.set(Calendar.YEAR, year);
            myCalendarDataZakupu.set(Calendar.MONTH, monthOfYear);
            myCalendarDataZakupu.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(2);
        }
    };

    private void updateLabel(int numer) {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        switch (numer) {
            case 1:
                od.setText(sdf.format(myCalendarOd.getTime()));
                break;
            case 2:
                dataZakupu.setText(sdf.format(myCalendarDataZakupu.getTime()));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.plany, menu);
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

    /**
     * Metoda odopwiadajaca za obluge eventow jakie zaszly za pomoca kliknieca
     * @param view kontrolka klay View
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.buttonPlanyDodaj:
                CData tmp1 = new CData(okresy.get(okresy.size() - 1).getOKR_Do());
                CData tmp2 = new CData(dataZakupu.getText().toString());
                CData tmp3 = new CData(od.getText().toString());
                if (nazwa.getText().toString().isEmpty() && od.getText().toString().isEmpty() && dataZakupu.getText().toString().isEmpty() && cena.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Wypełnij wszystkie pola!", Toast.LENGTH_LONG).show();
                    break;
                }
                else if (tmp1.compareTo(tmp2) == -1) {
                    Toast.makeText(this, tmp1.getData().toString() + " Zanim dodasz plan, uzupełnij okresy!", Toast.LENGTH_LONG).show();
                    break;
                }
                else if (tmp3.compareTo(tmp2) == 1) {
                    Toast.makeText(this, "Data zakupu nie może być mniejsza od daty zaczynającej oszczędzanie!", Toast.LENGTH_LONG).show();
                    break;
                }
                ArrayAdapter<CPlanowanie> adapter = (ArrayAdapter<CPlanowanie>) listaViewPlany.getAdapter();
                CPlanowanie pla = bazaDanych.dodajPlan(nazwa.getText().toString(), od.getText().toString(), dataZakupu.getText().toString(), Float.valueOf(cena.getText().toString()));
                adapter.add(pla);
                od.setHint("Podaj datę od kiedy chcesz oszczędzać");
                od.setText("");
                dataZakupu.setHint("Podaj planowaną datę zakupu");
                dataZakupu.setText("");
                nazwa.setHint("Podaj nazwę przedmiotu");
                nazwa.setText("");
                cena.setHint("Podaj cenę planowanej rzeczy");
                cena.setText("");
                kwoty.clear();
                this.obliczKwote();
                adapterPlany.notifyDataSetChanged();
                break;
            case R.id.editTextPlanyOd:
                new DatePickerDialog(this, dateOd, myCalendarOd.get(Calendar.YEAR), myCalendarOd.get(Calendar.MONTH), myCalendarOd.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.editTextPlanyDo:
                new DatePickerDialog(this, dateDataZakupu, myCalendarDataZakupu.get(Calendar.YEAR), myCalendarDataZakupu.get(Calendar.MONTH), myCalendarDataZakupu.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    /**
     * Tworzy menu kontekstowe
     * @param menu menu
     * @param v kontrolka
     * @param menuInfo informacja o menu
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Wybierz czynność:");
        menu.add(0, v.getId(), 0, "Usuń wpis");
        menu.add(0, v.getId(), 0, "Edytuj wpis");
    }

    /**
     * Metoda obsluujaca klikniece w menu kontekstowym
     * @param item wybrany element menu
     * @return wartosc boolowska
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle()=="Usuń wpis") {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;
                bazaDanych.usunPlan(lista.get(index));
                lista.remove(index);
                adapter.notifyDataSetChanged();
        }
        else if(item.getTitle()=="Edytuj wpis"){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;
            Toast.makeText(this,String.valueOf(index),Toast.LENGTH_SHORT).show();
        }
        else {return false;}
        return true;
    }

    private void obliczKwote() {
        int ilePlanow[] = new int[lista.size()];
        CData d1, d2, d3, d4;

        for (int i = 0; i < lista.size(); i++)
        {
            d1 = new CData(lista.get(i).getPLA_Od());
            d3 = new CData(lista.get(i).getPLA_DataZakupu());

            for (int j = 0; j < okresy.size(); j++)
            {
                d2 = new CData(okresy.get(j).getOKR_Od());
                d4 = new CData(okresy.get(j).getOKR_Do());
                if (((d1.compareTo(d2) == -1 || d1.compareTo(d2) == 0) && (d3.compareTo(d4) == 1 || d3.compareTo(d4) == 0)) || (d1.compareTo(d2) == 1 && d1.compareTo(d4) == -1) || (d3.compareTo(d2) == 1 && d3.compareTo(d4) == -1))
                    ilePlanow[i]++;
            }
        }

        for (int i = 0; i < okresy.size(); i++) {
            float tmp = 0.0f;
            d2 = new CData(okresy.get(i).getOKR_Od());
            d4 = new CData(okresy.get(i).getOKR_Do());
            for (int j = 0; j < lista.size(); j++) {
                d1 = new CData(lista.get(j).getPLA_Od());
                d3 = new CData(lista.get(j).getPLA_DataZakupu());
                if (((d1.compareTo(d2) == -1 || d1.compareTo(d2) == 0) && (d3.compareTo(d4) == 1 || d3.compareTo(d4) == 0)) || (d1.compareTo(d2) == 1 && d1.compareTo(d4) == -1) || (d3.compareTo(d2) == 1 && d3.compareTo(d4) == -1))
                    tmp = tmp + lista.get(j).getPLA_Cena() / ilePlanow[j];
            }
            kwoty.add(tmp);
        }
    }
}
