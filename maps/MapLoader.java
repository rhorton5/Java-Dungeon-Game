package maps;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapLoader {
	public char [][] loadMap(String mapName) throws FileNotFoundException{
		File f = new File(System.getProperty("user.dir") + "/maps/" + mapName);
		Scanner fout = new Scanner(f);
		String dimensionData [] = fout.nextLine().split("x");
		int x = Integer.parseInt(dimensionData[0].trim()), y = Integer.parseInt(dimensionData[1].trim());
		char [][] map = new char[y][x];
		for(int i = 0; i < map.length; i++) {
			map[i] = fout.nextLine().toCharArray();
		}
		fout.close();
		return map;
	}
}
