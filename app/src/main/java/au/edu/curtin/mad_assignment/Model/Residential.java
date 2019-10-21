package au.edu.curtin.mad_assignment.Model;

import au.edu.curtin.mad_assignment.Database.GameData;

/*
    structure type Residential model
 */
public class Residential extends Structure
{

    public Residential(int imageId)
    {
        super(imageId);
    }

    @Override
    public String getType()
    {
        return "Residential";
    }

    @Override
    public int getCost()
    {
        return GameData.get().getSettings().getHouseBuildingCost();
    }
}
