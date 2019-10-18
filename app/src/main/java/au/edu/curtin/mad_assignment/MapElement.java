package au.edu.curtin.mad_assignment;

import android.graphics.Bitmap;

public class MapElement
{
    private boolean buildable;
    private Structure structure;
    private Bitmap image;
    private String ownerName;
    private int row;
    private int col;

    public MapElement(boolean buildable, Structure structure, int row, int col)
    {
        this.buildable = buildable;
        this.structure = structure;
        this.row = row;
        this.col = col;
    }

    public boolean isBuildable()
    {
        return buildable;
    }

    public int getNorthWest()
    {
        return R.drawable.ic_grass1;
    }

    public int getSouthWest()
    {
        return R.drawable.ic_grass2;
    }

    public int getNorthEast()
    {
        return R.drawable.ic_grass3;
    }

    public int getSouthEast()
    {
        return R.drawable.ic_grass1;
    }

    public Bitmap getImage()
    {
        return image;
    }

    public void setImage(Bitmap image)
    {
        this.image = image;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public int getRow()
    {
        return row;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public int getCol()
    {
        return col;
    }

    public void setCol(int col)
    {
        this.col = col;
    }

    public void setBuildable(boolean buildable)
    {
        this.buildable = buildable;
    }

    /**
     * Retrieves the structure built on this map element.
     * @return The structure, or null if one is not present.
     */
    public Structure getStructure()
    {
        return structure;
    }

    public void setStructure(Structure structure)
    {
        this.structure = structure;
    }
}
