package hu.techbazaar;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class categories_activity extends AppCompatActivity {
    TextView back;

    private RecyclerView category_view;
    private Category_adapter cadapter;
    private ArrayList<Category_items> category_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        category_view = findViewById(R.id.category_Recycler);
        category_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        category_items = new ArrayList<>();
        load_data2();
        cadapter = new Category_adapter(this, category_items);
        category_view.setAdapter(cadapter);

        setSupportActionBar(findViewById(R.id.toolbar));

        String category_name = getIntent().getStringExtra("CATEGORY_NAME");
        back = findViewById(R.id.later);
        back.setText(category_name + " termékek feltöltése később!");
    }

    private void load_data2(){
        String[] citems_name = getResources().getStringArray(R.array.category_items_name);
        TypedArray citems_images = getResources().obtainTypedArray(R.array.category_images);

        category_items.clear();

        for (int i = 0; i < citems_name.length;i++){
            category_items.add(new Category_items(citems_name[i],
                    citems_images.getResourceId(i,0)));
        }

        citems_images.recycle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.search) return true;
        else if (item.getItemId() == R.id.cart) return true;
        else if (item.getItemId() == R.id.settings) return true;
        else if (item.getItemId() == R.id.fav) return true;
        else if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(categories_activity.this, "Kijelentkezve!", Toast.LENGTH_SHORT).show();

            Intent Start_intent = new Intent(this, Start_activity.class);
            Start_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(Start_intent);

            finish();
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }

    public void back_to_home(View view) {
        Intent home_intent = new Intent(this, Home_activity.class);
        startActivity(home_intent);
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
}