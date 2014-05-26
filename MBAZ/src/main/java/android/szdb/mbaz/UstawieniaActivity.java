package android.szdb.mbaz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class UstawieniaActivity extends Activity implements View.OnClickListener{

    private EditText editTextOd;
    private EditText editTextDo;
    private ListView listViewUstawienia;
    private CBazaSystem bazaDanych;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawienia);
        editTextOd = (EditText)findViewById(R.id.editTextUstawieniaOd);
        editTextDo = (EditText)findViewById(R.id.editTextUstawieniaDo);
        Button buttonDodaj = (Button) findViewById(R.id.buttonUstawienia);
        listViewUstawienia = (ListView)findViewById(R.id.listViewUstawienia);

        buttonDodaj.setOnClickListener(this);

        bazaDanych = new CBazaSystem(this);
        bazaDanych.open();

        List<COkres> lista = bazaDanych.zwrocOkresy();
        ArrayAdapter<COkres> adapter = new ArrayAdapter<COkres>(this, android.R.layout.simple_list_item_1, lista);
        listViewUstawienia.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ustawienia, menu);
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
    public void onClick(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        switch (view.getId()){
            case R.id.buttonUstawienia:
                ArrayAdapter<COkres> adapter = (ArrayAdapter<COkres>) listViewUstawienia.getAdapter();
                COkres okr = bazaDanych.dodajOkres(editTextOd.getText().toString(), editTextDo.getText().toString());
                adapter.add(okr);
                editTextOd.setText(null);
                editTextDo.setText(null);
                editTextOd.setHint("Podaj datę rozpoczęcia okresu");
                editTextDo.setHint("Podaj datę zakończenia okresu");
                break;
            case R.id.home:
                setResult(Activity.RESULT_CANCELED, intent);
                bazaDanych.close();
                Toast.makeText(this, "COfnięto", Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }
}
