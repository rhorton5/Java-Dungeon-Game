package main;
import java.io.FileNotFoundException;
import java.util.Scanner;

import character.PlayerCharacter;
import character.PlayerCharacterCreator;
import dungeon.Dungeon;
import maps.MapLoader;
public class Main {
    public static void main(String [] args) throws FileNotFoundException{
        Scanner kb = new Scanner(System.in);
        PlayerCharacterCreator playerCharacterCreator = new PlayerCharacterCreator(kb);
        PlayerCharacter pc = playerCharacterCreator.createCharacter();
        MapLoader maps = new MapLoader();
        char [][] map = maps.loadMap("test1.DUNGEON");
        Dungeon dungeon = new Dungeon(map,2,2,pc);
        dungeon.dungeonOverworldMenu(kb);
        kb.close();
    }
}
