
package com.npace.snake.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.npace.snake.framework.FileIO;

public class Settings {
    public static boolean soundEnabled = true;
    public static int[] highScores = new int[] {
            100, 80, 50, 30, 10
    };

    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(files.readFile(".droidsnake")));
            soundEnabled = Boolean.parseBoolean(in.readLine());
            for (int i = 0; i < 5; i++) {
                if (in.readLine() != null)
                    highScores[i] = Integer.parseInt(in.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void save(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(files.writeFile(".droidsnake")));
            out.write(Boolean.toString(soundEnabled));
            for (int i = 0; i < 5; i++) {
                out.write(Integer.toString(highScores[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addScore(int score) {
        for (int i = 0; i < 5; i++) {
            if (highScores[i] < score) {
                for (int j = 4; j < i; j--) {
                    highScores[j] = highScores[j - 1];
                }
                highScores[i] = score;
                break;
            }
        }
    }
}
