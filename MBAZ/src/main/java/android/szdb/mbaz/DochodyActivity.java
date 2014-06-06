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


public class DochodyActivity extends Activity implements View.OnCreateContextMenuListener{

    private ListView listViewDochody;
    private CBazaSystem bazaDanych;
    private List<CDochody> lista;
    private ArrayAdapter<CDochody> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dochody);
        listViewDochody = (ListView)findViewById(R.id.listViewDochody);
        registerForContextMenu(listViewDochody);

        bazaDanych = new CBazaSystem(this);
        bazaDanych.open();

        lista = bazaDanych.zwrocDochody();
        adapter = new ArrayAdapter<CDochody>(this, android.R.layout.simple_list_item_1, lista);
        listViewDochody.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dochody, menu);
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Wybierz czynność:");
        menu.add(0, v.getId(), 0, "Usuń wpis");
        menu.add(0, v.getId(), 0, "Edytuj wpis");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle()=="Usuń wpis") {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;
            bazaDanych.usunDochody(lista.get(index));
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
}
