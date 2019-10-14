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
        final int HEIGHT_RANGE = 256;
        final int INLAND_BIAS = 24;
        final int AREA_SIZE = 1;
        final int SMOOTHING_ITERATIONS = 2;

        int[][] heightField = new int[height][width];
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                heightField[i][j] =
                        rng.nextInt(HEIGHT_RANGE)
                                + INLAND_BIAS * (
                                Math.min(Math.min(i, j), Math.min(height - i - 1, width - j - 1)) -
                                        Math.min(height, width) / 4);
            }
        }

        int[][] newHf = new int[height][width];
        for(int s = 0; s < SMOOTHING_ITERATIONS; s++)
        {
            for(int i = 0; i < height; i++)
            {
                for(int j = 0; j < width; j++)
                {
                    int areaSize = 0;
                    int heightSum = 0;

                    for(int areaI = Math.max(0, i - AREA_SIZE);
                        areaI < Math.min(height, i + AREA_SIZE + 1);
                        areaI++)
                    {
                        for(int areaJ = Math.max(0, j - AREA_SIZE);
                            areaJ < Math.min(width, j + AREA_SIZE + 1);
                            areaJ++)
                        {
                            areaSize++;
                            heightSum += heightField[areaI][areaJ];
                        }
                    }

                    newHf[i][j] = heightSum / areaSize;
                }
            }

            int[][] tmpHf = heightField;
            heightField = newHf;
            newHf = tmpHf;
        }

        MapElement[][] grid = new MapElement[height][width];
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                MapElement element;

                grid[i][j] = new MapElement(true,
                        GRASS[rng.nextInt(GRASS.length)],
                        GRASS[rng.nextInt(GRASS.length)],
                        GRASS[rng.nextInt(GRASS.length)],
                        GRASS[rng.nextInt(GRASS.length)],
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
}
