package br.edu.ifsp.intents;

import static android.content.Intent.ACTION_VIEW;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.edu.ifsp.intents.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    public static final String PARAMETRO = "PARAMETRO";

    //private final int OUTRA_ACTIVITY_REQUEST_CODE = 0;

    private ActivityResultLauncher<Intent> outraActivityResultLauncher;

    private ActivityResultLauncher<Intent> selecionarImagemActivityResultLauncher;

    private ActivityResultLauncher<Intent> escolherAplicativoActivityResultLauncher;

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

        selecionarImagemActivityResultLauncher = registerForActivityResult( new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK){
                        Intent visualizar = new Intent(ACTION_VIEW);
                        visualizar.setData(result.getData().getData());
                        startActivity(visualizar);
                    }
                });

        escolherAplicativoActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK){
                        Intent visualizar = new Intent(ACTION_VIEW);
                        visualizar.setData(result.getData().getData());
                        startActivity(visualizar);
                    }
                });

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
                Intent abrirNavegadorIntent = new Intent(ACTION_VIEW);
                abrirNavegadorIntent.setData(Uri.parse(activityMainBinding.parametroET.getText().toString()));
                startActivity(abrirNavegadorIntent);

                return true;
            }
            case R.id.callMi: {
                verifyCallPhonePermission();
                return true;
            }
            case R.id.dialMi: {
                Intent discarIntent = new Intent(Intent.ACTION_DIAL);
                discarIntent.setData(Uri.parse("tel: " + activityMainBinding.parametroET.getText().toString()));
                startActivity(discarIntent);
                return true;
            }
            case R.id.pickMi: {
                selecionarImagemActivityResultLauncher.launch(getPickageImageIntent());
                return true;
            }
            case R.id.chooserMi: {
                Intent escolherActivityIntent = new Intent(Intent.ACTION_CHOOSER);
                escolherActivityIntent.putExtra(Intent.EXTRA_INTENT, getPickageImageIntent());
                escolherActivityIntent.putExtra(Intent.EXTRA_TITLE, "Escolha um app para selecionar imagem");
                escolherAplicativoActivityResultLauncher.launch(escolherActivityIntent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
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

    private Intent getPickageImageIntent(){
        Intent pegarImagemIntent = new Intent(Intent.ACTION_PICK);
        String diretorioImagens = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
        pegarImagemIntent.setDataAndType(Uri.parse(diretorioImagens), "image/*");

        return pegarImagemIntent;
    }

    private void verifyCallPhonePermission() {
        Intent ligarIntent = new Intent(Intent.ACTION_CALL);
        ligarIntent.setData(Uri.parse("tel: " + activityMainBinding.parametroET.getText().toString()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                startActivity(ligarIntent);
            }   else{
                int CALL_PHONE_PERMISSION_REQUEST_CODE = 1;
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_REQUEST_CODE);
            }
        }   else{
            startActivity(ligarIntent);
        }

    }



}

