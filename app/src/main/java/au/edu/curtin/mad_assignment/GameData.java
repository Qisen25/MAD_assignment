package au.edu.curtin.mad_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import au.edu.curtin.mad_assignment.GameDataSchema.SettingsTable;

public class GameData
{
    private static GameData instance = null;

    private MapElement[][] map;

    private Settings settings;
    private int money;
    private int gameTime;

    private SQLiteDatabase db;
    private SettingsCursor setCursor;

    public void load(Context context)
    {
        this.db = new GameDataDbHelper(context.getApplicationContext()).getWritableDatabase();
        setCursor = new SettingsCursor(db.query(SettingsTable.NAME, null, null, null,
                null, null, null, null));
        try
        {
            setCursor.moveToFirst();
            while(!setCursor.isAfterLast())
            {
                this.settings = setCursor.getSettings();
                setCursor.moveToNext();
            }
        }
        finally
        {
            setCursor.close();
        }
    }

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

                grid[i][j] = new MapElement(true,null);
            }
        }
        return grid;
    }

    protected GameData()
    {
        settings = new Settings();
        this.map = generateGrid(settings.getMapHeight(), settings.getMapWidth());
        this.money = settings.getInitialMoney();
    }

    public Settings getSettings()
    {
        return settings;
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

    public void editSetting(Settings sett)
    {
        ContentValues cv = new ContentValues();
        cv.put(SettingsTable.Cols.MAP_HEIGHT, sett.getMapHeight());
        cv.put(SettingsTable.Cols.MAP_WIDTH, sett.getMapWidth());
        cv.put(SettingsTable.Cols.MONEY, sett.getInitialMoney());

        String[] where = { "1" };

        db.update(SettingsTable.NAME, cv, SettingsTable.Cols.ID + " = ?", where);
    }

    public void storeDefaultSettings()
    {
        if(setCursor.getCount() == 0)
        {
            ContentValues cv = new ContentValues();
            cv.put(SettingsTable.Cols.ID, 1);
            cv.put(SettingsTable.Cols.MAP_HEIGHT, settings.getMapHeight());
            cv.put(SettingsTable.Cols.MAP_WIDTH, settings.getMapWidth());
            cv.put(SettingsTable.Cols.MONEY, settings.getInitialMoney());
            db.insert(SettingsTable.NAME, null, cv);
        }
    }

    public int getDBCount()
    {
        return setCursor.getCount();
    }
}
