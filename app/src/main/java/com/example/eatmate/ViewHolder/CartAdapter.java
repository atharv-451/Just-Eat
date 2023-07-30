package com.example.eatmate.ViewHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.eatmate.Cart;
import com.example.eatmate.Common.Common;
import com.example.eatmate.Database.Database;
import com.example.eatmate.Interface.ItemClickListener;
import com.example.eatmate.Model.Order;
import com.example.eatmate.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

    public TextView txt_cart_name,txt_price,txtquantity;

    private ItemClickListener itemClickListener;
    public Button btnRemove,btnAdd;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_cart_name = (TextView) itemView.findViewById(R.id.cart_item_name);
        txt_price = (TextView) itemView.findViewById(R.id.cart_item_Price);
        txtquantity = (TextView) itemView.findViewById(R.id.cart_count);
        btnRemove = (Button) itemView.findViewById(R.id.btnRemove);
        btnAdd = (Button) itemView.findViewById(R.id.btnAdd);
        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select action");
        menu.add(0,0,getAdapterPosition(), Common.DELETE);
    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{

    private List<Order> listData = new ArrayList<>();
    private Cart context;

    public CartAdapter(List<Order> listData, Cart context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtquantity.setText(listData.get(position).getQuantity());
        Order order = listData.get(position);
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(holder.txtquantity.getText().toString());
                if(count>1){
                    order.setQuantity(String.valueOf(count-1));
                    new Database(context).updateCart(order);
                    int total = 0;
                    List<Order> orders = new Database(context).getCarts();
                    for(Order item:orders)
                        total += (Integer.parseInt(item.getPrice())) * (Integer.parseInt(item.getQuantity()));
                    Locale locale = new Locale("en","IN");
                    NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

                    context.txtTotalPrice.setText(fmt.format(total));
                    holder.txtquantity.setText(listData.get(position).getQuantity());
                    int price =(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
                    holder.txt_price.setText(fmt.format(price));
                }
            }
        });
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(holder.txtquantity.getText().toString());
                if(count<20){
                    order.setQuantity(String.valueOf(count+1));
                    new Database(context).updateCart(order);
                    int total = 0;
                    List<Order> orders = new Database(context).getCarts();
                    for(Order item:orders)
                        total += (Integer.parseInt(item.getPrice())) * (Integer.parseInt(item.getQuantity()));
                    Locale locale = new Locale("en","IN");
                    NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                    context.txtTotalPrice.setText(fmt.format(total));
                    holder.txtquantity.setText(listData.get(position).getQuantity());
                    int price =(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
                    holder.txt_price.setText(fmt.format(price));
                }
            }
        });
        Locale locale = new Locale("en","IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price =(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        holder.txt_price.setText(fmt.format(price));
        holder.txt_cart_name.setText(listData.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
