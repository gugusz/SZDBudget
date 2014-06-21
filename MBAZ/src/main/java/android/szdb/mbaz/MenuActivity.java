package android.szdb.mbaz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Klasa odpowiadajaca za wywwietlanie aktywnosci menu
 * @author Michal Bednarz & Adrian Zyzda
 * @version 1.0
 */
public class MenuActivity extends Activity implements View.OnClickListener{

    /**
     * Metoda bedaca w pewnym sensie konstruktorem. Wywolywana jest podczas tworzenia aktywnosci. Przypisuje id kontrolek do pol klasy, ustawia OnClickListenera
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button buttonMenuZestawienie = (Button) findViewById(R.id.buttonMenuZestawienie);
        Button buttonMenuDWydatki = (Button) findViewById(R.id.buttonMenuDWydatki);
        Button buttonMenuDDochody = (Button) findViewById(R.id.buttonMenuDDochody);
        Button buttonMenuWydatki = (Button) findViewById(R.id.buttonMenuWydatki);
        Button buttonMenuDochody = (Button) findViewById(R.id.buttonMenuDochody);
        Button buttonMenuPlanowanie = (Button) findViewById(R.id.buttonMenuPlanowanie);
        Button buttonMenuUstawienia = (Button) findViewById(R.id.buttonMenuUstawienia);
        Button buttonMenuKoniec = (Button) findViewById(R.id.buttonMenuClose);

        buttonMenuZestawienie.setOnClickListener(this);
        buttonMenuDWydatki.setOnClickListener(this);
        buttonMenuDDochody.setOnClickListener(this);
        buttonMenuWydatki.setOnClickListener(this);
        buttonMenuDochody.setOnClickListener(this);
        buttonMenuPlanowanie.setOnClickListener(this);
        buttonMenuUstawienia.setOnClickListener(this);
        buttonMenuKoniec.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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

        Intent intent;
        switch(view.getId()){
            case R.id.buttonMenuZestawienie:
                intent = new Intent(this, ZestawienieActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.buttonMenuDWydatki:
                intent = new Intent(this, WydatkiDodajActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.buttonMenuDDochody:
                intent = new Intent(this, DochodyDodajActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.buttonMenuWydatki:
                intent = new Intent(this, WydatkiActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.buttonMenuDochody:
                intent = new Intent(this, DochodyActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.buttonMenuPlanowanie:
                intent = new Intent(this, PlanyActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.buttonMenuUstawienia:
                intent = new Intent(this, UstawieniaActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.buttonMenuClose:
                //finish();
                System.exit(0);
                break;
        }
    }
}
