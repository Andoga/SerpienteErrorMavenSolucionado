package com.serpiente.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Serpiente {
    ///////////
    //Estado//
    //////////
    protected int posX,posY,ancho,anchoReal,altoReal,anchoAltoPantalla;
    protected ArrayList<Pieza> miCuerpo;
    protected int direccion;
    private Sound crecer;


    ///////////////////
    ///COMPORTAMIENTO//
    ///////////////////

    public Serpiente(int posX,int posY, int nancho, int anchoAltoPantalla, int anchoReal, int altoReal){
        Pieza nuevaCabeza;
        nuevaCabeza = new Pieza(posX,posY,nancho);
        this.anchoAltoPantalla = anchoAltoPantalla;
        this.anchoReal = anchoReal;
        this.altoReal= altoReal;

        direccion = Pieza.ARR;
        this.posX = posX;
        this.posY = posY;
        this.ancho = nancho;

        miCuerpo = new ArrayList();
        miCuerpo.add(nuevaCabeza);
        crecer = Gdx.audio.newSound(Gdx.files.internal("campana.mp3"));


    }
    public Serpiente(Serpiente antigua){
        Pieza nuevaCabeza;

        posX= antigua.getPosX();
        posY = antigua.getPosY();
        ancho = antigua.getAncho();

        anchoAltoPantalla=antigua.getAnchoAltoPantalla();
        anchoReal=antigua.getAnchoReal();
        altoReal=antigua.getAltoReal();

        nuevaCabeza = new Pieza(posX,posY,ancho);

        direccion = Pieza.ARR;
        crecer = Gdx.audio.newSound(Gdx.files.internal("campana.mp3"));

        miCuerpo = new ArrayList();
        miCuerpo.add(nuevaCabeza);
    }

    public int getAnchoAltoPantalla() {
        return anchoAltoPantalla;
    }

    public int getAnchoReal() {
        return anchoReal;
    }

    public int getAltoReal() {
        return altoReal;
    }

    //moverse
    public void  moverse(){

        this.crecer();
        crecer.play();
        miCuerpo.remove(miCuerpo.size()-1);

    }

    public void crecer(){

        Pieza nuevaCabeza;
        Pieza cabezaAntigua = miCuerpo.get(0);
        nuevaCabeza = new Pieza(cabezaAntigua);
        nuevaCabeza.moverse(direccion);
        miCuerpo.add(0,nuevaCabeza);
        crecer.play();



    }

    public void pintarse (SpriteBatch miSB){
        for(Pieza unaPieza: miCuerpo) {
            unaPieza.render(miSB);
        }
    }

    public void dispose(){
        for(Pieza unaPieza: miCuerpo) {
            unaPieza.dispose();
        }
    }
    public int getPosX(){ return posX;}
    public int getPosY(){ return posY;}
    public int getAncho(){ return ancho;}

    //comportamiento hasMuerto
    public boolean hasMuerto(){

        return (testCuerpo() || testParedes());
    }
    private boolean testCuerpo(){
        // +(0) 1 2 3 4 5 6
        //tengo que mirar si colisionan una a una las piezas del cuerpo con la cabeza
        Pieza cabezona = this.miCuerpo.get(0);

        if (miCuerpo.size()<4) return false;

        for(int i=4;i<miCuerpo.size();i++){
            if (miCuerpo.get(i).colisiona(cabezona)) {
                return true;
            }
        }
        return false;
    }
    private boolean testParedes(){
        float limiteIzq,limiteDer;
        float limiteArr,limiteAba;
        Pieza cabeza = miCuerpo.get(0);

        limiteIzq = 0;
        limiteAba = Gdx.graphics.getHeight();
        limiteDer = Gdx.graphics.getWidth();
        limiteArr = 0;
        return (cabeza.getPosX()<limiteIzq || cabeza.getPosX()>limiteDer ||
                cabeza.getPosY()<limiteArr || cabeza.getPosY()>limiteAba);

    }

    public void cambiaDireccion(EstadoTeclado miTeclado){

        switch (this.direccion){
            case Pieza.ABJ:
            case Pieza.ARR: if (miTeclado.isTeclaDer()){
                direccion= Pieza.DER;
            } else {
                direccion= Pieza.IZQ;
            }
            break;
            case Pieza.DER:
            case Pieza.IZQ: if (miTeclado.isTeclaAbajo()){
                direccion = Pieza.ABJ;
            } else {
                direccion = Pieza.ARR;
            }
            break;
        }
    }
}