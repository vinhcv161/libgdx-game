package com.sunshine.ninjafruit.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.sunshine.ninjafruit.NinjaFruitGame;
import com.sunshine.ninjafruit.entities.Fruit;
import com.sunshine.ninjafruit.entities.LevelTransporters;
import com.sunshine.ninjafruit.screens.menu.GameOverScreen;

import java.util.Random;

public class NinjaFruitCommon implements Screen {
    private NinjaFruitGame game;

    // sound
    private Music music;
    private Sound sound;
    private Integer[] touchPointsX; // chỉ gồm 2 phần tử, tác dụng ghi log để check đảo chiều di ngón tay
    private int touchIdx;

    private Texture background, pizza, apple, bomb, coin; //Xác định các hình ảnh sẽ sử dụng trong lớp Texture.
    private BitmapFont textFont;
    private Random random;

    float zorluk, zorlukLimit;
    int lives;
    int score;
    int bombs;

    float genCounter;
    float genSpeed;

    private Array<Fruit> fruitArray;

    public NinjaFruitCommon(NinjaFruitGame game) {
        this.game = game;
    }

    public void init(LevelTransporters trans) {
        // Điện thoại được sử dụng theo chiều ngang hay chiều dọc
        Fruit.radius = Math.max(NinjaFruitGame.HEIGHT, NinjaFruitGame.WIDTH) / 18f;

        initFromTrans(trans);
        initItems();
        //initMusic();
        initInputProcessor();
        initParams();
    }

    private void initFromTrans(LevelTransporters trans) {
        if (trans.background != null) background = trans.background;
        else background = new Texture("fruit-game/ninja.jpg");
        if (trans.textFont != null) textFont = trans.textFont;
        else textFont = Utils.createFont("fonts/robotobold.ttf", Color.WHITE, (int) (0.6f * Fruit.radius), Color.ROYAL, 2f);

        if (trans.music != null) music = trans.music;
        else music = Gdx.audio.newMusic(Gdx.files.internal("audios/music.mp3"));

        if (trans.sound != null) sound = trans.sound;
        else sound = Gdx.audio.newSound(Gdx.files.internal("audios/sfx_wing.ogg"));

        // Độ khó
        if (trans.zorlukLimit != null) zorlukLimit = trans.zorlukLimit;
        else zorlukLimit = 0.28f;
        if (trans.genSpeed != null) genSpeed = trans.genSpeed;
        else genSpeed = 1.1f;
    }

    private void initItems() {
        apple = new Texture("fruit-game/apple.png");
        bomb = new Texture("fruit-game/piecesBomb.png");
        pizza = new Texture("fruit-game/pizza.png");
        coin = new Texture("fruit-game/piecesCoin.png");
    }

    private void initMusic() {
        if (!music.isPlaying()) {
            music.setLooping(true);
            music.setVolume(0.1f);
            music.play(); // play music

            touchPointsX = new Integer[2];
        }
    }

    private void initParams() {
        random = new Random();
        score = 0;
        lives = 4;
        fruitArray = new Array<>();
    }

    private void initInputProcessor() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                // System.out.println("TOUCH-DOWN");
                // System.out.println(screenX + " - " + screenY + " - " + pointer);
                if (lives > 0) {
                    // setup -> di ngón tay: màn hình chơi game
                    touchIdx = -1; // B2: Bắt đầu chạm ngón tay xuống
                } else {
                    // for chạm rồi nhấc lên: màn hình menu
                    long id = sound.play(0.1f);
                    sound.setPitch(id, 2);
                    sound.setLooping(id, false);
                }
                return super.touchUp(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                // System.out.println("TOUCH-DRAGGED");
                // System.out.println(screenX + " - " + screenY + " - " + pointer);
                if (lives > 0) {
                    // B3: sound: khi di tay chém hoa quả.
                    touchIdx++;
                    if (touchIdx == 0) { // first touch
                        createTouchMusic();
                    } else if (touchIdx == 2) {
                        boolean isOldUp = touchPointsX[1] - touchPointsX[0] > 0;
                        boolean isUp = screenX - touchPointsX[1] > 0;
                        if (isOldUp && !isUp || !isOldUp && isUp) { // đảo chiều di ngón tay
                            createTouchMusic();
                        }

                        touchIdx = 1; // vượt quá index của mảng touchPointsX thì gán lại bằng 1
                        touchPointsX[0] = touchPointsX[1]; // swap
                    }
                    // set current point
                    touchPointsX[touchIdx] = screenX; // X current point

                    Array<Fruit> toRemove = new Array<>();
                    Vector2 pos = new Vector2(screenX, NinjaFruitGame.HEIGHT - screenY);
                    int plusScore = 0;
                    // Trong phần này, điểm số và mạng sống được thay đổi tùy theo loại đối tượng được chạm vào.
                    for (Fruit f : fruitArray) {

                        if (f.clicked(pos)) {
                            toRemove.add(f);

                            switch (f.type) {
                                case REGULAR:
                                    plusScore++;
                                    break;
                                case EXTRA:
                                    plusScore += 5;
                                    break;
                                case BOMB:
                                    lives--;
                                    break;
                                case COIN:
                                    lives++;
                                    break;
                            }
                        }
                    }
                    score += plusScore;
                    // Trong phần này, chúng tôi sẽ chiết xuất những loại trái cây được chạm vào trong bộ phim truyền hình
                    for (Fruit f : toRemove) {
                        fruitArray.removeValue(f, true);
                    }
                }
                return false;
            }
        });
    }

    private void createTouchMusic() {
        long id = sound.play(0.1f);
        sound.setPitch(id, 2);
        sound.setLooping(id, false);
    }

    @Override
    public void show() {
        System.out.println("SHOW");
        initMusic();
    }

    @Override
    public void render(float delta) {
        if (lives <= 0) {
            this.dispose();
            game.setScreen(new GameOverScreen(game, score));
            return;
        }

        // draw background
        // Gdx.gl.glClearColor(0, 0, 0, 1);
        // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0, 0, NinjaFruitGame.WIDTH, NinjaFruitGame.HEIGHT);
        // game.scrollingBackground.updateAndRender(delta, game.batch);

        // những việc cần làm trong khi trò chơi đang chạy
        genSpeed -= delta * 0.015f;

        if (genCounter <= 0f) {
            genCounter = genSpeed;
            addItem();
        } else {
            genCounter -= delta;
        }

        for (int i = 0; i < lives; i++) {
            game.batch.draw(coin, Fruit.radius / 3 + (i * Fruit.radius), NinjaFruitGame.HEIGHT - Fruit.radius, Fruit.radius, Fruit.radius);
        }

        for (Fruit fruit : fruitArray) {
            fruit.update(delta);
            switch (fruit.type) {
                case REGULAR:
                    game.batch.draw(apple, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
                    break;
                case BOMB:
                    game.batch.draw(bomb, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
                    break;
                case EXTRA:
                    game.batch.draw(pizza, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
                    break;
                case COIN:
                    game.batch.draw(coin, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
                    break;
            }
        }

        /*
        Chúng tôi đã tạo điều kiện cho những quả táo tắt màn hình làm giảm sức khỏe.
        Và chúng tôi đã ngăn không cho những quả táo khác trên màn hình giảm tuổi thọ
        nếu chúng tắt màn hình, vì vậy khi người dùng bỏ lỡ nhiều quả táo cùng một lúc
        thì chỉ có một quả bị mất mạng.
        */
        boolean holdLives = false;

        Array<Fruit> toRemove = new Array<>();
        for (Fruit fruit : fruitArray) {
            if (fruit.outOfScreen()) {
                toRemove.add(fruit);

                if (fruit.living && fruit.type == Fruit.Type.REGULAR) {
                    lives--;
                    holdLives = true;
                    break;
                }
            }
        }
        if (holdLives) {
            for (Fruit f : fruitArray) {
                f.living = false;
            }
        }
        for (Fruit f : toRemove) {
            fruitArray.removeValue(f, true);
        }

        //
        GlyphLayout scoreLayout = new GlyphLayout(textFont, "Score: " + score);
        textFont.draw(game.batch, scoreLayout, Fruit.radius / 3, 0.6f * Fruit.radius);
        scoreLayout = new GlyphLayout(textFont, "Bomb: " + bombs);
        textFont.draw(game.batch, scoreLayout, Fruit.radius / 3, 1.25f * Fruit.radius);

        game.batch.end();
    }

    private void addItem() {
        float pos = random.nextFloat() * Math.max(NinjaFruitGame.HEIGHT, NinjaFruitGame.WIDTH);

        Fruit item = new Fruit(new Vector2(pos, -Fruit.radius), new Vector2((NinjaFruitGame.WIDTH * 0.5f - pos) * (0.3f + (random.nextFloat() - 0.5f)), NinjaFruitGame.HEIGHT * 0.5f));

        // trong phần này tôi chỉ ra khả năng của các đối tượng trong tương lai
        float type = random.nextFloat();
        if (type > 0.98f) item.type = Fruit.Type.COIN;
        else if (type > 0.88f) item.type = Fruit.Type.EXTRA;
        else if (type + zorluk > 0.78f) {
            item.type = Fruit.Type.BOMB;
            bombs++;
        }
        if (fruitArray.size < 15) fruitArray.add(item);
        else if (zorluk < zorlukLimit) zorluk = zorluk + 0.01f;
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("RESIZE");
    }

    @Override
    public void pause() {
        System.out.println("PAUSE");
    }

    @Override
    public void resume() {
        System.out.println("RESUME");
    }

    @Override
    public void hide() {
        System.out.println("HIDE");
    }

    @Override
    public void dispose() {
        System.out.println("DISPOSE");
        Gdx.input.setInputProcessor(null);
        textFont.dispose();
        music.dispose();
        sound.dispose();
    }
}
