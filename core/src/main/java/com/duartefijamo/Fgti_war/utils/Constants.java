package com.duartefijamo.Fgti_war.utils;

/**
 * Constantes globais do jogo FgtiWar.
 * Contém parâmetros de exibição, física de plataforma, dimensões de tiles e chaves de salvamento.
 */
public class Constants {
    // Dimensões da tela virtual (Resolução interna)
    public static final int LARGURA_VIRTUAL = 800;
    public static final int ALTURA_VIRTUAL = 480;

    // Dimensões de Tile (Pixel Art 32x32)
    public static final int TILE_SIZE = 32;

    // Configurações de Física do Jogador
    public static final float VELOCIDADE_JOGADOR = 220f;    // Velocidade de caminhada (px/s)
    public static final float FORCA_PULO = 620f;            // Impulso inicial de pulo (alcança 5 tiles de altura)
    public static final float GRAVIDADE = -1200f;           // Aceleração da gravidade (px/s²)
    public static final float VELOCIDADE_QUEDA_MAX = -600f; // Velocidade limite de queda
    public static final float JUMP_CUT_FACTOR = 0.55f;      // Fator de corte ao soltar o botão de pulo
    public static final float COYOTE_TIME_DURATION = 0.15f; // Janela para pulo pós-plataforma (segundos)
    public static final float JUMP_BUFFER_DURATION = 0.15f; // Janela para pré-registro do pulo (segundos)
    public static final float TEMPO_INVENCIBILIDADE = 1.2f; // Tempo de invencibilidade após dano (segundos)

    // Fases do Jogo
    public static final int TOTAL_FASES = 3;
    public static final int FASE_1_LABORATORIO = 1;
    public static final int FASE_2_SERVIDORES = 2;
    public static final int FASE_3_REITORIA = 3;

    // Nomes e Chaves de Preferências (Salvamento)
    public static final String PREFS_NAME = "FgtiWar_Prefs";
    public static final String PREF_FASE_DESBLOQUEADA = "fase_desbloqueada";
    public static final String PREF_MELHOR_NOTA_FASE = "melhor_nota_fase_";
    public static final String PREF_VOLUME_SOM = "volume_som";

    // Constantes legadas mantidas para compatibilidade
    public static final float ALTURA_CHAO = 32f;
    public static final float MARGEM_LATERAL = 0f;
}
