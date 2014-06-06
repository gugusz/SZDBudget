package android.szdb.mbaz;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;


public class DochodyDodajActivity extends Activity implements View.OnClickListener{

    private EditText kwota;
    private EditText data;
    private Spinner spinnerDD;
    private EditText kategoria;
    private CheckBox opcja;
    private Button dodajKategorie;
    private Button dodajDochod;
    private CBazaSystem bazaDanych;
    private List<CKat_doch>listaKategoriiDochodow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dochody_dodaj);
        kwota = (EditText)findViewById(R.id.editTextDDochodyKwota);
        data = (EditText)findViewById(R.id.editTextDDochodyData);
        spinnerDD = (Spinner)findViewById(R.id.spinnerDDochody);
        kategoria = (EditText)findViewById(R.id.editTextDDochodyKategoria);
        opcja = (CheckBox)findViewById(R.id.checkBoxDDochody);
        dodajKategorie = (Button)findViewById(R.id.buttonDDochodyKategoria);
        dodajDochod = (Button)findViewById(R.id.buttonDDochodyDodaj);

        opcja.setOnClickListener(this);
        dodajKategorie.setOnClickListener(this);
        dodajDochod.setOnClickListener(this);

        bazaDanych = new CBazaSystem(this);
        bazaDanych.open();

        listaKategoriiDochodow = bazaDanych.zwrocKDO();
        ArrayAdapter<CKat_doch> adapterKDO = new ArrayAdapter<CKat_doch>(this, android.R.layout.simple_list_item_1, listaKategoriiDochodow);
        spinnerDD.setAdapter(adapterKDO);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dochody_dodaj, menu);
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
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.checkBoxDDochody:
                if(opcja.isChecked()){
                    kategoria.setEnabled(true);
                    dodajKategorie.setEnabled(true);
                }
                else{
                    kategoria.setEnabled(false);
                    dodajKategorie.setEnabled(false);
                }
                break;
            case R.id.buttonDDochodyDodaj:
                int tmp = listaKategoriiDochodow.get(spinnerDD.getSelectedItemPosition()).getKDOk_1_Id();// nie jestem pewnien co do tego
                //Toast.makeText(this,String.valueOf(tmp),Toast.LENGTH_SHORT).show();
                CDochody newDOC = bazaDanych.dodajDochody(Float.valueOf(kwota.getText().toString()),data.getText().toString(),tmp);
                break;
            case R.id.buttonDDochodyKategoria:
                ArrayAdapter<CKat_doch> adapter = (ArrayAdapter<CKat_doch>) spinnerDD.getAdapter();
                CKat_doch newKDO = bazaDanych.dodajKDO(kategoria.getText().toString());
                adapter.add(newKDO);
                kategoria.setText(null);
                kategoria.setHint("Podaj nazwę kategorii");
                kategoria.setEnabled(false);
                dodajKategorie.setEnabled(false);
                opcja.setChecked(false);
                break;
        }
    }
}
