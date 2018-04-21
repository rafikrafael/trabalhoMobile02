package adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rafikrafael.trabalho2.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import entidades.Jogo;

/**
 * Created by rafikrafael on 20/04/2018.
 */

public class AdapterJogo extends BaseAdapter {

    Context context;
    List<Jogo> jogos;

    public AdapterJogo(
            Context context,
            List<Jogo> jogos) {
        this.context = context;
        this.jogos = jogos;
    }

    @Override
    public int getCount() {
        return jogos.size();
    }

    @Override
    public Object getItem(int position) {
        return jogos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return jogos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewLinha =
                LayoutInflater.from(context).
                        inflate(R.layout.lista_jogos, parent,
                                false);
        TextView tipoJogo = (TextView) viewLinha.findViewById(R.id.textTipoJogo);
        TextView concurso = (TextView) viewLinha.findViewById(R.id.textConcurso);
        TextView data = (TextView) viewLinha.findViewById(R.id.textDataConcurso);
        TextView numeros = (TextView) viewLinha.findViewById(R.id.textNumeros);
        TextView qtdeAcertos = (TextView) viewLinha.findViewById(R.id.textQtdeAcertos);
        TextView acertos = (TextView) viewLinha.findViewById(R.id.textView5);
        TextView resultado = (TextView) viewLinha.findViewById(R.id.textResultado);
        TextView resultadoValor = (TextView) viewLinha.findViewById(R.id.textResultadoValor);

        Jogo jogo = jogos.get(position);

        tipoJogo.setText(jogo.getTipoJogo().toString());
        concurso.setText(jogo.getNumeroConcurso());
        data.setText(jogo.getDataConcursoStr());
        numeros.setText(jogo.getNumeros().toString());
        qtdeAcertos.setText("");

        if (jogo.getResultado() == null) {
            acertos.setVisibility(View.INVISIBLE);
            qtdeAcertos.setVisibility(View.INVISIBLE);
            resultado.setVisibility(View.INVISIBLE);
            resultadoValor.setVisibility(View.INVISIBLE);
        } else {
            resultadoValor.setText(jogo.getResultado());
            if (jogo.getQtdeAcertos() != null) {
                qtdeAcertos.setText(jogo.getQtdeAcertos().toString());
            }
        }

        return viewLinha;
    }


}
