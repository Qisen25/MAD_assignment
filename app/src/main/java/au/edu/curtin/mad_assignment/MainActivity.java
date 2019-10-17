package au.edu.curtin.mad_assignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private Button startGame, settings;
    private GameData gd;

    private static final int REQUEST_CODE_GAME = 0;
    private static final int REQUEST_CODE_SETTINGS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        gd = GameData.get();
        setupButtons();

        startGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                gd.load(getApplicationContext(), true);//get database changes everytime, true coz at this point game has begun
                gd.initGame();
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivityForResult(intent, REQUEST_CODE_GAME);
            }
        });

        settings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                gd.load(getApplicationContext(), false);// set false coz we are not in game
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SETTINGS);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(resultCode == RESULT_OK && (requestCode == REQUEST_CODE_GAME || requestCode == REQUEST_CODE_SETTINGS))
        {
            gd.closeDB();
        }
    }

    private void setupButtons()
    {
        this.startGame = (Button)findViewById(R.id.startGame);
        this.settings = (Button)findViewById(R.id.settingButton);
    }
}
