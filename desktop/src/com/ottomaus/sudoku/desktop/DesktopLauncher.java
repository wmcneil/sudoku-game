package com.ottomaus.sudoku.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.ottomaus.sudoku.Sudoku;

public class DesktopLauncher {
	public static void main (String[] arg) {
        Settings settings = new Settings();
        settings.maxWidth = 512;
        settings.maxHeight = 512;
        TexturePacker.process(settings, "data/ui", "data/ui", "ui");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        boolean portrait = false;
        if(portrait == true) {
            config.width = 800;
            config.height = 1280;
        } else {
            config.width = 1280;
            config.height = 800;
        }
		new LwjglApplication(new Sudoku(), config);
	}
}
