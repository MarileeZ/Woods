package com.woods.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;
import java.util.Map;

/**
 * Main class for the game
 * Name: Robert Zafaripour
 * This game will simply simulate a collision from several players on a tile board.
 * Framework: libGDX
 */
public class Woods extends Game
{
    SpriteBatch batch;
    Texture img;
    BitmapFont monoFont;
    BitmapFont medievalFont;
    BitmapFont arrowKeyFont;
    BitmapFont largeFont;
    Label.LabelStyle aLabelStyle;
    Array<Texture> backgroundTextures;
    HashMap<String, Texture> menuTextures;
    HashMap<String, Button> buttons;
    HashMap<String, ImageButton> imageButtons;
    Texture[] boardTextures;
    Music forestMusic;
    Music scaryMusic;
    Sound found;
    OrthographicCamera camera;
    Viewport aViewport;
    Skin someSkin;

    final float WORLD_WIDTH = 100;
    final float WORLD_HEIGHT = 100;

    /**
     * Basic creation method
     */
    @Override
    public void create()
    {
        someSkin =  new Skin();
        batch = new SpriteBatch();
        backgroundTextures = new Array<>();
        buttons = new HashMap<>();
        imageButtons = new HashMap<>();
        menuTextures = new HashMap<>();
        boardTextures = new Texture[10];
        this.camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT); //Sets the game size, the full width/height is the entire length in pixels
        camera.setToOrtho(false);
        aViewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        aViewport.apply();

        addTextures();
        this.monoFont = new BitmapFont();
        this.medievalFont = new BitmapFont(Gdx.files.internal("leela.fnt"));
        this.arrowKeyFont = new BitmapFont(Gdx.files.internal("monospace.fnt"));
        this.largeFont = new BitmapFont(Gdx.files.internal("leelaLarge.fnt"));
        aLabelStyle = new Label.LabelStyle();
        aLabelStyle.font = new BitmapFont(Gdx.files.internal("monofnt.fnt"));
        this.monoFont = new BitmapFont(Gdx.files.internal("monofnt.fnt"));
        this.forestMusic = Gdx.audio.newMusic(Gdx.files.internal("nightForest.mp3"));
        this.scaryMusic = Gdx.audio.newMusic(Gdx.files.internal("scary.mp3"));
        this.found = Gdx.audio.newSound(Gdx.files.internal("found.wav"));
        createButtons();
        createMenuButtons();
        this.setScreen(new MenuScreen(this));

    }

    @Override
    public void render()
    {
        super.render();
    }

    /**
     * Each class that extends the game class must have a dispose method to get rid of objects
     */
    @Override
    public void dispose()
    {
        batch.dispose();
        for (Texture aTexture : backgroundTextures)
        {
            aTexture.dispose();
        }
        forestMusic.dispose();
        monoFont.dispose();
        medievalFont.dispose();
        arrowKeyFont.dispose();
        for (int i = 0; i < boardTextures.length; i++)
        {
            boardTextures[i].dispose();
        }
        for (Map.Entry<String, Texture> entry : menuTextures.entrySet())
        {
            entry.getValue().dispose();
        }
        found.dispose();

    }

    private void addTextures()
    {
        backgroundTextures.add(new Texture(Gdx.files.internal("rain-0.png")));
        backgroundTextures.add(new Texture(Gdx.files.internal("rain-1.png")));

        menuTextures.put("SlantedTree", new Texture(Gdx.files.internal("slantedTree.png")));
        menuTextures.put("DeadTree", new Texture(Gdx.files.internal("deadTree.png")));
        menuTextures.put("Reset", new Texture(Gdx.files.internal("reset.png")));
        menuTextures.put("Exit", new Texture(Gdx.files.internal("exit.png")));
        menuTextures.put("Bunny", new Texture(Gdx.files.internal("bunny.png")));
        menuTextures.put("SleepingBunny", new Texture(Gdx.files.internal("sleepingBunny.png")));

        boardTextures[0] = new Texture(Gdx.files.internal("Tree_Pine_00.png"));
        boardTextures[1] = new Texture(Gdx.files.internal("Tree_Pine_01.png"));
        boardTextures[2] = new Texture(Gdx.files.internal("Tree_Pine_02.png"));
        boardTextures[3] = new Texture(Gdx.files.internal("Tree_Pine_03.png"));
        boardTextures[4] = new Texture(Gdx.files.internal("Tree_Pine_04.png"));
        boardTextures[5] = new Texture(Gdx.files.internal("Tree_Pine_Snow_00.png"));
        boardTextures[6] = new Texture(Gdx.files.internal("Tree_Pine_Snow_01.png"));
        boardTextures[7] = new Texture(Gdx.files.internal("Tree_Pine_Snow_02.png"));
        boardTextures[8] = new Texture(Gdx.files.internal("Tree_Pine_Snow_03.png"));
        boardTextures[9] = new Texture(Gdx.files.internal("Tree_Pine_Snow_04.png"));

        Texture blueTile = new Texture(Gdx.files.internal("blueTile.png"));
    }

    private void createMenuButtons()
    {
        ImageButton.ImageButtonStyle gradeButtonStyle = new ImageButton.ImageButtonStyle();
        TextureRegion bunnyRegion = new TextureRegion(menuTextures.get("SleepingBunny"));
        Image bunnyImage = new Image(bunnyRegion);
        someSkin.add("bunny", bunnyRegion);
        gradeButtonStyle.up = new TextureRegionDrawable(bunnyRegion);
        gradeButtonStyle.over = someSkin.newDrawable("bunny", Color.CORAL);
        ImageButton imageButtonOfBunny = new ImageButton(gradeButtonStyle);

        imageButtons.put("SleepingBunny", imageButtonOfBunny);
    }

    private void createButtons()
    {

        Button.ButtonStyle resetButtonStyle = new Button.ButtonStyle();
        Button.ButtonStyle exitButtonStyle = new Button.ButtonStyle();

        Texture resetTexture = menuTextures.get("Reset");
        Texture exitTexture = menuTextures.get("Exit");
        TextureRegion exitRegion = new TextureRegion(exitTexture);
        TextureRegion resetRegion = new TextureRegion(resetTexture);
        someSkin.add("white", resetTexture);
        someSkin.add("black", exitTexture);


        resetButtonStyle.up = new TextureRegionDrawable(resetRegion);
        exitButtonStyle.up = new TextureRegionDrawable(exitRegion);
        resetButtonStyle.checked = someSkin.newDrawable("white", Color.DARK_GRAY);

        resetButtonStyle.over = someSkin.newDrawable("white", Color.LIME); //This adds a new drawable using the white skin and applying Color.Lime
        exitButtonStyle.over = someSkin.newDrawable("black", Color.CORAL);

        Button exitButton = new Button(exitButtonStyle);
        Button resetButton = new Button(resetButtonStyle);
        resetButton.setColor(Color.BROWN.r, Color.BROWN.g, Color.BROWN.b, 0.8f);
        exitButton.setColor(Color.CHARTREUSE.r, Color.CHARTREUSE.g, Color.CHARTREUSE.b, 0.8f);
        resetButton.setX(camera.viewportWidth - 110);

        exitButton.setHeight((float) exitTexture.getHeight() / 4);
        exitButton.setWidth((float) exitTexture.getWidth() / 4);
        resetButton.setHeight((float) resetTexture.getHeight() / 3);
        resetButton.setWidth((float) resetTexture.getWidth() / 3);
        buttons.put("reset", resetButton);
        buttons.put("exit", exitButton);
    }
}
