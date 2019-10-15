package au.edu.curtin.mad_assignment;


public class Settings
{
    private int mapWidth;
    private int mapHeight;
    private int initialMoney;
    private int familySize;
    private int shopSize;
    private int salary;
    private double taxRate;
    private int serviceCost;
    private int houseBuildingCost;
    private int commBuildingCost;
    private int roadBuildingCost;

    public Settings()
    {
        mapWidth = 50;
        mapHeight = 10;
        initialMoney = 1000;
        familySize = 4;
        shopSize = 6;
        salary = 10;
        taxRate = 0.3;
        serviceCost = 2;
        houseBuildingCost = 100;
        commBuildingCost = 500;
        roadBuildingCost = 20;
    }

    public Settings(int width, int height, int money)
    {
        this.mapWidth = width;
        this.mapHeight = height;
        this.initialMoney = money;
        this.familySize = 4;
        this.shopSize = 6;
        this.salary = 10;
        this.taxRate = 0.3;
        this.serviceCost = 2;
        this.houseBuildingCost = 100;
        this.commBuildingCost = 500;
        this.roadBuildingCost = 20;
    }

    public int getMapWidth()
    {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth)
    {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight()
    {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight)
    {
        this.mapHeight = mapHeight;
    }

    public int getInitialMoney()
    {
        return initialMoney;
    }

    public void setInitialMoney(int initialMoney)
    {
        this.initialMoney = initialMoney;
    }

    public int getFamilySize()
    {
        return familySize;
    }

    public void setFamilySize(int familySize)
    {
        this.familySize = familySize;
    }

    public int getShopSize()
    {
        return shopSize;
    }

    public void setShopSize(int shopSize)
    {
        this.shopSize = shopSize;
    }

    public int getSalary()
    {
        return salary;
    }

    public void setSalary(int salary)
    {
        this.salary = salary;
    }

    public double getTaxRate()
    {
        return taxRate;
    }

    public void setTaxRate(double taxRate)
    {
        this.taxRate = taxRate;
    }

    public int getServiceCost()
    {
        return serviceCost;
    }

    public void setServiceCost(int serviceCost)
    {
        this.serviceCost = serviceCost;
    }

    public int getHouseBuildingCost()
    {
        return houseBuildingCost;
    }

    public void setHouseBuildingCost(int houseBuildingCost)
    {
        this.houseBuildingCost = houseBuildingCost;
    }

    public int getCommBuildingCost()
    {
        return commBuildingCost;
    }

    public void setCommBuildingCost(int commBuildingCost)
    {
        this.commBuildingCost = commBuildingCost;
    }

    public int getRoadBuildingCost()
    {
        return roadBuildingCost;
    }

    public void setRoadBuildingCost(int roadBuildingCost)
    {
        this.roadBuildingCost = roadBuildingCost;
    }
}
