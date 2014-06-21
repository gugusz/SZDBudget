package android.szdb.mbaz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

/**
 * Klasa odpowiadajaca za wywwietlanie aktywnosci ustawienia
 * @author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 */
public class UstawieniaActivity extends Activity implements View.OnClickListener{

    private EditText editTextOd;
    private EditText editTextDo;
    private Button buttonDodaj;
    private ListView listViewUstawienia;
    private CBazaSystem bazaDanych;
    private RadioButton radioButtonOkr;
    private RadioButton radioButtonKDO;
    private RadioButton radioButtonKWY;
    private RadioButton radioButtonSUB;
    private List<COkres> listaOkr;
    private ArrayAdapter<COkres> adapterOkr;
    private List<CKat_doch> listaKdo;
    private ArrayAdapter<CKat_doch> adapterKdo;
    private List<CKat_wyd> listaKwy;
    private ArrayAdapter<CKat_wyd> adapterKwy;
    private List<CSubkategoria> listaSub;
    private ArrayAdapter<CSubkategoria> adapterSub;

    /**
     * Metoda bedaca w pewnym sensie konstruktorem. Wywolywana jest podczas tworzenia aktywnosci. Przypisuje id kontrolek do pol klasy
     * Otwiera baze danych, tworzy ArrayAdapter w ktorym przechowaywane sa rekordy bazy, ustawia OnClickListenera
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawienia);
        editTextOd = (EditText)findViewById(R.id.editTextUstawieniaOd);
        editTextDo = (EditText)findViewById(R.id.editTextUstawieniaDo);
        buttonDodaj = (Button) findViewById(R.id.buttonUstawienia);
        listViewUstawienia = (ListView)findViewById(R.id.listViewUstawienia);
        radioButtonOkr = (RadioButton)findViewById(R.id.radioButtonUstawieniaOkres);
        radioButtonKDO = (RadioButton)findViewById(R.id.radioButtonUstawieniaKDO);
        radioButtonKWY = (RadioButton)findViewById(R.id.radioButtonUstawieniaKWY);
        radioButtonSUB = (RadioButton)findViewById(R.id.radioButtonUstawieniaSUB);

        buttonDodaj.setOnClickListener(this);
        radioButtonOkr.setOnClickListener(this);
        radioButtonKDO.setOnClickListener(this);
        radioButtonKWY.setOnClickListener(this);
        radioButtonSUB.setOnClickListener(this);
        registerForContextMenu(listViewUstawienia);

        bazaDanych = new CBazaSystem(this);
        bazaDanych.open();

        listaOkr = bazaDanych.zwrocOkresy();
        adapterOkr = new ArrayAdapter<COkres>(this, android.R.layout.simple_list_item_1, listaOkr);
        listViewUstawienia.setAdapter(adapterOkr);

        listaKdo = bazaDanych.zwrocKDO();
        adapterKdo = new ArrayAdapter<CKat_doch>(this, android.R.layout.simple_list_item_1, listaKdo);

        listaKwy = bazaDanych.zwrocKWY();
        adapterKwy = new ArrayAdapter<CKat_wyd>(this, android.R.layout.simple_list_item_1, listaKwy);

        listaSub = bazaDanych.zwrocSubkategorie();
        adapterSub = new ArrayAdapter<CSubkategoria>(this, android.R.layout.simple_list_item_1, listaSub);
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

        Intent intent = new Intent(this, MainActivity.class);
        switch (view.getId()){
            case R.id.buttonUstawienia:
                if (radioButtonOkr.isChecked()) {
                    ArrayAdapter<COkres> adapter = (ArrayAdapter<COkres>) listViewUstawienia.getAdapter();
                    COkres okr = bazaDanych.dodajOkres(editTextOd.getText().toString(), editTextDo.getText().toString());
                    adapter.add(okr);
                    editTextOd.setText(null);
                    editTextDo.setText(null);
                    editTextOd.setHint("Podaj datę rozpoczęcia okresu");
                    editTextDo.setHint("Podaj datę zakończenia okresu");
                }
                else if (radioButtonKDO.isChecked()) {
                    ArrayAdapter<CKat_doch> adapter = (ArrayAdapter<CKat_doch>) listViewUstawienia.getAdapter();
                    CKat_doch kdo = bazaDanych.dodajKDO(editTextOd.getText().toString());
                    adapter.add(kdo);
                    editTextOd.setText(null);
                    editTextOd.setHint("Podaj nazwę kategorii dochodów");
                }
                else if (radioButtonKWY.isChecked()) {
                    ArrayAdapter<CKat_wyd> adapter = (ArrayAdapter<CKat_wyd>) listViewUstawienia.getAdapter();
                    CKat_wyd kwy = bazaDanych.dodajKWY(editTextOd.getText().toString());
                    adapter.add(kwy);
                    editTextOd.setText(null);
                    editTextOd.setHint("Podaj nazwę kategorii wydatków");
                }
                else {
                    ArrayAdapter<CSubkategoria> adapter = (ArrayAdapter<CSubkategoria>) listViewUstawienia.getAdapter();
                    CSubkategoria sub = bazaDanych.dodajSubkategorie(editTextOd.getText().toString());
                    adapter.add(sub);
                    editTextOd.setText(null);
                    editTextOd.setHint("Podaj nazwę subkategorii wydatków");
                }
                break;
            case R.id.radioButtonUstawieniaOkres:
                editTextOd.setHint("Podaj datę rozpoczęcia okresu");
                editTextOd.setInputType(InputType.TYPE_CLASS_DATETIME);
                editTextDo.setHint("Podaj datę zakończenia okresu");
                editTextDo.setVisibility(View.VISIBLE);
                listViewUstawienia.setAdapter(adapterOkr);
                break;
            case R.id.radioButtonUstawieniaKDO:
                editTextOd.setHint("Podaj nazwę kategorii dochodów");
                editTextOd.setInputType(InputType.TYPE_CLASS_TEXT);
                editTextDo.setVisibility(View.INVISIBLE);
                listViewUstawienia.setAdapter(adapterKdo);
                break;
            case R.id.radioButtonUstawieniaKWY:
                editTextOd.setHint("Podaj nazwę kategorii wydatków");
                editTextOd.setInputType(InputType.TYPE_CLASS_TEXT);
                editTextDo.setVisibility(View.INVISIBLE);
                listViewUstawienia.setAdapter(adapterKwy);
                break;
            case R.id.radioButtonUstawieniaSUB:
                editTextOd.setHint("Podaj nazwę subkategorii wydatków");
                editTextOd.setInputType(InputType.TYPE_CLASS_TEXT);
                editTextDo.setVisibility(View.INVISIBLE);
                listViewUstawienia.setAdapter(adapterSub);
                break;
            case R.id.home:
                setResult(Activity.RESULT_CANCELED, intent);
                bazaDanych.close();
                Toast.makeText(this, "COfnięto", Toast.LENGTH_LONG).show();
                finish();
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
            if (radioButtonOkr.isChecked()) {
                bazaDanych.usunOkres(listaOkr.get(index));
                listaOkr.remove(index);
                adapterOkr.notifyDataSetChanged();
            }
            else if (radioButtonKDO.isChecked()) {
                bazaDanych.usunKDO(listaKdo.get(index));
                listaKdo.remove(index);
                adapterKdo.notifyDataSetChanged();
            }
            else if (radioButtonKWY.isChecked()) {
                bazaDanych.usunKWY(listaKwy.get(index));
                listaKwy.remove(index);
                adapterKwy.notifyDataSetChanged();
            }
            else {
                bazaDanych.usunSubkategorie(listaSub.get(index));
                listaSub.remove(index);
                adapterSub.notifyDataSetChanged();
            }
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
