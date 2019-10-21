package au.edu.curtin.mad_assignment.Model;
/**
 * Represents a possible structure to be placed on the map. A structure simply contains a drawable
 * int reference, string type to be shown in the selector and cost to build.
 * REFERENCE: MAD prac 3, slight modified
 */
public abstract class Structure
{
    private int imageId;

    public Structure(int drawableId)
    {
        this.imageId = drawableId;
    }

    public int getImageId()
    {
        return imageId;
    }

    public abstract String getType();
    public abstract int getCost();
}
