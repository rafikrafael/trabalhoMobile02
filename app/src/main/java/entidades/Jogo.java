package entidades;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rafikrafael on 19/04/2018.
 */

public class Jogo {

    private Long id;

    private TipoJogo tipoJogo;

    private ArrayList<Integer> numeros = new ArrayList<>();

    private String numeroConcurso;

    private Date dataConcurso;

    private Integer qtdeAcertos;

    private String resultado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoJogo getTipoJogo() {
        return tipoJogo;
    }

    public void setTipoJogo(TipoJogo tipoJogo) {
        this.tipoJogo = tipoJogo;
    }

    public ArrayList<Integer> getNumeros() {
        return numeros;
    }

    public void addNumeros(Integer numeros) {
        this.numeros.add(numeros);
    }

    public String getNumeroConcurso() {
        return numeroConcurso;
    }

    public void setNumeroConcurso(String numeroConcurso) {
        this.numeroConcurso = numeroConcurso;
    }

    public Date getDataConcurso() {
        return dataConcurso;
    }

    public void setDataConcurso(Date dataConcurso) {
        this.dataConcurso = dataConcurso;
    }

    public String getDataConcursoStr() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(dataConcurso);
    }

    public Integer getQtdeAcertos() {
        return qtdeAcertos;
    }

    public void setQtdeAcertos(Integer qtdeAcertos) {
        this.qtdeAcertos = qtdeAcertos;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
