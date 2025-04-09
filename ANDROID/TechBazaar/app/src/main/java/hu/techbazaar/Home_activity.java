package hu.techbazaar;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Home_activity extends AppCompatActivity {
    RecyclerView home_view;
    RecyclerView category_view;

    private ArrayList<Home_items> home_items;
    private ArrayList<Categories_items> category_items;

    private Home_adapter iadapter;
    Categories_adapter cadapter;

    TextView highlighted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setSupportActionBar(findViewById(R.id.toolbar));

        home_view = findViewById(R.id.home_content);
        category_view = findViewById(R.id.category_Recycler);

        home_view.setLayoutManager(new GridLayoutManager(this, 2));
        category_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        home_items = new ArrayList<>();
        category_items = new ArrayList<>();
        load_data();
        load_data2();

        iadapter = new Home_adapter(this, home_items);
        cadapter = new Categories_adapter(this, category_items);
        home_view.setAdapter(iadapter);
        category_view.setAdapter(cadapter);

        highlighted = findViewById(R.id.highlighted);

        Animation slideIn = AnimationUtils.loadAnimation(this, R.anim.slide);
        highlighted.startAnimation(slideIn);


    }

    private void load_data() {
        String[] items_name = getResources().getStringArray(R.array.items_names);
        String[] items_description = getResources().getStringArray(R.array.items_description);
        String[] items_price = getResources().getStringArray(R.array.items_prices);
        TypedArray items_images = getResources().obtainTypedArray(R.array.items_images);
        TypedArray items_rated = getResources().obtainTypedArray(R.array.items_rates);

        home_items.clear();

        for (int i = 0; i < items_name.length;i++){
            home_items.add(new Home_items(items_name[i], items_description[i],
                    items_price[i], items_images.getResourceId(i,0),
                    items_rated.getFloat(i, 0)));
        }

        items_images.recycle();
    }

    private void load_data2(){
        String[] citems_name = getResources().getStringArray(R.array.category_items_name);
        TypedArray citems_images = getResources().obtainTypedArray(R.array.category_images);

        category_items.clear();

        for (int i = 0; i < citems_name.length;i++){
            category_items.add(new Categories_items(citems_name[i], citems_images.getResourceId(i,0)));
        }

        citems_images.recycle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem search_item = menu.findItem(R.id.search);
        SearchView search_view = (SearchView) search_item.getActionView();

        assert search_view != null;
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                iadapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cart) {
            Intent cart_intent = new Intent(this, Cart_activity.class);
            startActivity(cart_intent);
            return true;
        }
        else if (item.getItemId() == R.id.settings) {
            Intent settings_intent = new Intent(this, Settings_activity.class);
            startActivity(settings_intent);
            return true;
        }
        else if (item.getItemId() == R.id.fav){
            Intent favorite_intent = new Intent(this, Favorite_activity.class);
            startActivity(favorite_intent);
            return true;
        }
        else if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(Home_activity.this, "Kijelentkezve!", Toast.LENGTH_SHORT).show();

            Intent Start_intent = new Intent(this, Start_activity.class);
            Start_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(Start_intent);

            finish();
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent protected_intent = new Intent(this, Start_activity.class);
            protected_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(protected_intent);
            finish();
        }
    }

    public void clickHome(View view) {
        Intent home = new Intent(this, Home_activity.class);
        startActivity(home);
    }
}