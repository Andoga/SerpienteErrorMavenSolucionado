package com.serpiente.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Controlador {

    /////////////////////////////////////////////////////////////////////////////////////
    //
    //ESTADO
    //
    /////////////////////////////////////////////////////////////////////////////////////

    //CONSTANTES
    protected final static String IMAGEN_INICIAL = "imagenInicio.png";
    protected final static String IMAGEN_FINAL = "imagenFinal.png";
    protected final static int TIEMPO_CRECER = 239;
    protected final static int TIEMPO_MOVER = 59;
    private Music menu;
    private Music jugando;
    private Music gameover;


    private static  Controlador miControlador;

    //RESTO DEL ESTADO
    protected Serpiente serpi;

    //UN BATCH PARA DIBUJAR
    protected SpriteBatch batch;

    //IMAGENES SPLASH PARA INICIO Y FINAL
    protected  Texture imagenInicio;
    protected Texture imagenFinal;

    //El SIMULADOR DE TECLADO
    EstadoTeclado et;

    enum Videojuego{
        INICIO,
        JUGANDO,
        FINALIZADO
    }

    //ESTADO DEL CONTROLADOR
    protected  Videojuego controlVG;
    protected int controlTiempo;
    protected int anchoPantalla, altoPantalla;

    /////////////////////////////////////////////////////////////////////////////////////
    //
    //COMPORTAMIENTO
    //
    /////////////////////////////////////////////////////////////////////////////////////

    //CONTRUCTOR QUE INICIA LA SERPIENTE
    private Controlador(int anchoReal){
        controlVG= Videojuego.INICIO;
        batch = new SpriteBatch();
        imagenInicio= new Texture(IMAGEN_INICIAL);
        imagenFinal = new Texture(IMAGEN_FINAL);
        et = new EstadoTeclado(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        anchoPantalla = anchoReal;
        controlTiempo = 0;
        menu = Gdx.audio.newMusic(Gdx.files.internal("Spring_Village.ogg"));
        jugando = Gdx.audio.newMusic(Gdx.files.internal("jugando.mp3"));
        gameover = Gdx.audio.newMusic(Gdx.files.internal("Game_Overr.mp3"));
    }

    //RESTO DE COMPORTAMIENTOS
    protected static Controlador getInstance(int posXinicial, int posYinicial, int ancho, int altoAnchoPantalla, int anchoReal, int altoReal){
        if (Controlador.miControlador == null) {
            miControlador = new Controlador(altoAnchoPantalla);
            miControlador.setSerpi(new Serpiente(posXinicial,posYinicial,ancho,altoAnchoPantalla,anchoReal,altoReal));
        }

        return Controlador.miControlador;
    }

    //MAQUINA DE ESTADOS DEL CONTROLADOR
    private void pantallaInicio(){
        //serpi.pintarse(batch);
        batch.begin();
        menu.play();
        batch.draw(imagenInicio, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();




        //ACTUALIZO EL TECLADO
        boolean recienTocado;

        recienTocado = Gdx.input.justTouched();
        if (recienTocado){
            this.iniciaPartida();
            menu.dispose();

        }
    }




    private void setSerpi(Serpiente nuevaSerpiente){
        serpi = nuevaSerpiente;
    }

    //COMPORTAMIENTO DE CONTROL Y DIBUJO DEL VIDEOJUEGO
    public void render(){
        switch (controlVG){
            case INICIO: this.pantallaInicio();
                break;
            case JUGANDO: this.controlaEstadoJugando();
                break;
            case FINALIZADO: this.finalPartida();
                break;
        }
    }

    //ELIMINACION DE RECURSOS
    public void dispose(){
        if (serpi!=null) serpi.dispose();
        batch.dispose();
        imagenFinal.dispose();
        imagenInicio.dispose();
    }



    private void iniciaPartida(){
        Serpiente nuevaSerpiente = new Serpiente(serpi);
        controlVG = Videojuego.JUGANDO;
        serpi.dispose();
        serpi = nuevaSerpiente;
        jugando.setVolume(0.25f);
        jugando.play();
    }

    private void controlaEstadoJugando(){
        //MIRAMOS SI HAN PULADO UNA TECLA PARA CAMBIAR DIRECCION
        boolean recienTocado;

        recienTocado = Gdx.input.justTouched();
        if (recienTocado){
            //¿Donde han pinchado en la pantalla?
            int posNuevaX, posNuevaY;
            posNuevaX = Gdx.input.getX();
            posNuevaY = Gdx.input.getY();

            //Le doy esos valores a et
            et.simulaTeclado(posNuevaX,posNuevaY);

            //Le paso a la serpiente el objeto et, que teclas estan pulsadas.
            serpi.cambiaDireccion(et);
        }


        //TENGO QUE MOVER LA SERPIENTE O CRECER LA SERPIENTE
        if (controlTiempo % TIEMPO_MOVER == 0){
            serpi.moverse();
            controlTiempo++;
        }else if(controlTiempo == TIEMPO_CRECER){
            serpi.crecer();
            controlTiempo = 0;
        }else {
            controlTiempo++;
        }

        //ME HABRE CHOCADO??
        if (serpi.hasMuerto()) {
            controlVG = Videojuego.FINALIZADO;
        }


        //TENGO QUE PINTAR LA SERPIENTE
        serpi.pintarse(batch);

    }

    private void finalPartida(){
        jugando.dispose();
        gameover.play();
        batch.begin();
        batch.draw(imagenFinal, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();

        //MIRAMOS SI HAN PULSADO UNA TECLA PARA COMENZAR A JUGAR.
        boolean recienTocado;

        recienTocado = Gdx.input.justTouched();
        if (recienTocado){
            gameover.dispose();
            this.iniciaPartida();
        }
    }
}