package cat.itb.imagenapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import cat.itb.imagenapp.R;
import cat.itb.imagenapp.fragments.Fragment1;
import cat.itb.imagenapp.fragments.MapsFragment;

public class MainActivity extends AppCompatActivity {

    Fragment cuurentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (savedInstanceState==null) {
            cuurentFragment = new Fragment1();
            changeFragment(cuurentFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_fragment1:
                cuurentFragment = new Fragment1();
                break;
            case R.id.menu_mapa:
                cuurentFragment = new MapsFragment();
                break;
        }

        changeFragment(cuurentFragment);
        return super.onOptionsItemSelected(item);
    }

    private void changeFragment(Fragment cuurentFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,cuurentFragment).commit();
    }
}