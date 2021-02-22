package com.serpiente.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pieza {
    /* Estado*/
    public final static int DER= 1;
    public final static int IZQ = 2;
    public final static int ARR = 3;
    public final static int ABJ = 4;

    //pos en X (pixel)
    protected int PosX;

    //pos en Y(Pixel)
    protected int PosY;

    // ancho
    protected int ancho;

    //textura a dibujar
    protected Texture textura;
    protected final static String MIIMAGEN = "SpriteSnake.png";

    ////////////////////
    // COMPORTAMIENTO //
    ////////////////////

    //Constructor (necesita posiciones de partida, ancho y no necesitamos texturas
    public Pieza(int posNX, int posNY, int nAncho){
        PosX = posNX;
        PosY = posNY;
        ancho = nAncho;
        textura = new Texture(MIIMAGEN);
    }
    public Pieza(Pieza copiaPieza){
        PosX = copiaPieza.getPosX();
        PosY = copiaPieza.getPosY();
        ancho = copiaPieza.getAncho();
        textura = new Texture (MIIMAGEN);
    }

    public int getPosX() {
        return PosX;
    }
    public int getPosY() {
        return PosY;
    }
    public int getAncho(){ return ancho;}

    //Pintarse (usará la textura y necesita un escenario -> batch)
    public void render(SpriteBatch miSB){
        miSB.begin();
        miSB.draw(this.textura,this.PosX,this.PosY,this.ancho,this.ancho);
        miSB.end();
    }

    //Moverse (afectará a las posX y posY pero nunca a la vez)
    public void moverse(int direccion){
        switch(direccion){
            case DER: PosX += ancho;
                break;
            case IZQ: PosX-= ancho;
                break;
            case ARR: PosY += ancho;
                break;
            case ABJ: PosY -= ancho;
        }
    }

    //Disposar
    public void dispose(){
        if (textura!=null){
            textura.dispose();
        }
    }

    //Colisiona(le pasamos una pieza y nos dice si estan en el mismo sitio)
    public boolean colisiona(Pieza p){
        boolean colisionX, colisionY, resultado;
        colisionX = (PosX == p.getPosX());
        colisionY = (PosY == p.getPosY());
        resultado = (colisionX && colisionY);
        return resultado;
    }
}
