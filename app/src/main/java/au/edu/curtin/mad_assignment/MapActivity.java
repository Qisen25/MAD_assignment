package au.edu.curtin.mad_assignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MapActivity extends AppCompatActivity {

    private Button endTurn;
    private MapFragment mapFrag;
    private SelectorFragment selFrag;
    private TextView money, gameTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        money = (TextView) findViewById(R.id.cash);
        gameTime = (TextView) findViewById(R.id.gameTime);

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

        endTurn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                GameData.get().endTurn();
                updateHud();
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
        super.onBackPressed();
    }

    public void updateHud()
    {
        money.setText("Money: " + GameData.get().getMoney());
        gameTime.setText("Time: " + GameData.get().getGameTime());
//        gameTime.setText("Time: " + GameData.get().getSize());
    }
}
