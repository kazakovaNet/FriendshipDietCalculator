package ru.kazakova_net.friendshipdietcalculator.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ru.kazakova_net.friendshipdietcalculator.databinding.ProductsListItemBinding;
import ru.kazakova_net.friendshipdietcalculator.model.Product;

/**
 * Created by nkazakova on 13/05/2019.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    
    private List<Product> products;
    
    public ProductsAdapter(List<Product> products) {
        this.products = products;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ProductsListItemBinding binding =
                ProductsListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        
        return new ViewHolder(binding);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(products.get(position));
    }
    
    @Override
    public int getItemCount() {
        return products.size();
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
        
        private ProductsListItemBinding binding;
        
        ViewHolder(ProductsListItemBinding binding) {
            super(binding.getRoot());
            
            this.binding = binding;
        }
        
        public void bind(Product product) {
            binding.setProduct(product);
        }
    }
}
