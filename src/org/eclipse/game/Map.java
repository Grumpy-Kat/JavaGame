package org.eclipse.game;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Graphics;

enum TileType {Dirt, Stone, Grass, Sand, Wood, Metal, Roof, Brick, Siding, Water, Character, Tree}

public class Map {
	static Map instance;

	public BufferedImage spritesheetImg;
	public BufferedImage mapImg;

	public final int tileWidth = 32;
	public final int tileHeight = 32;
	
	public HashMap<TileType, int[]> tiles = new HashMap<TileType, int[]>();
	public HashMap<Color, TileType> tileColors = new HashMap<Color, TileType>();
	
	public TileType[][] map;

	public static Map GetInstance() {
		if(Map.instance == null) {
			Map.instance = new Map();
		}
		return instance;
	}
	
	public Map() {
		if(Map.instance == null) {
			Map.instance = this;
		}
		System.out.println(new File(".").getAbsolutePath());
		try {
			Map.GetInstance().spritesheetImg = ImageIO.read(new File("res/spritesheet.png"));
			Map.GetInstance().mapImg = ImageIO.read(new File("res/map.png"));
		} catch (Exception e) {
			System.out.println("Image unable to be read!");
		}
		LoadTiles();
		LoadTileColors();
		CreateMap();
	}
	
	void LoadTiles() {
		Map.GetInstance().tiles.put(TileType.Dirt, new int[] {0, 0});
		Map.GetInstance().tiles.put(TileType.Stone, new int[] {1, 0});
		Map.GetInstance().tiles.put(TileType.Grass, new int[] {2, 0});
		Map.GetInstance().tiles.put(TileType.Sand, new int[] {3, 0});
		Map.GetInstance().tiles.put(TileType.Wood, new int[] {4, 0});
		Map.GetInstance().tiles.put(TileType.Metal, new int[] {5, 0});
		Map.GetInstance().tiles.put(TileType.Roof, new int[] {6, 0});
		Map.GetInstance().tiles.put(TileType.Brick, new int[] {7, 0});
		Map.GetInstance().tiles.put(TileType.Siding, new int[] {8, 0});
		Map.GetInstance().tiles.put(TileType.Water, new int[] {9, 0});
		Map.GetInstance().tiles.put(TileType.Character, new int[] {0, 1});
		Map.GetInstance().tiles.put(TileType.Tree, new int[] {1, 1});
	}
	
	void LoadTileColors() {
		Map.GetInstance().tileColors.put(new Color(255, 0, 0), TileType.Dirt);
		Map.GetInstance().tileColors.put(new Color(0, 0, 0), TileType.Stone);
		Map.GetInstance().tileColors.put(new Color(0, 255, 0), TileType.Grass);
		Map.GetInstance().tileColors.put(new Color(255, 255, 0), TileType.Sand);
		Map.GetInstance().tileColors.put(new Color(128, 128, 128), TileType.Wood);
		Map.GetInstance().tileColors.put(new Color(192, 192, 192), TileType.Metal);
		Map.GetInstance().tileColors.put(new Color(128, 0, 0), TileType.Roof);
		Map.GetInstance().tileColors.put(new Color(128, 0, 128), TileType.Brick);
		Map.GetInstance().tileColors.put(new Color(255, 255, 255), TileType.Siding);
		Map.GetInstance().tileColors.put(new Color(0, 0, 255), TileType.Water);
		Map.GetInstance().tileColors.put(new Color(128, 128, 0), TileType.Tree);
	}
	
	void CreateMap() {
		map = new TileType[Map.GetInstance().mapImg.getWidth()][Map.GetInstance().mapImg.getHeight()];
		Color[] colorKeys = Map.GetInstance().tileColors.keySet().toArray(new Color[0]);
		for(int x = 0; x < Map.GetInstance().mapImg.getWidth(); x++) {
			for(int y = 0; y < Map.GetInstance().mapImg.getWidth(); y++) {
				Color color = new Color(Map.GetInstance().mapImg.getRGB(x,y));
				for(int i = 0; i < colorKeys.length; i++) {
					if(colorKeys[i].getRGB() == color.getRGB()) {
						Map.GetInstance().map[x][y] = Map.GetInstance().tileColors.get(colorKeys[i]);
						break;
					}
				}
			}
		}
	}
	
	public void Render(Graphics g) {
		for(int x = 0; x < Map.GetInstance().map.length; x++) {
			for(int y = 0; y < Map.GetInstance().map[x].length; y++) {
				Draw(g, x, y, 32, 32, Map.GetInstance().map[x][y]);
			}
		}
	}
	
	void Draw(Graphics g, int x, int y, int width, int height, TileType tileType) {
		g.drawImage(Map.GetInstance().spritesheetImg, x * width, y * height, (x * width) + width, (y * height) + height, Map.GetInstance().tiles.get(tileType)[0] * Map.GetInstance().tileWidth, Map.GetInstance().tiles.get(tileType)[1] * Map.GetInstance().tileHeight, (Map.GetInstance().tiles.get(tileType)[0] * Map.GetInstance().tileWidth) + Map.GetInstance().tileWidth, (Map.GetInstance().tiles.get(tileType)[1] * Map.GetInstance().tileHeight) + Map.GetInstance().tileHeight, null);
	}
}