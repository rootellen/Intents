package br.edu.ifsp.intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import br.edu.ifsp.intents.databinding.ActivityOutraBinding;

public class OutraActivity extends AppCompatActivity {

    private ActivityOutraBinding activityOutraBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOutraBinding = ActivityOutraBinding.inflate(getLayoutInflater());
        setContentView(activityOutraBinding.getRoot());

        getSupportActionBar().setTitle("Outra Activity");
        getSupportActionBar().setSubtitle("Recebe e retorna um valor");

        Log.v(""+getString(R.string.app_name)+"/"+getLocalClassName(), "onCreate: Inicio CC");

        //recebendo 1
        /*
        Bundle parametroBundle = getIntent().getExtras();
        if(parametroBundle != null){
            String parametro = parametroBundle.getString(MainActivity.PARAMETRO, "");
            activityOutraBinding.recebidoTV.setText(parametro);
        }*/

        String parametro = getIntent().getStringExtra(MainActivity.PARAMETRO);
        if(parametro != null){
            activityOutraBinding.recebidoTV.setText(parametro);
        }



        activityOutraBinding.retornoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String retorno = activityOutraBinding.retornoET.getText().toString();
                Intent retornoIntent = new Intent();
                retornoIntent.putExtra(MainActivity.PARAMETRO, retorno);
                setResult(RESULT_OK, retornoIntent);
                finish();
            }
        });
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
}