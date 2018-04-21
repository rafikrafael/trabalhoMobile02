package entidades;

/**
 * Created by rafikrafael on 19/04/2018.
 */

public enum TipoJogo {
    MEGASENA("Mega Sena", 0),
    LOTOFACIL("Loto Fácil", 1),
    QUINA("Quina", 2),
    DUPLASENA("Duplca Sena", 3),
    LOTOMANIA("Loto Mania", 4),
    TIMEMANIA("Time Mania", 5);

    private String stringValue;
    private int intValue;
    private TipoJogo(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    public static TipoJogo getTipoJogoFromInt(int tipo) {
        switch (tipo) {
            case 0: return MEGASENA;
            case 1: return LOTOFACIL;
            case 2: return QUINA;
            case 3: return DUPLASENA;
            case 4: return LOTOMANIA;
            case 5: return TIMEMANIA;
        }
        return MEGASENA;
    }

}
