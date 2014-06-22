package android.szdb.mbaz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;


public class ZestawienieActivity extends Activity {

    private Spinner okresy;
    private ListView zestawienie;
    private List<COkres> listaOkres;
    private ArrayAdapter<COkres> adapterOkres;
    private CBazaSystem bazaDanych;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zestawienie);
        okresy = (Spinner) findViewById(R.id.spinnerZestawienie);
        zestawienie = (ListView) findViewById(R.id.listViewZestawienie);

        bazaDanych = new CBazaSystem(this);
        bazaDanych.open();

        listaOkres = bazaDanych.zwrocOkresy();
        adapterOkres = new ArrayAdapter<COkres>(this, android.R.layout.simple_list_item_1, listaOkres);
        okresy.setAdapter(adapterOkres);
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
}
