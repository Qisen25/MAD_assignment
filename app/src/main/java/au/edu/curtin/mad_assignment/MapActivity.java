package au.edu.curtin.mad_assignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/*
    activity handling the game screen
 */
public class MapActivity extends AppCompatActivity {

    private Button endTurn;
    private MapFragment mapFrag;
    private SelectorFragment selFrag;
    private TextView money, gameTime, recentIncome, employment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        money = (TextView) findViewById(R.id.cash);
        gameTime = (TextView) findViewById(R.id.gameTime);
        recentIncome = (TextView) findViewById(R.id.income);
        employment = (TextView) findViewById(R.id.employment);

        FragmentManager fm = getSupportFragmentManager();
        mapFrag = (MapFragment)fm.findFragmentById(R.id.map);
        selFrag = (SelectorFragment) fm.findFragmentById(R.id.selector);

        if(selFrag == null)
        {
            selFrag = new SelectorFragment();
            fm.beginTransaction().add(R.id.selector, selFrag).commit();
        }

        if(mapFrag == null)
        {
            mapFrag = new MapFragment();
            mapFrag.setSelector(selFrag);
            fm.beginTransaction().add(R.id.map, mapFrag).commit();
        }

        updateHud();

        endTurn = (Button)findViewById(R.id.endTurn);

        //button to increment game time
        endTurn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                GameData.get().endTurn();
                updateHud();
                if(GameData.get().getMoney() < 0 && !GameData.get().isGameOver())
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "GAME OVER", Toast.LENGTH_SHORT * 2);
                    toast.show();
                    GameData.get().setGameOver(true);
                }

            }
        });

    }

    //android back button logic
    @Override
    public void onBackPressed()
    {
        //if there are no fragments on the back stack, can return to main menu
        if(getSupportFragmentManager().getBackStackEntryCount() == 0)
        {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
            super.onBackPressed();
        }
        else//otherwise go back to previous fragments
        {
            getSupportFragmentManager().popBackStack();
        }
    }

    //update player displayed stats
    public void updateHud()
    {
        money.setText("Money: " + GameData.get().getMoney());
        gameTime.setText("Time: " + GameData.get().getGameTime());
        recentIncome.setText("Income: " + GameData.get().getRecentIncome());
        employment.setText("Emploment rate: " + GameData.get().getEmployPercent() + "%");
    }
}
