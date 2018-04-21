package controller;

import android.content.Context;

import java.util.List;

import DAO.DAOJogo;
import entidades.Jogo;

/**
 * Created by rafikrafael on 19/04/2018.
 */

public class ControllerJogo {

    DAOJogo daoJogo;

    public ControllerJogo(Context context){
        daoJogo = new DAOJogo(context);
    }

    public List<Jogo> getAllJogosOrderByLast() {
        return daoJogo.getAll(" dataConcurso desc");
    }

    public void save(Jogo jogo) {
        daoJogo.save(jogo);
    }

    public void remover(Jogo jogo) {
        daoJogo.remover(jogo);
    }

}
