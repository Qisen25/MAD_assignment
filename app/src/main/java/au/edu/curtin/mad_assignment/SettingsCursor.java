package au.edu.curtin.mad_assignment;

import android.database.Cursor;
import android.database.CursorWrapper;

import au.edu.curtin.mad_assignment.GameDataSchema.SettingsTable;

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
