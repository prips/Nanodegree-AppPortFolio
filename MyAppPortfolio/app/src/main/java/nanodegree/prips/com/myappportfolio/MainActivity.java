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
    }

    public void onButtonClick(View v) {
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
}
