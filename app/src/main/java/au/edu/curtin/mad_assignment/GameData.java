package au.edu.curtin.mad_assignment;

import java.util.Random;

public class GameData
{
    private static final int[] GRASS = {R.drawable.ic_grass1, R.drawable.ic_grass2,
            R.drawable.ic_grass3, R.drawable.ic_grass4};

    private static final Random rng = new Random();

    private static GameData instance = null;

    private MapElement[][] map;

    private Settings settings;
    private int money;
    private int gameTime;

    public static GameData get()
    {
        if(instance == null)
        {
            instance = new GameData();
        }
        return instance;
    }

    private MapElement[][] generateGrid(int height, int width)
    {
        MapElement[][] grid = new MapElement[height][width];
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                MapElement element;

                grid[i][j] = new MapElement(true,
                        R.drawable.ic_grass1,
                        R.drawable.ic_grass2,
                        R.drawable.ic_grass3,
                        R.drawable.ic_grass4,
                        null);
            }
        }
        return grid;
    }

    protected GameData()
    {
        settings = Settings.get();
        this.map = generateGrid(settings.getMapHeight(), settings.getMapWidth());
    }

    public int getMoney()
    {
        return money;
    }

    public void setMoney(int money)
    {
        this.money = money;
    }

    public int getGameTime()
    {
        return gameTime;
    }

    public void setGameTime(int gameTime)
    {
        this.gameTime = gameTime;
    }

    public MapElement get(int i, int j)
    {
        return map[i][j];
    }

    public void reset()
    {
        this.map = generateGrid(settings.getMapHeight(), settings.getMapWidth());
    }
}
