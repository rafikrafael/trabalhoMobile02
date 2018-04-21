package Tasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import adapters.AdapterJogo;
import controller.ControllerJogo;
import entidades.Jogo;
import entidades.TipoJogo;

/**
 * Created by rafikrafael on 21/04/2018.
 */

public class AsyncTasksValidarJogo extends AsyncTask<Jogo,Integer, Jogo> {

    // lotodicas.com.br/api/[lotofacil|mega-sena|quina|dupla-sena|lotomania|timenania]/NUMERO-DO-CONCURSO
    private final String url = "https://lotodicas.com.br/api/";
    private Context context;
    ControllerJogo controllerJogo;

    public AsyncTasksValidarJogo(Context context) {
        this.context = context;
        controllerJogo = new ControllerJogo(context);
    }
    
    private String getUrl(Jogo jogo) {
        switch (jogo.getTipoJogo()) {
            case MEGASENA: return url + "mega-sena/" + jogo.getNumeroConcurso();
            case DUPLASENA: return url + "dupla-sena/" + jogo.getNumeroConcurso();
            case LOTOFACIL: return url + "lotofacil/" + jogo.getNumeroConcurso();
            case LOTOMANIA: return url + "lotomania/" + jogo.getNumeroConcurso();
            case QUINA: return url + "quina/" + jogo.getNumeroConcurso();
            case TIMEMANIA: return url + "timemania/" + jogo.getNumeroConcurso();
        }
        return "";
    }

//    @Override
//    protected void onPostExecute(Jogo jogo) {
//        super.onPostExecute(jogo);
//    }

    @Override
    protected Jogo doInBackground(Jogo... jogos) {
        String dadosRetorno=null;
        try {
            URL url = new URL(getUrl(jogos[0]));
            InputStream stream = url.openStream();
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            onProgressUpdate(10);
            String linha;
            while ((linha = bufferedReader.readLine())!=null){
                builder.append(linha);
            }
            dadosRetorno = builder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("este", dadosRetorno);
        if (!dadosRetorno.equals("") && !dadosRetorno.equals("false")) {
            validaAcertos(dadosRetorno, jogos[0]);
        }
        return jogos[0];
    }
    
    private void validaAcertos(String dadosRetorno, Jogo jogo) {
        String resultado = dadosRetorno.substring(dadosRetorno.indexOf("\"sorteio\":["), dadosRetorno.indexOf("]"));
        resultado = resultado.replace("\"sorteio\":[", "").replace("]", "");
        String[] resArray = resultado.split(",");
        Integer acertos = 0;
        for (Integer numero: jogo.getNumeros()) {
            for (String v: resArray) {
                if (v.equals(numero.toString())) {
                    acertos ++;
                }
            }
        }
        jogo.setQtdeAcertos(acertos);
        jogo.setResultado(resultado);
        controllerJogo.save(jogo);
    }
}
