package android.szdb.mbaz;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

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
    private TextView wynik;
    private ListView listaViewPlany;
    private List<CPlanowanie> lista;
    private ArrayAdapter<CPlanowanie> adapter;

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
        wynik = (TextView) findViewById(R.id.textViewWynik);
        wynik.setText("W tym okresie musisz odłożyć: " + this.obliczKwote() + "zł");

        bazaDanych = new CBazaSystem(this);
        bazaDanych.open();
        buttonDodaj.setOnClickListener(this);
        registerForContextMenu(listaViewPlany);

        lista = bazaDanych.zwrocPlany();
        adapter = new ArrayAdapter<CPlanowanie>(this, android.R.layout.simple_list_item_1, lista);
        listaViewPlany.setAdapter(adapter);
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

    /**
     * Metoda odopwiadajaca za obluge eventow jakie zaszly za pomoca kliknieca
     * @param view kontrolka klay View
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.buttonPlanyDodaj:
                if (od.getText().toString().matches("")) {
                    od.setText(DateFormat.getDateInstance().format(new Date()));
                }
                ArrayAdapter<CPlanowanie> adapter = (ArrayAdapter<CPlanowanie>) listaViewPlany.getAdapter();
                CPlanowanie pla = bazaDanych.dodajPlan(nazwa.getText().toString(), od.getText().toString(), dataZakupu.getText().toString());
                adapter.add(pla);
                od.setHint("Podaj datę od kiedy chcesz oszczędzać");
                od.setText("");
                dataZakupu.setHint("Podaj planowaną datę zakupu");
                dataZakupu.setText("");
                nazwa.setHint("Podaj nazwę przedmiotu");
                nazwa.setText("");
                cena.setHint("Podaj cenę planowanej rzeczy");
                cena.setText("");
                wynik.setText("W tym okresie musisz odłożyć: " + this.obliczKwote() + "zł");
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

    /**
     * Metoda obliczajaca kwote ktora nalezy oszczedzic
     * @return
     */
    private float obliczKwote() {
        return 0.0f;
    }
}
