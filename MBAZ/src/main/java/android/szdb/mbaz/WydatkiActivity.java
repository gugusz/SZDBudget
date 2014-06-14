package android.szdb.mbaz;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Klasa odpowiadajaca za wywwietlanie aktywnosci wydatki
 * @author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 */
public class WydatkiActivity extends Activity {

    private ListView listViewWydatki;
    private CBazaSystem bazaDanych;
    private List<CWydatki> lista;
    private ArrayAdapter<CWydatki> adapter;

    /**
     * Metoda bedaca w pewnym sensie konstruktorem. Wywolywana jest podczas tworzenia aktywnosci. Przypisuje id kontrolek do pol klasy
     * Otwiera baze danych, tworzy ArrayAdapter w ktorym przechowaywane sa rekordy bazy,
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wydatki);
        listViewWydatki = (ListView)findViewById(R.id.listViewWydatki);
        registerForContextMenu(listViewWydatki);

        bazaDanych = new CBazaSystem(this);
        bazaDanych.open();

        lista = bazaDanych.zwrocWydatki();
        adapter = new ArrayAdapter<CWydatki>(this, android.R.layout.simple_list_item_1, lista);
        listViewWydatki.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wydatki, menu);
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
            bazaDanych.usunWydatki(lista.get(index));
            lista.remove(index);
            adapter.notifyDataSetChanged();
        }
        else if(item.getTitle()=="Edytuj wpis"){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;
            Toast.makeText(this, String.valueOf(index), Toast.LENGTH_SHORT).show();
        }
        else {return false;}
        return true;
    }
}
