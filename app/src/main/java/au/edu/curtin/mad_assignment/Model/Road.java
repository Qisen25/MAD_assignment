package au.edu.curtin.mad_assignment.Model;

import au.edu.curtin.mad_assignment.Database.GameData;

/*
    structure type road model
 */
public class Road extends Structure
{

    public Road(int imageId)
    {
        super(imageId);
    }

    @Override
    public String getType()
    {
        return "Road";
    }

    @Override
    public int getCost()
    {
        return GameData.get().getSettings().getRoadBuildingCost();
    }
}
