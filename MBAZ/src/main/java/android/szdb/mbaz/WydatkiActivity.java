package android.szdb.mbaz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Klasa odpowiadajaca za wywwietlanie aktywnosci wydatki
 * @author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 */
public class WydatkiActivity extends Activity {

    private CBazaSystem bazaDanych;
    private List<CWydatki> lista;
    private ArrayWydatkiAdapter adapter;

    /**
     * Metoda bedaca w pewnym sensie konstruktorem. Wywolywana jest podczas tworzenia aktywnosci. Przypisuje id kontrolek do pol klasy
     * Otwiera baze danych, tworzy ArrayAdapter w ktorym przechowaywane sa rekordy bazy,
     * @param savedInstanceState stan instancji
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wydatki);
        ListView listViewWydatki = (ListView) findViewById(R.id.listViewWydatki);
        registerForContextMenu(listViewWydatki);

        bazaDanych = new CBazaSystem(this);
        bazaDanych.open();

        List<CKat_wyd> kategorie = bazaDanych.zwrocKWY();
        List<CSubkategoria> subkategoria = bazaDanych.zwrocSubkategorie();
        lista = bazaDanych.zwrocWydatki();
        adapter = new ArrayWydatkiAdapter(this, R.layout.textview_adapter, lista, kategorie, subkategoria);
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
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        setResult(Activity.RESULT_CANCELED, intent);
        bazaDanych.close();
        finish();
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
            assert info != null;
            int index = info.position;
            bazaDanych.usunWydatki(lista.get(index));
            lista.remove(index);
            adapter.notifyDataSetChanged();
        }
        else if(item.getTitle()=="Edytuj wpis"){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            assert info != null;
            int index = info.position;
            Toast.makeText(this, String.valueOf(index), Toast.LENGTH_SHORT).show();
        }
        else {return false;}
        return true;
    }
}
