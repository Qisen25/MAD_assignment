package au.edu.curtin.mad_assignment;

public class GameDataSchema
{
    public static class SettingsTable
    {
        public static final String NAME = "settings";
        public static class Cols
        {
            public static final String ID = "setting_id";
            public static final String MAP_HEIGHT = "height";
            public static final String MAP_WIDTH = "width";
            public static final String MONEY = "money";
        }
    }

    public static class PlayerDataTable
    {
        public static final String NAME = "playerData";
        public static class Cols
        {
            public static final String PLAYER_ID = "player_id";
            public static final String CURR_MONEY = "curr_money";
            public static final String GAME_TIME = "gameTime";
        }
    }

    public static class MapElementTable
    {
        public static final String NAME = "mapElements";
        public static class Cols
        {
            public static final String ROW_ID = "row_id";
            public static final String COL_ID = "col_id";
            public static final String STRUCT_ID = "struct_id";
            public static final String STRUCT_TYPE = "struct_type";
            public static final String OWNER_NAME = "owner_name";
        }
    }
}
