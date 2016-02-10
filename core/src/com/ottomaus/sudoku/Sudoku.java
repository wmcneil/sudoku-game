package com.ottomaus.sudoku;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;

public class Sudoku extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Stage stage;

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

    Skin skin;

    public Table createGrid (int[][] board) {
        Table grid = new Table();
        for(int y=0;y<9;y++) {
            for (int x = 0; x < 9; x++) {
                grid.add(new TextButton(String.valueOf(board[y][x]), skin)).expand().fill();
            }
            grid.row();
        }
        return grid;
    }

    public Table createNumberSelection (int low, int high) {
        Table grid = new Table();
            for (int x = low; x < high; x++) {
                grid.add(new TextButton(String.valueOf(x), skin)).expand().fill();
            }
            grid.row();
        return grid;
    }

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		Table table = new Table();
		Table grid = new Table();
        skin = new Skin(Gdx.files.internal("data/uiskin.json"), new TextureAtlas("data/uiskin.atlas"));
		Label button = new Label("Seppuku", skin);
        table.setDebug(true);
		table.setFillParent(true);
		table.add(button).height(Value.percentHeight(0.1f, table)).fill();
		table.row();
		table.add(createGrid(board)).fill().expand();
        table.row();
        table.add(createNumberSelection(1, 10)).height(Value.percentHeight(0.1f, table)).fill().padTop(5f).padBottom(5f);
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
	}
}
