package com.stephenklop.breakingbadassesment.data;

import android.app.Application;

import com.stephenklop.breakingbadassesment.domain.Character;

import java.util.ArrayList;
import java.util.List;

public class LocalAppStorage extends Application {
    private static List<Character> characters = new ArrayList<>();

    public static void setCharacters(List<Character> characters) {
        LocalAppStorage.characters = characters;
    }

    public static List<Character> getCharacters() {
        return characters;
    }

    public static Character getCharacter(int characterId) {
        for(Character character : characters) {
            if(character.getmId() == characterId) {
                return character;
            }
        }

        return null;
    }
}
