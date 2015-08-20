package nanodegree.prips.com.myappportfolio;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button spotifyButton = (Button) findViewById(R.id.activity_portfolio_spotify_button);
        spotifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(v);
            }
        });

        Button scoresButton = (Button) findViewById(R.id.activity_portfolio_scores_button);
        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(v);
            }
        });

        Button libraryButton = (Button) findViewById(R.id.activity_portfolio_library_button);
        libraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(v);
            }
        });

        Button biggerButton = (Button) findViewById(R.id.activity_portfolio_bigger_button);
        biggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(v);
            }
        });

        Button xyzButton = (Button) findViewById(R.id.activity_portfolio_XYZ_button);
        xyzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(v);
            }
        });
    }

    private void onButtonClick(View v) {
        switch (v.getId()) {
            case R.id.activity_portfolio_spotify_button:
                Toast.makeText(MainActivity.this, "COMING SOON!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_portfolio_scores_button:
                Toast.makeText(MainActivity.this, "COMING SOON!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_portfolio_library_button:
                Toast.makeText(MainActivity.this, "COMING SOON!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_portfolio_bigger_button:
                Toast.makeText(MainActivity.this, "COMING SOON!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_portfolio_XYZ_button:
                Toast.makeText(MainActivity.this, "COMING SOON!!", Toast.LENGTH_SHORT).show();
                break;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
