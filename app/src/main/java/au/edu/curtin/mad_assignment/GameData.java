package au.edu.curtin.mad_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import au.edu.curtin.mad_assignment.GameDataSchema.SettingsTable;
import au.edu.curtin.mad_assignment.GameDataSchema.PlayerDataTable;

public class GameData
{
    private static GameData instance = null;

    private MapElement[][] map;

    private Settings settings;
    private int money;
    private int gameTime;

    private SQLiteDatabase db;
    private List<MapElement> mapDBList;
    private SettingsCursor setCursor;


    public void load(Context context, boolean enterGame)
    {

        PlayerDataCursor playerCursor;

        this.db = new GameDataDbHelper(context.getApplicationContext()).getWritableDatabase();
        getDBSettings(db);
        playerCursor = getDBPlayerData(db);
        getDBstructures(db);

        if(enterGame)
        {
            storeDefaultData(setCursor, playerCursor);//store defaults if tables not created yet
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
        this.settings = new Settings();
        this.money = settings.getInitialMoney();
        this.gameTime = 0;
        this.mapDBList = new ArrayList<>();
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

    public List<MapElement> getMapDBList()
    {
        return mapDBList;
    }

    public void setMapDBList(List<MapElement> mapDBList)
    {
        this.mapDBList = mapDBList;
    }

    public void initGame()
    {
        this.map = generateGrid(settings.getMapHeight(), settings.getMapWidth());
        if(!mapDBList.isEmpty())
        {
            for(MapElement ele : mapDBList)
            {
                map[ele.getRow()][ele.getCol()] = ele;//
            }
        }
    }

    public void reset()
    {
        this.settings = new Settings();
        this.gameTime = 0;
        this.money = settings.getInitialMoney();
        this.map = generateGrid(settings.getMapHeight(), settings.getMapWidth());
        editSetting(settings);
        updateGameData();
    }

    public void editSetting(Settings sett)
    {
        if(!hasBegun())//edit only if game hasn't started yet
        {
            ContentValues cv = new ContentValues();
            cv.put(SettingsTable.Cols.MAP_HEIGHT, sett.getMapHeight());
            cv.put(SettingsTable.Cols.MAP_WIDTH, sett.getMapWidth());
            cv.put(SettingsTable.Cols.MONEY, sett.getInitialMoney());

            String[] where = { "1" };

            db.update(SettingsTable.NAME, cv, SettingsTable.Cols.ID + " = ?", where);
            this.settings = sett;
            this.money = settings.getInitialMoney();
//        this.map = generateGrid(settings.getMapHeight(), settings.getMapWidth());
        }
    }

    //check if game has begun by checking if game settings has been set in database
    private boolean hasBegun()
    {
        return this.setCursor.getCount() > 0;
    }

    private void updateGameData()
    {
        ContentValues cv = new ContentValues();
        cv.put(PlayerDataTable.Cols.CURR_MONEY, this.money);

        cv.put(PlayerDataTable.Cols.GAME_TIME, this.gameTime);

        String[] where = { "1" };

        db.update(PlayerDataTable.NAME, cv, PlayerDataTable.Cols.PLAYER_ID + " = ?", where);
    }

    private void getDBSettings(SQLiteDatabase db)
    {
        this.setCursor = new SettingsCursor(db.query(SettingsTable.NAME, null, null, null,
                null, null, null, null));
        try
        {
            this.setCursor.moveToFirst();
            while(!this.setCursor.isAfterLast())
            {
                this.settings = this.setCursor.getSettings();
                this.setCursor.moveToNext();
            }
        }
        finally
        {
            this.setCursor.close();
        }
    }

    private PlayerDataCursor getDBPlayerData(SQLiteDatabase db)
    {
        PlayerDataCursor playerCursor = new PlayerDataCursor(db.query(PlayerDataTable.NAME, null, null, null,
                null, null, null, null));
        try
        {
            playerCursor.moveToFirst();
            while(!playerCursor.isAfterLast())
            {
                this.money = playerCursor.getCurrMoney();
                this.gameTime = playerCursor.getGameTime();
                playerCursor.moveToNext();
            }
        }
        finally
        {
            playerCursor.close();
        }

        return playerCursor;
    }

    private void getDBstructures(SQLiteDatabase db)
    {
        MapElementCursor mapCursor = new MapElementCursor(db.query(GameDataSchema.MapElementTable.NAME, null, null, null,
                null, null, null, null));
        try
        {
            mapCursor.moveToFirst();
            while(!mapCursor.isAfterLast())
            {
                this.mapDBList.add(mapCursor.getStructures());
                mapCursor.moveToNext();
            }
        }
        finally
        {
            mapCursor.close();
        }
    }

    private void storeDefaultData(SettingsCursor setCursor, PlayerDataCursor playerDataCursor)
    {
        //only create defaults in databases are empty
        if(setCursor.getCount() == 0)
        {
            ContentValues cv = new ContentValues();
            cv.put(SettingsTable.Cols.ID, 1);
            cv.put(SettingsTable.Cols.MAP_HEIGHT, settings.getMapHeight());
            cv.put(SettingsTable.Cols.MAP_WIDTH, settings.getMapWidth());
            cv.put(SettingsTable.Cols.MONEY, settings.getInitialMoney());
            db.insert(SettingsTable.NAME, null, cv);
        }

        if(playerDataCursor.getCount() == 0)
        {
            ContentValues cv = new ContentValues();
            cv.put(PlayerDataTable.Cols.PLAYER_ID, 1);
            cv.put(PlayerDataTable.Cols.CURR_MONEY, settings.getInitialMoney());
            cv.put(PlayerDataTable.Cols.GAME_TIME, 0);
            db.insert(PlayerDataTable.NAME, null, cv);
        }
    }
}
