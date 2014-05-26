package android.szdb.mbaz;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PlanyActivity extends Activity implements View.OnClickListener{

    private EditText nazwa;
    private EditText od;
    private EditText dataZakupu;
    private Button buttonDodaj;
    private CBazaSystem bazaDanych;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plany);
        nazwa = (EditText)findViewById(R.id.editTextPlany1);
        od = (EditText)findViewById(R.id.editTextPlany2);
        dataZakupu = (EditText)findViewById(R.id.editTextPlanowanie3);
        buttonDodaj = (Button)findViewById(R.id.buttonPlany);

        bazaDanych = new CBazaSystem(this);
        bazaDanych.open();
        buttonDodaj.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.plany, menu);
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
            case R.id.buttonPlany:
                //Toast.makeText(this,nazwa.getText().toString()+ od.getText().toString()+ dataZakupu.getText().toString(),Toast.LENGTH_SHORT).show();
                CPlanowanie newPlan = bazaDanych.dodajPlan(nazwa.getText().toString(), od.getText().toString(), dataZakupu.getText().toString());
                break;
        }
    }
}
