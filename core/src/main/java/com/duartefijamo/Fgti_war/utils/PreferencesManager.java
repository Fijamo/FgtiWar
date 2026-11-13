package com.duartefijamo.Fgti_war.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Gerenciador de persistência de dados do FgtiWar usando LibGDX Preferences.
 * Armazena progresso de fases desbloqueadas, melhores notas/pontuações e configurações.
 */
public class PreferencesManager {

    private static Preferences getPrefs() {
        return Gdx.app.getPreferences(Constants.PREFS_NAME);
    }

    /**
     * Retorna a maior fase desbloqueada pelo jogador.
     * @return Número da fase (1 por padrão).
     */
    public static int getFaseDesbloqueada() {
        return getPrefs().getInteger(Constants.PREF_FASE_DESBLOQUEADA, 1);
    }

    /**
     * Atualiza a fase desbloqueada se a nova fase for maior que a atual.
     * @param fase Número da fase conquistada.
     */
    public static void desbloquearFase(int fase) {
        int atual = getFaseDesbloqueada();
        if (fase > atual && fase <= Constants.TOTAL_FASES) {
            Preferences prefs = getPrefs();
            prefs.putInteger(Constants.PREF_FASE_DESBLOQUEADA, fase);
            prefs.flush();
        }
    }

    /**
     * Obtém a melhor nota (pontuação/ECTS) registrada para uma fase.
     * @param fase Número da fase (1, 2 ou 3).
     * @return Nota acumulada (0 a 20 valores).
     */
    public static float getMelhorNota(int fase) {
        return getPrefs().getFloat(Constants.PREF_MELHOR_NOTA_FASE + fase, 0f);
    }

    /**
     * Salva a nota se ela for superior à nota armazenada anteriormente.
     * @param fase Número da fase.
     * @param nota Nota/Pontuação obtida.
     */
    public static void salvarMelhorNota(int fase, float nota) {
        float notaAnterior = getMelhorNota(fase);
        if (nota > notaAnterior) {
            Preferences prefs = getPrefs();
            prefs.putFloat(Constants.PREF_MELHOR_NOTA_FASE + fase, nota);
            prefs.flush();
        }
    }

    /**
     * Retorna o volume dos efeitos e músicas (0.0 a 1.0).
     */
    public static float getVolumeSom() {
        return getPrefs().getFloat(Constants.PREF_VOLUME_SOM, 0.8f);
    }

    /**
     * Define o volume global de áudio.
     */
    public static void setVolumeSom(float volume) {
        Preferences prefs = getPrefs();
        prefs.putFloat(Constants.PREF_VOLUME_SOM, volume);
        prefs.flush();
    }
}
