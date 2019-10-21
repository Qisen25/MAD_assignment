package au.edu.curtin.mad_assignment.Database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import au.edu.curtin.mad_assignment.Database.GameDataSchema.MapElementTable;
import au.edu.curtin.mad_assignment.Model.Commercial;
import au.edu.curtin.mad_assignment.Model.MapElement;
import au.edu.curtin.mad_assignment.Model.Residential;
import au.edu.curtin.mad_assignment.Model.Road;

/*
    cursor for retrieving map elements with structures from database map elements table
 */
public class MapElementCursor extends CursorWrapper
{
    public MapElementCursor(Cursor cursor)
    {
        super(cursor);
    }

    public MapElement getStructures()
    {
        MapElement element = null;
        Bitmap photo = null;

        int row = getInt(getColumnIndex(MapElementTable.Cols.ROW_ID));
        int col = getInt(getColumnIndex(MapElementTable.Cols.COL_ID));
        int structID = getInt(getColumnIndex(MapElementTable.Cols.STRUCT_ID));
        String type = getString(getColumnIndex(MapElementTable.Cols.STRUCT_TYPE));
        String owner = getString(getColumnIndex(MapElementTable.Cols.OWNER_NAME));

        //convert img back to bitmap
        byte[] img = getBlob(getColumnIndex(MapElementTable.Cols.PHOTO));
        if(img != null)
        {
            photo = BitmapFactory.decodeByteArray(img, 0, img.length);
        }

        if(type.equals("Residential"))
        {
            element = new MapElement(false, new Residential(structID), row, col, photo);
            element.setOwnerName(owner);
        }
        else if(type.equals("Commercial"))
        {
            element = new MapElement(false, new Commercial(structID), row, col, photo);
            element.setOwnerName(owner);
        }
        else if(type.equals("Road"))
        {
            element = new MapElement(false, new Road(structID), row, col, photo);
            element.setOwnerName(owner);
        }

        return element;
    }
}
