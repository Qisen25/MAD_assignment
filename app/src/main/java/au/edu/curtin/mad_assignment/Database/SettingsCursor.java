package au.edu.curtin.mad_assignment.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import au.edu.curtin.mad_assignment.Database.GameDataSchema.SettingsTable;
import au.edu.curtin.mad_assignment.Model.Settings;
/*
    cursor for retrieving settings from database settings table
 */
public class SettingsCursor extends CursorWrapper
{
    public SettingsCursor(Cursor cursor)
    {
        super(cursor);
    }

    public Settings getSettings()
    {
        int height = getInt(getColumnIndex(SettingsTable.Cols.MAP_HEIGHT));
        int width = getInt(getColumnIndex(SettingsTable.Cols.MAP_WIDTH));
        int money = getInt(getColumnIndex(SettingsTable.Cols.MONEY));
        double tax = getDouble(getColumnIndex(SettingsTable.Cols.TAX));

        return new Settings(width, height, money, tax);
    }
}
