package au.edu.curtin.mad_assignment;

class Road extends Structure
{

    public Road(int imageId)
    {
        super(imageId);
    }

    @Override
    public String getLabel()
    {
        return "Road";
    }

    @Override
    public int getCost()
    {
        return GameData.get().getSettings().getRoadBuildingCost();
    }
}
