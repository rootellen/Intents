package br.edu.ifsp.intents;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import br.edu.ifsp.intents.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    public static final String PARAMETRO = "PARAMETRO";

    private final int OUTRA_ACTIVITY_REQUEST_CODE = 0;

    private ActivityResultLauncher<Intent> outraActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(""+getString(R.string.app_name)+"/"+getLocalClassName(), "onCreate: Inicio CC");
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        getSupportActionBar().setTitle("Tratando Intents");
        getSupportActionBar().setSubtitle("Principais tipos");

        outraActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK){
                        String retorno = result.getData().getStringExtra(PARAMETRO);
                        activityMainBinding.retornoTV.setText(retorno);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.outraActivityMi: {
                //abrir outra activity
                Intent outraActivityIntent = new Intent(this, OutraActivity.class);
                outraActivityIntent.putExtra(PARAMETRO, activityMainBinding.parametroET.getText().toString());

                //passagem usando bundle
                /*Bundle parametrosBundle = new Bundle();
                parametrosBundle.putString(PARAMETRO, activityMainBinding.parametroET.getText().toString());
                outraActivityIntent.putExtras(parametrosBundle);
                */


                //startActivityForResult(outraActivityIntent, OUTRA_ACTIVITY_REQUEST_CODE);
                outraActivityResultLauncher.launch(outraActivityIntent);
                return true;
            }
            case R.id.viewMi: {

                return true;
            }
            case R.id.callMi: {

                return true;
            }
            case R.id.dialMi: {

                return true;
            }
            case R.id.pickMi: {

                return true;
            }
            case R.id.chooserMi: {

                return true;
            }
            default: {
                return false;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(""+getString(R.string.app_name)+"/"+getLocalClassName(), "onStart: Inicio CV");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(""+getString(R.string.app_name)+"/"+getLocalClassName(), "onResume: Inicio CPP");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(""+getString(R.string.app_name)+"/"+getLocalClassName(), "onPause: Fim CPP");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(""+getString(R.string.app_name)+"/"+getLocalClassName(), "onStop: Fim CV");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(""+getString(R.string.app_name)+"/"+getLocalClassName(), "onDestroy: Fim CC");
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == OUTRA_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
//            String retorno = data.getStringExtra(PARAMETRO);
//            activityMainBinding.retornoTV.setText(retorno);
//        }
//    }
}