package au.edu.curtin.mad_assignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Map;

import au.edu.curtin.mad_assignment.GameDataSchema.SettingsTable;
import au.edu.curtin.mad_assignment.GameDataSchema.MapElementTable;
import au.edu.curtin.mad_assignment.GameDataSchema.PlayerDataTable;

public class GameDataDbHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "GameData.db";

    public GameDataDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //settings
        db.execSQL("CREATE TABLE " + SettingsTable.NAME + "(" +
                SettingsTable.Cols.ID + " INTEGER, " +
                SettingsTable.Cols.MAP_HEIGHT + " INTEGER, " +
                SettingsTable.Cols.MAP_WIDTH + " INTEGER, " +
                SettingsTable.Cols.TAX + " REAL, " +
                SettingsTable.Cols.MONEY + " INTEGER)");

        //player data
        db.execSQL("CREATE TABLE " + PlayerDataTable.NAME + "(" +
                PlayerDataTable.Cols.PLAYER_ID + " INTEGER, " +
                PlayerDataTable.Cols.CURR_MONEY + " INTEGER, " +
                PlayerDataTable.Cols.GAME_TIME + " INTEGER)");

        //map element table
        db.execSQL("CREATE TABLE " + MapElementTable.NAME + "(" +
                MapElementTable.Cols.ROW_ID + " INTEGER, " +
                MapElementTable.Cols.COL_ID + " INTEGER, " +
                MapElementTable.Cols.STRUCT_ID + " INTEGER, " +
                MapElementTable.Cols.STRUCT_TYPE + " TEXT, " +
                MapElementTable.Cols.OWNER_NAME + " TEXT, " +
                MapElementTable.Cols.PHOTO + " BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
