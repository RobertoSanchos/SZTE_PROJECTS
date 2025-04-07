package hu.techbazaar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Home_adapter extends RecyclerView.Adapter<Home_adapter.ProductViewHolder> implements Filterable {
    private Context context;
    private List<Home_items> productList;
    private List<Home_items> productListFiltered;

    public Home_adapter(Context context, List<Home_items> productList) {
        this.context = context;
        this.productList = productList;
        this.productListFiltered = productList;
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
        Home_items product = productList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice());
        holder.description.setText(product.getDesc());
        holder.rate.setRating(product.getRate());
        Glide.with(context).load(product.getImgsrc()).into(holder.imageView);

//       if (product.isFavorite()) {
//            holder.favoriteButton.setImageResource(R.drawable.favorite_on);
//       } else {
//            holder.favoriteButton.setImageResource(R.drawable.favorite_off);
//       }
//
//        // Kedvenc gomb kattintás kezelése
//        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // A helyes pozíció kezelése a klikkelés során
//                items clickedProduct = productList.get(holder.getAdapterPosition());
//                clickedProduct.setFavorite(!clickedProduct.isFavorite());  // Állapot váltás
//                notifyItemChanged(holder.getAdapterPosition());  // Frissítés
//            }
//        });
    }

    @Override
    public int getItemCount() {
        // A megjelenített termékek számának megadása
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        return productFilter;
    }

    private Filter productFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Home_items> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(productListFiltered);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Home_items item : productListFiltered) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            productList = (List<Home_items>)filterResults.values;
            notifyDataSetChanged();
        }
    };

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        // Nézetek beállítása
        TextView name, price, description;
        ImageView imageView;
        ImageButton favoriteButton;
        RatingBar rate;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);
            description = itemView.findViewById(R.id.item_description);
            imageView = itemView.findViewById(R.id.item_img);
            rate = itemView.findViewById(R.id.item_rate);
            favoriteButton = itemView.findViewById(R.id.favorite);
        }
    }
}