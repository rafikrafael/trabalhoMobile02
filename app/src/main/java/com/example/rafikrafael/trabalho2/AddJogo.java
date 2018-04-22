package com.example.rafikrafael.trabalho2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import controller.ControllerJogo;
import entidades.Jogo;
import entidades.TipoJogo;

public class AddJogo extends AppCompatActivity {

    private Button btnSalvar;
    private Button btnCancelar;

    private EditText editConcurso;
    private EditText editDataConcurso;

    private AutoCompleteTextView autoCompleteTipoJogo;

    private EditText editNumero;
    private EditText editNumeros;

    private Button btnAdicionarNumero;
    private Button btnLimparNumeros;

    private List<Integer> numeros = new ArrayList<Integer>();
    private ControllerJogo controllerJogo;

    private Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jogo);

        editConcurso = (EditText) findViewById(R.id.editConcurso);
        editDataConcurso = (EditText) findViewById(R.id.editDataConcurso);
        addDateToEditDataConcurso();

        editDataConcurso.setFocusable(false);
        editDataConcurso.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddJogo.this, datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        mapearAutoComplete();
        editNumero = (EditText) findViewById(R.id.editNumero);
        btnAdicionarNumero = (Button) findViewById(R.id.btnAddNumero);
        btnLimparNumeros = (Button) findViewById(R.id.btnLimparNumeros);
        editNumeros = (EditText) findViewById(R.id.editNumeros);
        editNumeros.setEnabled(false);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        controllerJogo = new ControllerJogo(this);

    }

    private void mapearAutoComplete() {
        List<String> tiposJogos = new ArrayList<String>();

        for (TipoJogo tipo: TipoJogo.values()) {
            tiposJogos.add(tipo.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, tiposJogos.toArray(new String[0]));
        //Getting the instance of AutoCompleteTextView
        autoCompleteTipoJogo= (AutoCompleteTextView) findViewById(R.id.autoCompleteTipoJogo);
        autoCompleteTipoJogo.setAdapter(adapter);
        autoCompleteTipoJogo.setText(tiposJogos.get(0));
    }

    public void btnSalvarClicked(View view){
        if (validacoes()) {
            Jogo jogo = new Jogo();
            jogo.setDataConcurso(myCalendar.getTime());
            jogo.setNumeroConcurso(editConcurso.getText().toString());
            String tipoStr = autoCompleteTipoJogo.getText().toString();
            TipoJogo tipo = null;
            for (TipoJogo tipo_: TipoJogo.values()) {
                if (tipo_.toString().equals(tipoStr)) {
                    tipo = tipo_;
                    Log.i("tipo", tipo.toString());
                    break;
                }
            }
            jogo.setTipoJogo(tipo);
            for (Integer numero: numeros) {
                jogo.addNumeros(numero);
            }
            controllerJogo.save(jogo);
            finish();
        }
    }

    public void btnCancelarClicked(View view){
        finish();
    }

    public void mostraNumeros() {
        String num = "";
        for (Integer numero: numeros) {
            if (!num.equals("")) {
                num += " ";
            }
            num += numero.toString();
        }
        editNumeros.setText(num);
    }

    public void btnAdicionarNumeroClicked(View view){
        if (!editNumero.getText().equals("")) {
            numeros.add(Integer.valueOf(editNumero.getText().toString()));

            mostraNumeros();
            editNumero.setText("");
        }
    }

    public void btnLimparNumerosClicked(View view){
        if (numeros.size() > 0) {
            numeros.remove(numeros.size()-1);
        }
        mostraNumeros();
    }

    DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            addDateToEditDataConcurso();
        }

    };

    private void addDateToEditDataConcurso() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        editDataConcurso.setText(sdf.format(myCalendar.getTime()));
    }

    private Boolean validacoes() {
        String msg = "";

        if (editConcurso.getText().toString().equals("")) {
            msg += "O concurso deve ser informado.";
        }

        if (editNumeros.getText().toString().equals("")) {
            msg += "Deve ser informado os numeros do jogo.";
        }

        if (autoCompleteTipoJogo.getText().toString().equals("")) {
            msg += "Deve ser informado o Tipo do Jogo.";
        }

        if (msg.equals("")) {
            return true;
        } else {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
