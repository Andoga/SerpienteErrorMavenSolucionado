package com.serpiente.game;

public class EstadoTeclado {
    /////////////////////////////////////////////////////////////////////////////////////
    //
    //ESTADO
    //
    /////////////////////////////////////////////////////////////////////////////////////

    private boolean teclaArriba;
    private boolean teclaAbajo;
    private boolean teclaIzq;
    private boolean teclaDer;

    private int alto, ancho; //alto y ancho de la pantalla, para simular teclado
    private int limiteAlturaZonaArriba; //linea vertical pixel a partir del cual conseguimos...
    private int limiteAnchoZonaLateral; //ancho de las zonas laterales para controlar la serpiente

    /////////////////////////////////////////////////////////////////////////////////////
    //
    //COMPORTAMIENTO
    //
    /////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////
    //
    //CONSTRUCTORES
    //
    /////////////////////////////////////////////////////////////////////////////////////

    public EstadoTeclado(int ancho, int alto) {
        teclaArriba = false;
        teclaAbajo = false;
        teclaIzq = false;
        teclaDer = false;
        this.ancho = ancho;
        this.alto = alto;
        limiteAlturaZonaArriba = ancho / 2;
        limiteAnchoZonaLateral = alto / 2;
    }

    //Resto de comportamiento

    public boolean isTeclaArriba() {
        return teclaArriba;
    }

    public boolean isTeclaAbajo() {
        return teclaAbajo;
    }

    public boolean isTeclaIzq() {
        return teclaIzq;
    }

    public boolean isTeclaDer() {
        return teclaDer;
    }

    //nos han pulsado la pantalla en posX, posY. Simulamos el equivalente en teclas
    public void simulaTeclado(int posX, int posY) {
        if (posY < limiteAlturaZonaArriba) {
            teclaArriba = true;
            teclaAbajo = false;
        } else {
            teclaArriba = false;
            teclaAbajo = true;
        }

        if (posX < limiteAnchoZonaLateral) {
            teclaIzq = true;
            teclaDer = false;
        } else {
            teclaIzq = false;
            teclaDer = true;
        }
    }
}

