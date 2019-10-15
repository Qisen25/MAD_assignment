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

    private static final int REQUEST_CODE_MAP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        gd = GameData.get();
        gd.load(getApplicationContext());
//        gd.storeDefaultSettings();
        setupButtons();

        startGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupButtons()
    {
        this.startGame = (Button)findViewById(R.id.startGame);
        this.settings = (Button)findViewById(R.id.settingButton);
    }
}
