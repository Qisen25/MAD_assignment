package au.edu.curtin.mad_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import au.edu.curtin.mad_assignment.GameDataSchema.SettingsTable;
import au.edu.curtin.mad_assignment.GameDataSchema.PlayerDataTable;
import au.edu.curtin.mad_assignment.GameDataSchema.MapElementTable;
/*
    class handling the games data
 */
public class GameData
{
    private static GameData instance = null;

    private MapElement[][] map;

    private Settings settings;
    private int money;
    private int gameTime;
    private int population, recentIncome;
    private double employmentRate;

    private SQLiteDatabase db;
    private List<MapElement> mapDBList;
    private SettingsCursor setCursor;
    private boolean gameOver = false;

    public void load(Context context, boolean enterGame)
    {
        PlayerDataCursor playerCursor;

        this.db = new GameDataDbHelper(context.getApplicationContext()).getWritableDatabase();
        getDBSettings(db);
        playerCursor = getDBPlayerData(db);
        getDBstructures(db);

        if(enterGame)// store defaults if begin button is pressed in mainactivity
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

    /*
        create grid
     */
    private MapElement[][] generateGrid(int height, int width)
    {
        MapElement[][] grid = new MapElement[height][width];
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                grid[i][j] = new MapElement(true,null, i, j, null);
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

    public int getPopulation()
    {
        return population;
    }

    public String getRecentIncomeString()
    {
        String str = "";
        if(this.recentIncome > 0)
        {
            str = "+" + this.recentIncome;
        }
        else
        {
            str = String.valueOf(this.recentIncome);
        }
        return str;
    }

    public double getEmploymentRate()
    {
        return this.employmentRate;
    }

    //get percentage of emploment rate to 2 decimal place
    public String getEmployPercent()
    {
        return String.format("%.2f", (this.employmentRate * 100));
    }

    //check if game over condition is met
    public boolean isGameOver()
    {
        return gameOver;
    }

    public void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }

    //initialise map and player stats
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
        this.recentIncome = this.gameLogic();
    }

    public int getSize()
    {
        return this.mapDBList.size();
    }

    //to reset game, currently not used by game
    public void reset()
    {
        this.settings = new Settings();
        this.gameTime = 0;
        this.money = settings.getInitialMoney();
        this.map = generateGrid(settings.getMapHeight(), settings.getMapWidth());
        editSetting(settings);
        updatePlayData();
    }

    //edit settings
    public void editSetting(Settings sett)
    {
        if(!hasBegun())//edit only if game hasn't started yet
        {
            ContentValues cv = new ContentValues();
            cv.put(SettingsTable.Cols.MAP_HEIGHT, sett.getMapHeight());
            cv.put(SettingsTable.Cols.MAP_WIDTH, sett.getMapWidth());
            cv.put(SettingsTable.Cols.TAX, sett.getTaxRate());
            cv.put(SettingsTable.Cols.MONEY, sett.getInitialMoney());

            String[] where = { "1" };

            db.update(SettingsTable.NAME, cv, SettingsTable.Cols.ID + " = ?", where);
            this.settings = sett;
            this.money = settings.getInitialMoney();
        }
        else//editable settings after start
        {
            ContentValues cv = new ContentValues();
            cv.put(SettingsTable.Cols.TAX, sett.getTaxRate());

            String[] where = { "1" };

            db.update(SettingsTable.NAME, cv, SettingsTable.Cols.ID + " = ?", where);
            this.settings.setTaxRate(sett.getTaxRate());//update tax rate
        }
    }

    //check if game has begun by checking if game settings has been set in database
    private boolean hasBegun()
    {
        return this.setCursor.getCount() > 0;
    }

    //update database player stats
    private void updatePlayData()
    {
        ContentValues cv = new ContentValues();
        cv.put(PlayerDataTable.Cols.CURR_MONEY, this.money);

        cv.put(PlayerDataTable.Cols.GAME_TIME, this.gameTime);

        String[] where = { "1" };

        db.update(PlayerDataTable.NAME, cv, PlayerDataTable.Cols.PLAYER_ID + " = ?", where);
    }

    //adding structures to game data
    protected void addStructure(MapElement ele)
    {
        this.mapDBList.add(ele);

        ContentValues cv = new ContentValues();
        cv.put(MapElementTable.Cols.ROW_ID, ele.getRow());
        cv.put(MapElementTable.Cols.COL_ID, ele.getCol());
        cv.put(MapElementTable.Cols.STRUCT_ID, ele.getStructure().getImageId());
        cv.put(MapElementTable.Cols.STRUCT_TYPE, ele.getStructure().getType());
        cv.put(MapElementTable.Cols.OWNER_NAME, ele.getOwnerName());
        db.insert(MapElementTable.NAME, null, cv);

        this.money -= ele.getStructure().getCost();//decrease money when building
        updatePlayData();
    }

    //update map element ownernames and images
    protected void updateMapElement(MapElement ele)
    {
        ContentValues cv = new ContentValues();
        cv.put(MapElementTable.Cols.OWNER_NAME, ele.getOwnerName());
        cv.put(MapElementTable.Cols.PHOTO, this.convertToByte(ele.getImage()));

        String[] where = {String.valueOf(ele.getRow()), String.valueOf(ele.getCol())};

        db.update(MapElementTable.NAME, cv, MapElementTable.Cols.ROW_ID + " = ? AND " + MapElementTable.Cols.COL_ID + " = ?", where);
    }

    //remove structure
    protected void removeStructure(MapElement ele)
    {
        this.mapDBList.remove(ele);

        String[] where = {String.valueOf(ele.getRow()), String.valueOf(ele.getCol())};
        db.delete(MapElementTable.NAME, MapElementTable.Cols.ROW_ID + " = ? AND " + MapElementTable.Cols.COL_ID + " = ?", where);
    }

    //close database to prevent errors when not using
    protected void closeDB()
    {
        if(this.db != null)
        {
            db.close();
        }
    }

    //increase game time logic
    protected void endTurn()
    {
        this.gameTime++;
        this.recentIncome = this.gameLogic();
        this.money += this.recentIncome;

        this.updatePlayData();
    }

    //function calculates current income based on current city/game setup
    protected int gameLogic()
    {
        int shopSize = this.settings.getShopSize();
        this.population = this.settings.getFamilySize() * this.nBuildings("house");
        int nCommercial = this.nBuildings("commercial");
        this.employmentRate = 0.0;
        int salary = this.settings.getSalary();
        double taxRate = this.settings.getTaxRate();
        int serviceCost = this.settings.getServiceCost();

        if(population > 0)//make population greater than 0 to avoid divide by zero
        {
            this.employmentRate = Math.min(1.0,(double)(nCommercial * shopSize) / (double)(population));
        }

        return (int)(population * (employmentRate * salary * taxRate - serviceCost));
    }

    //get saved settings
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

    //get saved player stats
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

    //retrieving structures existing in database
    private void getDBstructures(SQLiteDatabase db)
    {
        MapElementCursor mapCursor = new MapElementCursor(db.query(GameDataSchema.MapElementTable.NAME, null, null, null,
                null, null, null, null));
        List<MapElement> temp = new ArrayList<>();

        try
        {
            mapCursor.moveToFirst();
            while(!mapCursor.isAfterLast())
            {
                temp.add(mapCursor.getStructures());
                mapCursor.moveToNext();
            }
        }
        finally
        {
            mapCursor.close();
        }

        this.mapDBList = temp;
    }

    //function to store default when player has newly started
    private void storeDefaultData(SettingsCursor setCursor, PlayerDataCursor playerDataCursor)
    {
        //only create defaults in databases are empty
        //default settings
        if(setCursor.getCount() == 0)
        {
            ContentValues cv = new ContentValues();
            cv.put(SettingsTable.Cols.ID, 1);
            cv.put(SettingsTable.Cols.MAP_HEIGHT, settings.getMapHeight());
            cv.put(SettingsTable.Cols.MAP_WIDTH, settings.getMapWidth());
            cv.put(SettingsTable.Cols.TAX, settings.getTaxRate());
            cv.put(SettingsTable.Cols.MONEY, settings.getInitialMoney());
            db.insert(SettingsTable.NAME, null, cv);
        }

        //player starting stats
        if(playerDataCursor.getCount() == 0)
        {
            ContentValues cv = new ContentValues();
            cv.put(PlayerDataTable.Cols.PLAYER_ID, 1);
            cv.put(PlayerDataTable.Cols.CURR_MONEY, settings.getInitialMoney());
            cv.put(PlayerDataTable.Cols.GAME_TIME, 0);
            db.insert(PlayerDataTable.NAME, null, cv);
        }
    }

    //get number of types of existing buildings
    private int nBuildings(String type)
    {
        int num = 0;

        for(MapElement m : mapDBList)
        {

            if(m.getStructure() instanceof  Residential && type.equals("house"))
            {
                num++;
            }
            else if(m.getStructure() instanceof Commercial && type.equals("commercial"))
            {
                num++;
            }
            else if(m.getStructure() instanceof Road && type.equals("road"))
            {
                num++;
            }
        }

        return num;
    }

    /*
        function to convert bitmap to byte array, needed in order
        to store bitmap in database
     */
    private byte[] convertToByte(Bitmap bm)
    {
        if(bm != null)
        {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bout);
            return bout.toByteArray();
        }
        else
        {
            return null;
        }
    }
}
