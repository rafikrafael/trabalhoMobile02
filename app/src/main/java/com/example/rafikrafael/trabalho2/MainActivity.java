package com.example.rafikrafael.trabalho2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import Tasks.AsyncTasksValidarJogo;
import adapters.AdapterJogo;
import controller.ControllerJogo;
import entidades.Jogo;

public class MainActivity extends AppCompatActivity {

    private Button btnNovo;
    private Button btnConsultar;
    private ListView listaJogos;
    private AdapterJogo adapterJogo;

    private ControllerJogo controllerJogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controllerJogo = new ControllerJogo(this);

        listaJogos = (ListView) findViewById(R.id.listaJogos);
        btnNovo = (Button) findViewById(R.id.btnNovo);

        listaJogos.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view,
                                            ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(Menu.NONE, 1, Menu.NONE, "Consultar Concurso");
                contextMenu.add(Menu.NONE, 2, Menu.NONE, "Excluir");
            }
        });

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        switch (item.getItemId()) {
            case 1:
                consultarConcurso(position);
                break;
            case 2:
                deletar(position);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void consultarConcurso(int position) {
        Jogo jogo = (Jogo) adapterJogo.getItem(position);
        if (jogo != null) {
            AsyncTasksValidarJogo asyncTasksValidarJogo = new AsyncTasksValidarJogo(this) {
                @Override
                protected void onPostExecute(Jogo jogo) {
                    super.onPostExecute(jogo);
                    atualizaLista();
                }
            };
            asyncTasksValidarJogo.execute(jogo);
        }
    }

    private void deletar(int position) {
        Jogo jogo = (Jogo) adapterJogo.getItem(position);
        if (jogo != null) {
            controllerJogo.remover(jogo);
            atualizaLista();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaLista();
    }

    private void atualizaLista() {
        List<Jogo> jogos = controllerJogo.getAllJogosOrderByLast();
        adapterJogo = new AdapterJogo(this, jogos);
        listaJogos.setAdapter(adapterJogo);
    }

    public void btnNovoCliecked(View view){
        Intent intent = new
                Intent(this, AddJogo.class);
        startActivity(intent);
    }

}
