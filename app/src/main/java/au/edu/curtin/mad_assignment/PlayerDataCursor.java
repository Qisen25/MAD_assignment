package au.edu.curtin.mad_assignment;

import android.database.Cursor;
import android.database.CursorWrapper;

import au.edu.curtin.mad_assignment.GameDataSchema.SettingsTable;

/*
    cursor retrieve game time and the money player currently has
 */
public class PlayerDataCursor extends CursorWrapper
{
    public PlayerDataCursor(Cursor cursor)
    {
        super(cursor);
    }

    public int getCurrMoney()
    {
        return getInt(getColumnIndex(GameDataSchema.PlayerDataTable.Cols.CURR_MONEY));
    }

    public int getGameTime()
    {
        return getInt(getColumnIndex(GameDataSchema.PlayerDataTable.Cols.GAME_TIME));
    }
}
