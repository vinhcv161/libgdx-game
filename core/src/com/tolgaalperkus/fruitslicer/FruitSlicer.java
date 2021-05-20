package com.tolgaalperkus.fruitslicer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;

//CÁC THÀNH PHẦN GDX BẮT BUỘC ĐƯỢC TÌM HIỂU, CƠ BẢN CỦA TRÒ CHƠI LÀ Ở ĐÂY.

//Triển khai giao diện InputProcessor để theo dõi các đầu vào.
public class FruitSlicer extends ApplicationAdapter implements InputProcessor {

    SpriteBatch batch;
    //Xác định các hình ảnh sẽ sử dụng trong lớp Texture.
    Texture background, pizza, apple, bomb, time, coin;
    //Chỉ định loại văn bản trong phần đầu của menu và điểm số.
    BitmapFont font;
    FreeTypeFontGenerator fontGen;

    float zorluk = 0f;
    int lives = 0;
    int score = 0;

    float genCounter = 0;
    private final float startGenSpeed = 1.1f;
    float genSpeed = startGenSpeed;

    private double currentTime = 0;
    private double gameOverTime = -1.0f;
    Random random = new Random();
    Array<Fruit> fruitArray = new Array<Fruit>();

    // menu
    private boolean isShowTopScores;
    private Button playBtn, exitBtn, topScoresBtn, exitTopScores;
    // sound
    private Music music;
    private Sound sound;
    private Integer[] touchPointsX; // chỉ gồm 2 phần tử, tác dụng ghi log để check đảo chiều di ngón tay
    private int touchIdx;

    @Override
    public void create() {
        batch = new SpriteBatch();
        //Trỏ địa chỉ của các hình ảnh đến các đối tượng mà chúng tôi xác định trong lớp kết cấu.
        background = new Texture("ninja.jpg");
        apple = new Texture("apple.png");
        bomb = new Texture("piecesBomb.png");
        time = new Texture("piecesTime.png");
        pizza = new Texture("pizza.png");
        coin = new Texture("piecesCoin.png");
        //Điện thoại được sử dụng theo chiều ngang hay chiều dọc
        Fruit.radius = Math.max(Gdx.graphics.getHeight(), Gdx.graphics.getWidth()) / 18f;

        //Xử lý đầu vào của người dùng.
        Gdx.input.setInputProcessor(this);
        //chỉ định phông chữ tôi sẽ sử dụng bên dưới
        //và các tính năng của nó như chúng tôi muốn nhờ vào plugin FreeType
        fontGen = new FreeTypeFontGenerator(Gdx.files.internal("robotobold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.color = Color.WHITE;
        // params.borderWidth = 2;
        // params.borderColor = Color.WHITE;
        params.size = 100;
        font = fontGen.generateFont(params);
        // B1: khởi tạo các thông số cho menu
        float xBtn = Gdx.graphics.getWidth() / 2 - 250;
        float yBtn = Gdx.graphics.getHeight() / 1.65f;
        float widthBtn = 2.5f * Fruit.radius;
        float heightBtn = 1.3f * Fruit.radius;
        float paddingBtn = heightBtn + 20f;
        playBtn = new Button(new Texture("buttonStart.png"), xBtn, yBtn, widthBtn, heightBtn);
        topScoresBtn = new Button(new Texture("buttonTopScores.png"), xBtn, yBtn - paddingBtn, widthBtn, heightBtn);
        exitBtn = new Button(new Texture("buttonExit.png"), xBtn, yBtn - 2 * paddingBtn, widthBtn, heightBtn);
        // B1: create sound
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.2f);
        music.play();
        sound = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
        touchPointsX = new Integer[2];
    }

    @Override
    public void render() {
        // thể thêm các đối tượng sẽ được vẽ
        batch.begin();

        // Gdx.graphics.getWidth() và Gdx.graphics.getHeight() là kích thước màn hình thiết bị
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Vì số khung hình mỗi giây sẽ khác nhau trên các thiết bị khác nhau, tôi tạo bộ đếm thời gian của riêng mình.
        double newTime = TimeUtils.millis() / 1000.0;
        double frameTime = Math.min(newTime - currentTime, 0.3);
        float deltaTime = (float) frameTime;
        currentTime = newTime;

        if (lives > 0) {
            //những việc cần làm trong khi trò chơi đang chạy
            genSpeed -= deltaTime * 0.015f;

            if (genCounter <= 0f) {
                genCounter = genSpeed;
                addItem();
            } else {
                genCounter -= deltaTime;
            }

            for (int i = 0; i < lives; i++) {
                batch.draw(coin, 30 + (i * 100), Gdx.graphics.getHeight() - 110f, 100f, 100f);
            }

            for (Fruit fruit : fruitArray) {
                fruit.update(deltaTime);
                switch (fruit.type) {
                    case REGULAR:
                        batch.draw(apple, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
                        break;
                    case BOMB:
                        batch.draw(bomb, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
                        break;
                    case EXTRA:
                        batch.draw(pizza, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
                        break;
                    case COIN:
                        batch.draw(coin, fruit.getPos().x, fruit.getPos().y, Fruit.radius, Fruit.radius);
                        break;
                }
            }

			/*
			Chúng tôi đã tạo điều kiện cho những quả táo tắt màn hình làm giảm sức khỏe.
			Và chúng tôi đã ngăn không cho những quả táo khác trên màn hình giảm tuổi thọ
			nếu chúng tắt màn hình, vì vậy khi người dùng bỏ lỡ nhiều quả táo cùng một lúc
			thì chỉ có một quả bị mất mạng.
			*/
            boolean holdlives = false;

            Array<Fruit> toRemove = new Array<Fruit>();
            for (Fruit fruit : fruitArray) {
                if (fruit.outOfScreen()) {
                    toRemove.add(fruit);

                    if (fruit.living && fruit.type == Fruit.Type.REGULAR) {
                        lives--;
                        holdlives = true;
                        break;
                    }
                }
            }
            if (holdlives) {
                for (Fruit f : fruitArray) {
                    f.living = false;
                }
            }
            for (Fruit f : toRemove) {
                fruitArray.removeValue(f, true);
            }

        } else {
            // trò chơi vừa mới kết thúc
            if (gameOverTime == 0f) {
                gameOverTime = currentTime;
            }
            //font.draw(batch, "Start", Gdx.graphics.getWidth() / 2f - 250, Gdx.graphics.getHeight() / 2f);
            // B2: tạo menu
            if (isShowTopScores) topScoresRender();
            else menuRender();
        }
        // Hiển thị điểm
        font.draw(batch, "Score : " + score, 50, 100);
        batch.end();
    }

    private void menuRender() {
        drawBtn(playBtn);
        drawBtn(topScoresBtn);
        drawBtn(exitBtn);
    }

    // TODO ...
    private void topScoresRender() {
        float xBtn = Gdx.graphics.getWidth() / 2 - 250;
        float yBtn = Gdx.graphics.getHeight() / 1.3f;
        float padding = 125f;
        font.draw(batch, "1. 3056", xBtn, yBtn);
        font.draw(batch, "2. 2097", xBtn, yBtn - padding);
        font.draw(batch, "3. 2097", xBtn, yBtn - 2 * padding);
        font.draw(batch, "4. 2097", xBtn, yBtn - 3 * padding);
        font.draw(batch, "5. 2097", xBtn, yBtn - 4 * padding);
        // font.draw(batch, "EXIT", xBtn + 100, yBtn - 5 * padding);
        // create info exit btn
        exitTopScores = new Button(new Texture("buttonExit.png"), xBtn, yBtn - 5 * padding - 1.5f * Fruit.radius, exitBtn.getWidth(), exitBtn.getHeight());
        drawBtn(exitTopScores);
    }

    private void drawBtn(Button btn) {
        batch.draw(btn.getText(), btn.getX(), btn.getY(), btn.getWidth(), btn.getHeight());
    }

    private void addItem() {
        float pos = random.nextFloat() * Math.max(Gdx.graphics.getHeight(), Gdx.graphics.getWidth());

        Fruit item = new Fruit(new Vector2(pos, -Fruit.radius), new Vector2((Gdx.graphics.getWidth() * 0.5f - pos) * (0.3f + (random.nextFloat() - 0.5f)), Gdx.graphics.getHeight() * 0.5f));

        // trong phần này tôi chỉ ra khả năng của các đối tượng trong tương lai
        float type = random.nextFloat();
        if (type > 0.98f) item.type = Fruit.Type.COIN;
        else if (type > 0.88f) item.type = Fruit.Type.EXTRA;
        else if (type + zorluk > 0.78f) item.type = Fruit.Type.BOMB;
        if (fruitArray.size < 15) fruitArray.add(item);
        else if (zorluk < 0.28) zorluk = zorluk + 0.01f;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (lives > 0) {
            // for di ngón tay: màn hình chơi game
            touchIdx = -1; // B2: Bắt đầu chạm ngón tay xuống
        } else {
            // for chạm rồi nhấc lên: màn hình menu
            long id = sound.play(0.1f);
            sound.setPitch(id, 2);
            sound.setLooping(id, false);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // B3: người dùng thao tác với màn hình
        if (isShowTopScores) { // screen top scores
            if (isBtnClicked(exitTopScores, screenX, screenY)) {
                exitTopScores = null;
                isShowTopScores = false;
            }
        } else if (lives <= 0 && currentTime - gameOverTime > 2f) { // screen menu
            if (isBtnClicked(playBtn, screenX, screenY)) { // start
                System.out.println("=========PLAY==========");
                this.resetGame();
            } else if (isBtnClicked(topScoresBtn, screenX, screenY)) { // exit
                System.out.println("=========TOP SCORES==========");
                isShowTopScores = true;
            } else if (isBtnClicked(exitBtn, screenX, screenY)) { // topScores
                System.out.println("=========EXIT==========");
                Gdx.app.exit();
            }
        }
        return false;
    }

    private boolean isBtnClicked(Button btn, int screenX, int screenY) {
        /*System.out.println("screenX - screenY");
        System.out.println(screenX + " - " + screenY);
        System.out.println(btn.getX() + " - " + btn.getY());*/
        return btn != null &&
                btn.getX() < screenX && screenX < btn.getX() + btn.getWidth() && // width
                Gdx.graphics.getHeight() - btn.getY() > screenY && screenY > Gdx.graphics.getHeight() - btn.getY() - btn.getHeight(); // height
    }

    private void resetGame() {
        gameOverTime = 0f;
        score = 0;
        lives = 4;
        genSpeed = startGenSpeed;
        fruitArray.clear();
    }

    /*
    Đây là phần thường sử dụng trong Game này
    Phần này chứa các thao tác sẽ được thực hiện khi kéo ngón tay khỏi màn hình mà không rời khỏi nó.
    */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // trò chơi đang diễn ra
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
            Vector2 pos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
            int plusScore = 0;
            //Trong phần này, điểm số và mạng sống được thay đổi tùy theo loại đối tượng được chạm vào.
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
            //Trong phần này, chúng tôi sẽ chiết xuất những loại trái cây được chạm vào trong bộ phim truyền hình
            for (Fruit f : toRemove) {
                fruitArray.removeValue(f, true);
            }
        }
        return false;
    }

    private void createTouchMusic() {
        long id = sound.play(0.1f);
        sound.setPitch(id, 2);
        sound.setLooping(id, false);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
        fontGen.dispose();
        music.dispose();
        sound.dispose();
    }
}
