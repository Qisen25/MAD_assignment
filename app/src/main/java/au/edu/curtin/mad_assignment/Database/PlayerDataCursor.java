package au.edu.curtin.mad_assignment.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

/*
    cursor retrieve game time and the money player currently has saved in database
    player data table
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
