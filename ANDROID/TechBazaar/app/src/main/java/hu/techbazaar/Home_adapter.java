package hu.techbazaar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.List;

public class Home_adapter extends RecyclerView.Adapter<Home_adapter.ProductViewHolder> {
    private Context context;
    private List<items> productList;

    public Home_adapter(Context context, List<items> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Megjelenés kölcsönzése a soroknak
        View view = LayoutInflater.from(context).inflate(R.layout.home_items, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Ertékek hozzárendelése az Recycle view fájlban létrehozott nézetekhez
        items product = productList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice());
        holder.description.setText(product.getDesc());
        holder.rate.setRating(product.getRate());
        Glide.with(context).load(product.getImgsrc()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        // A megjelenített termékek számának megadása
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        // Nézetek beállítása
        TextView name, price, description;
        ImageView imageView;
        RatingBar rate;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);
            description = itemView.findViewById(R.id.item_description);
            imageView = itemView.findViewById(R.id.item_img);
            rate = itemView.findViewById(R.id.item_rate);
        }
    }
}