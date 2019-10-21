package au.edu.curtin.mad_assignment.Model;

import au.edu.curtin.mad_assignment.Database.GameData;

/*
    Structure type commercial model
 */
public class Commercial extends Structure
{
    public Commercial(int imageId)
    {
        super(imageId);
    }

    @Override
    public String getType()
    {
        return "Commercial";
    }

    @Override
    public int getCost()
    {
        return GameData.get().getSettings().getCommBuildingCost();
    }
}
