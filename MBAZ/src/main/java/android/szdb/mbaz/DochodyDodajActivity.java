package android.szdb.mbaz;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Klasa odpowiadajaca za wywwietlanie aktywnosci dodaj dochody
 * @author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 */
public class DochodyDodajActivity extends Activity implements View.OnClickListener{

    private EditText kwota;
    private EditText data;
    private Spinner spinnerDD;
    private EditText kategoria;
    private CheckBox opcja;
    private Button dodajKategorie;
    private CBazaSystem bazaDanych;
    private List<CKat_doch>listaKategoriiDochodow;
    private Calendar myCalendar = Calendar.getInstance();

    /**
     * Metoda bedaca w pewnym sensie konstruktorem. Wywolywana jest podczas tworzenia aktywnosci. Przypisuje id kontrolek do pol klasy, ustawia OnClickListenera
     * tworzy baze danych, ArrayAdaptera w ktrym sa rekordy
     * @param savedInstanceState
     */
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
        Button dodajDochod = (Button) findViewById(R.id.buttonDDochodyDodaj);

        opcja.setOnClickListener(this);
        dodajKategorie.setOnClickListener(this);
        dodajDochod.setOnClickListener(this);
        data.setOnClickListener(this);

        bazaDanych = new CBazaSystem(this);
        bazaDanych.open();

        listaKategoriiDochodow = bazaDanych.zwrocKDO();
        ArrayAdapter<CKat_doch> adapterKDO = new ArrayAdapter<CKat_doch>(this, android.R.layout.simple_list_item_1, listaKategoriiDochodow);
        spinnerDD.setAdapter(adapterKDO);
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        data.setText(sdf.format(myCalendar.getTime()));
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
     * Metoda odopwiadajaca za obluge eventow jakie zaszly za pomoca kliknieca
     * @param view kontrolka klay View
     */
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
                if (kwota.getText().toString().isEmpty() && data.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Wypełnij wszystkie pola!", Toast.LENGTH_SHORT).show();
                    break;
                }
                int tmp = listaKategoriiDochodow.get(spinnerDD.getSelectedItemPosition()).getKDOk_1_Id();// nie jestem pewnien co do tego
                bazaDanych.dodajDochody(Float.valueOf(kwota.getText().toString()),data.getText().toString(),tmp);
                kwota.setHint("Podaj kwotę");
                kwota.setText(null);
                data.setHint("Podaj datę dochodu");
                data.setText(null);
                break;
            case R.id.buttonDDochodyKategoria:
                if (kategoria.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Wypełnij wszystkie pola!", Toast.LENGTH_SHORT).show();
                    break;
                }
                ArrayAdapter<CKat_doch> adapter = (ArrayAdapter<CKat_doch>) spinnerDD.getAdapter();
                CKat_doch newKDO = bazaDanych.dodajKDO(kategoria.getText().toString());
                adapter.add(newKDO);
                kategoria.setText(null);
                kategoria.setHint("Podaj nazwę kategorii");
                kategoria.setEnabled(false);
                dodajKategorie.setEnabled(false);
                opcja.setChecked(false);
                break;
            case R.id.editTextDDochodyData:
                new DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }
}
