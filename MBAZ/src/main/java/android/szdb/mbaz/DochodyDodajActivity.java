package android.szdb.mbaz;

import android.app.Activity;
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

    private EditText nazwa;
    private EditText data;
    private Spinner lista;
    private EditText kategoria;
    private CheckBox opcja;
    private Button dodajKategorie;
    private Button dodajDochod;
    private CBazaSystem bazaDanych;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dochody_dodaj);
        nazwa = (EditText)findViewById(R.id.editTextDDochodyNazwa);
        data = (EditText)findViewById(R.id.editTextDDochodyData);
        lista = (Spinner)findViewById(R.id.spinnerDDochody);
        kategoria = (EditText)findViewById(R.id.editTextDDochodyKategoria);
        opcja = (CheckBox)findViewById(R.id.checkBoxDDochody);
        dodajKategorie = (Button)findViewById(R.id.buttonDDochodyKategoria);
        dodajDochod = (Button)findViewById(R.id.buttonDDochodyDodaj);

        bazaDanych = new CBazaSystem(this);
        bazaDanych.open();

        //List<CKat_doch>listaKategoriiDochodow = bazaDanych.zwrocKDO();
        //ArrayAdapter<CKat_doch> adapterKDO = new ArrayAdapter<CKat_doch>(this, android.R.layout.simple_list_item_1, listaKategoriiDochodow);
        //lista.setAdapter(adapterKDO);

        opcja.setOnClickListener(this);
        dodajKategorie.setOnClickListener(this);
        dodajDochod.setOnClickListener(this);
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
                //Toast.makeText(this,"Dupa",Toast.LENGTH_SHORT).show();
                if(opcja.isChecked()){
                    //Toast.makeText(this,"Dupa1",Toast.LENGTH_SHORT).show();
                    //opcja.setChecked(false);
                    kategoria.setEnabled(true);
                    dodajKategorie.setEnabled(true);
                }
                else{
                    //Toast.makeText(this,"Dupa2",Toast.LENGTH_SHORT).show();
                    //opcja.setChecked(false);
                    kategoria.setEnabled(false);
                    dodajKategorie.setEnabled(false);
                }
                break;
            case R.id.buttonDDochodyDodaj:
                break;
            case R.id.buttonDDochodyKategoria:
                CKat_doch newKDO = bazaDanych.dodajKDO(kategoria.getText().toString());
                break;
        }
    }
}
