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
import android.widget.ToggleButton;

import java.util.List;

/**
 * Klasa odpowiadajaca za wywwietlanie aktywnosci dodaj wydatki
 * @author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 */
public class WydatkiDodajActivity extends Activity implements View.OnClickListener{

    private EditText kwota;
    private EditText data;
    private Spinner spinnerDW1;
    private Spinner spinnerDW2;
    private EditText kategoria;
    private CheckBox opcja;
    private Button dodajKategorie;
    private CBazaSystem bazaDanych;
    private List<CKat_wyd> listaKategoriiWydatkow;
    private List<CSubkategoria> listaSubkategoriiWydatkow;
    private ToggleButton przelacznik;

    /**
     * Metoda bedaca w pewnym sensie konstruktorem. Wywolywana jest podczas tworzenia aktywnosci. Przypisuje id kontrolek do pol klasy
     * Otwiera baze danych, tworzy ArrayAdapter w ktorym przechowaywane sa rekordy bazy, ustawia OnClickListenera
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wydatki_dodaj);
        kwota = (EditText)findViewById(R.id.editTextDWydatkiKwota);
        data = (EditText)findViewById(R.id.editTextDWydatkiData);
        spinnerDW1 = (Spinner)findViewById(R.id.spinnerDWydatki1);
        spinnerDW2 = (Spinner)findViewById(R.id.spinnerDWydatki2);
        kategoria = (EditText)findViewById(R.id.editTextDWydatkiKategoria);
        opcja = (CheckBox)findViewById(R.id.checkBoxDWydatki);
        dodajKategorie = (Button)findViewById(R.id.buttonDWydatkiKategoria);
        Button dodajWydatek = (Button) findViewById(R.id.buttonDWydatkiDodaj);
        przelacznik = (ToggleButton)findViewById(R.id.toggleButtonDWydatki);

        opcja.setOnClickListener(this);
        przelacznik.setOnClickListener(this);
        dodajKategorie.setOnClickListener(this);
        dodajWydatek.setOnClickListener(this);

        bazaDanych = new CBazaSystem(this);
        bazaDanych.open();

        listaKategoriiWydatkow = bazaDanych.zwrocKWY();
        ArrayAdapter<CKat_wyd> adapterKWY = new ArrayAdapter<CKat_wyd>(this, android.R.layout.simple_list_item_1, listaKategoriiWydatkow);
        spinnerDW1.setAdapter(adapterKWY);
        listaSubkategoriiWydatkow = bazaDanych.zwrocSubkategorie();
        ArrayAdapter<CSubkategoria> adapterSUB = new ArrayAdapter<CSubkategoria>(this, android.R.layout.simple_list_item_1, listaSubkategoriiWydatkow);
        spinnerDW2.setAdapter(adapterSUB);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wydatki_dodaj, menu);
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

    /**
     * Metoda odopwiadajaca za obluge eventow jakie zaszly za pomoca kliknieca
     * @param view kontrolka klay View
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.checkBoxDWydatki:
                if(opcja.isChecked()){
                    kategoria.setEnabled(true);
                    dodajKategorie.setEnabled(true);
                    przelacznik.setEnabled(true);
                }
                else{
                    kategoria.setEnabled(false);
                    dodajKategorie.setEnabled(false);
                    przelacznik.setEnabled(false);
                }
                break;
            case R.id.toggleButtonDWydatki:
                if(przelacznik.isChecked()){
                    kategoria.setHint("Podaj nazwę subkategorii");
                    dodajKategorie.setText("Dodaj subkategorię");
                }
                else {
                    kategoria.setHint("Podaj nazwę kategorii");
                    dodajKategorie.setText("Dodaj kategorię");
                }
                break;
            case R.id.buttonDWydatkiDodaj:
                int tmp1 = listaKategoriiWydatkow.get(spinnerDW1.getSelectedItemPosition()).getKWYk_1_Id();// nie jestem pewnien co do tego
                int tmp2 = listaSubkategoriiWydatkow.get(spinnerDW2.getSelectedItemPosition()).getSUBk_1_Id();// nie jestem pewnien co do tego
                if (kategoria.getText().toString().equals("brak")) {
                    bazaDanych.dodajWydatkiNull(Float.valueOf(kwota.getText().toString()), data.getText().toString(), tmp1);
                }
                else {
                    bazaDanych.dodajWydatki(Float.valueOf(kwota.getText().toString()), data.getText().toString(), tmp1, tmp2);
                }
                kwota.setHint("Podaj kwotę");
                kwota.setText(null);
                data.setHint("Podaj datę dochodu");
                data.setText(null);
                break;
            case R.id.buttonDWydatkiKategoria:
                //dopicać w zależności od toggle buttona
                if (przelacznik.isChecked()) {
                    if (kategoria.getText().toString().equals("brak")) {
                        Toast.makeText(this, "Nazwa zastrzeżona", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ArrayAdapter<CSubkategoria> adapter = (ArrayAdapter<CSubkategoria>) spinnerDW2.getAdapter();
                        CSubkategoria newSUB = bazaDanych.dodajSubkategorie(kategoria.getText().toString());
                        adapter.add(newSUB);
                        kategoria.setText(null);
                        kategoria.setHint("Podaj nazwę subkategorii");
                        kategoria.setEnabled(false);
                        dodajKategorie.setEnabled(false);
                        opcja.setChecked(false);
                    }
                }
                else {
                    ArrayAdapter<CKat_wyd> adapter = (ArrayAdapter<CKat_wyd>) spinnerDW1.getAdapter();
                    CKat_wyd newKWY = bazaDanych.dodajKWY(kategoria.getText().toString());
                    adapter.add(newKWY);
                    kategoria.setText(null);
                    kategoria.setHint("Podaj nazwę kategorii");
                    kategoria.setEnabled(false);
                    dodajKategorie.setEnabled(false);
                    opcja.setChecked(false);
                }
                break;
        }
    }
}
