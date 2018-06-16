package org.eclipse.game;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Graphics;

enum TileType {Dirt, Stone, Grass, Sand, Wood, Metal, Roof, Brick, Siding, Water, Character, Tree}

public class Map {
	public static Map Instance;

	public static BufferedImage spritesheetImg;
	public static BufferedImage mapImg;

	public final int tileWidth = 32;
	public final int tileHeight = 32;
	
	public HashMap<TileType, int[]> tiles = new HashMap<TileType, int[]>();
	public HashMap<Color, TileType> tileColors = new HashMap<Color, TileType>();
	
	public TileType[][] map;

	public Map() {
		if(Map.Instance == null) {
			Map.Instance = this;
		}
		try {
			Map.Instance.spritesheetImg = ImageIO.read(new File("res/spritesheet.png"));
			Map.Instance.mapImg = ImageIO.read(new File("res/map.png"));
		} catch (Exception e) {
			System.out.println("Image unable to be read!");
		}
		CreateMap();
	}
	
	void LoadTiles() {
		Map.Instance.tiles.put(TileType.Dirt, new int[] {0, 0});
		Map.Instance.tiles.put(TileType.Stone, new int[] {1, 0});
		Map.Instance.tiles.put(TileType.Grass, new int[] {2, 0});
		Map.Instance.tiles.put(TileType.Sand, new int[] {3, 0});
		Map.Instance.tiles.put(TileType.Wood, new int[] {4, 0});
		Map.Instance.tiles.put(TileType.Metal, new int[] {5, 0});
		Map.Instance.tiles.put(TileType.Roof, new int[] {6, 0});
		Map.Instance.tiles.put(TileType.Brick, new int[] {7, 0});
		Map.Instance.tiles.put(TileType.Siding, new int[] {8, 0});
		Map.Instance.tiles.put(TileType.Water, new int[] {9, 0});
		Map.Instance.tiles.put(TileType.Character, new int[] {0, 1});
		Map.Instance.tiles.put(TileType.Tree, new int[] {1, 1});
	}
	
	void LoadTileColors() {
		Map.Instance.tileColors.put(new Color(255, 0, 0), TileType.Dirt);
		Map.Instance.tileColors.put(new Color(0, 0, 0), TileType.Stone);
		Map.Instance.tileColors.put(new Color(0, 255, 0), TileType.Grass);
		Map.Instance.tileColors.put(new Color(255, 255, 0), TileType.Sand);
		Map.Instance.tileColors.put(new Color(128, 128, 128), TileType.Wood);
		Map.Instance.tileColors.put(new Color(192, 192, 192), TileType.Metal);
		Map.Instance.tileColors.put(new Color(128, 0, 0), TileType.Roof);
		Map.Instance.tileColors.put(new Color(128, 0, 128), TileType.Brick);
		Map.Instance.tileColors.put(new Color(255, 255, 255), TileType.Siding);
		Map.Instance.tileColors.put(new Color(0, 0, 255), TileType.Water);
		Map.Instance.tileColors.put(new Color(128, 128, 0), TileType.Tree);
	}
	
	void CreateMap() {
		map = new TileType[Map.Instance.mapImg.getWidth()][Map.Instance.mapImg.getHeight()];
		for(int x = 0; x < Map.Instance.mapImg.getWidth(); x++) {
			for(int y = 0; y < Map.Instance.mapImg.getWidth(); y++) {
				Color color = new Color(Map.Instance.mapImg.getRGB(x,y));
				if(Map.Instance.tileColors.containsKey(color)) {
					Map.Instance.map[x][y] = Map.Instance.tileColors.get(color);
				}
			}
		}
	}
	
	void Draw(Graphics g, int x, int y, int width, int height, TileType tileType) {
		g.drawImage(Map.Instance.spritesheetImg, x, y, x + width,y + height, Map.Instance.tiles.get(tileType)[0] * tileWidth, Map.Instance.tiles.get(tileType)[1] * tileHeight, Map.Instance.tiles.get(tileType)[0] * tileWidth + tileWidth, Map.Instance.tiles.get(tileType)[1] * tileHeight + tileHeight, null);
	}
}