package com.ottomaus.sudoku;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class Sudoku extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Stage stage;
    TextButton[] gridButtons;

    int selectedNumber = 0;

    int[][] board_solved = { { 4 , 8 , 3 , 9 , 2 , 1 , 6 , 5 , 7 },
        { 9 , 6 , 7 , 3 , 4 , 5 , 8 , 2 , 1 },
        { 2 , 5 , 1 , 8 , 7 , 6 , 4 , 9 , 3 },
        { 5 , 4 , 8 , 1 , 3 , 2 , 9 , 7 , 6 },
        { 7 , 2 , 9 , 5 , 6 , 4 , 1 , 3 , 8 },
        { 1 , 3 , 6 , 7 , 9 , 8 , 2 , 4 , 5 },
        { 3 , 7 , 2 , 6 , 8 , 9 , 5 , 1 , 4 },
        { 8 , 1 , 4 , 2 , 5 , 3 , 7 , 6 , 9 },
        { 6 , 9 , 5 , 4 , 1 , 7 , 3 , 8 , 2 }};

    int[][] board = { { 0 , 0 , 3 , 0 , 2 , 0 , 6 , 0 , 0 },
        { 9 , 0 , 0 , 3 , 0 , 5 , 0 , 0 , 1 },
        { 0 , 0 , 1 , 8 , 0 , 6 , 4 , 0 , 0 },
        { 0 , 0 , 8 , 1 , 0 , 2 , 9 , 0 , 0 },
        { 7 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 8 },
        { 0 , 0 , 6 , 7 , 0 , 8 , 2 , 0 , 0 },
        { 0 , 0 , 2 , 6 , 0 , 9 , 5 , 0 , 0 },
        { 8 , 0 , 0 , 2 , 0 , 3 , 0 , 0 , 9 },
        { 0 , 0 , 5 , 0 , 1 , 0 , 3 , 0 , 0 },
    };

    Skin skin,newSkin;

    public boolean isSolved() {
        for(int y=0;y<9;y++){
            for(int x=0;x<9;x++){
                String label = gridButtons[x + y * 9].getText().toString().trim();
                if (label.length() == 0) label = "0";
                if (Integer.valueOf(label) != board_solved[y][x]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Table createGrid (int[][] board) {
        Table grid = new Table();

        int prefHeight = Math.round((stage.getHeight() * 0.7f) / 9f);
        int prefWidth = Math.round((stage.getWidth()) / 9f);
        int prefSize;
        if(prefWidth < prefHeight){
            prefSize = prefWidth;
        } else {
            prefSize = prefHeight;
        }
        gridButtons = new TextButton[9*9];

        for(int y=0;y<9;y++) {
            for (int x = 0; x < 9; x++) {
                String buttonLabel = " ";
                if(board[y][x] != 0) buttonLabel = String.valueOf(board[y][x]);
                TextButton button = new TextButton(buttonLabel, skin);

                /* TODO: Fix this unholy mess */
                if(((x/3)%2==0 && (y/3)%2==0) || ((x/3)%2!=0 && (y/3)%2!=0)){
                    if(board[y][x] != 0) {
                        button = new TextButton(buttonLabel, skin, "disabled-alt");
                        button.setTouchable(Touchable.disabled);
                    } else {
                        button = new TextButton(buttonLabel, skin, "alternate");
                    }
                } else if(board[y][x] != 0){
                    button = new TextButton(buttonLabel, skin, "disabled");
                    button.setTouchable(Touchable.disabled);
                }

                grid.add(button).minWidth(prefSize).minHeight(prefSize);

                final int localX = x;
                final int localY = y;

                button.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                            selectedNumber = board_solved[localY][localX];
                        }

                        System.out.println("x:" + String.valueOf(localX) + "y:" + String.valueOf(localY));
                        String label;
                        if(selectedNumber == 0) {
                            label = " ";
                        } else {
                            label = String.valueOf(selectedNumber);
                        }
                        gridButtons[localX + localY * 9].setText(label);

                        if(isSolved() == true) {
                            System.out.println("SOLVED");
                        }
                    }
                });

                gridButtons[x + y * 9] = button;
            }
            grid.row();
        }

        return grid;
    }

    public Table createNumberSelection (int low, int high) {
        Table grid = new Table();
        ButtonGroup<TextButton> group = new ButtonGroup<TextButton>();

        for (int x = low; x < high; x++) {
            final int xCopy = x;
            int spacing = 0;
            String label = String.valueOf(x);
            TextButton button = new TextButton(label, skin, "toggle");
            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    selectedNumber = xCopy;
                }
            });
            grid.add(button).expand().fill().padRight(spacing);
            group.add(button);
        }
        TextButton eraserButton = new TextButton("\uf12d", skin, "toggle-icon");
        eraserButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectedNumber = 0;
            }
        });
        grid.add(eraserButton).expand().fill().padLeft(20);
        group.add(eraserButton);
        grid.row();
        return grid;
    }

	@Override
	public void create () {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/fonts/segoesc.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 18;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("data/fonts/fontawesome.ttf"));
        parameter.characters="\uF12D";
        BitmapFont fontAwesome = generator.generateFont(parameter);
        TextureAtlas atlas;

		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
        //skin = new Skin(Gdx.files.internal("data/uiskin.json"), new TextureAtlas("data/uiskin.atlas"));
        skin = new Skin();
        skin.add("default-font", font12, BitmapFont.class);
        skin.add("font-awesome", fontAwesome, BitmapFont.class);
        skin.addRegions(new TextureAtlas("data/ui/ui.atlas"));
        skin.load(Gdx.files.internal("data/ui/ui.json"));
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		Table table = new Table();
        table.setDebug(false);
		table.setFillParent(true);
        Label titleLabel = new Label("NeguSudoku", skin);
		table.add(titleLabel).height(Value.percentHeight(0.1f, table)).fill();
        titleLabel.setAlignment(Align.center);
		table.row();
		table.add(createGrid(board)).fill().expand();
        table.row();
        table.add(createNumberSelection(1, 10)).height(Value.percentHeight(0.1f, table)).fill().pad(20f,5f,0f,5f);
        stage.addActor(table);
	}

    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }

	@Override
	public void render () {
		/*Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
	}
}
