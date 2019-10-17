package au.edu.curtin.mad_assignment;

import android.database.Cursor;
import android.database.CursorWrapper;

import au.edu.curtin.mad_assignment.GameDataSchema.MapElementTable;

public class MapElementCursor extends CursorWrapper
{
    public MapElementCursor(Cursor cursor)
    {
        super(cursor);
    }

    public MapElement getStructures()
    {
        MapElement element = null;

        int row = getInt(getColumnIndex(MapElementTable.Cols.ROW_ID));
        int col = getInt(getColumnIndex(MapElementTable.Cols.COL_ID));
        int structID = getInt(getColumnIndex(MapElementTable.Cols.STRUCT_ID));
        String type = getString(getColumnIndex(MapElementTable.Cols.STRUCT_TYPE));
        String owner = getString(getColumnIndex(MapElementTable.Cols.OWNER_NAME));

        if(type.equals("House"))
        {
            element = new MapElement(false, new Residential(structID), row, col);
            element.setOwnerName(owner);
        }
        else if(type.equals("Commercial"))
        {
            element = new MapElement(false, new Commercial(structID), row, col);
            element.setOwnerName(owner);
        }
        else if(type.equals("Road"))
        {
            element = new MapElement(false, new Road(structID), row, col);
            element.setOwnerName(owner);
        }

        return element;
    }
}
