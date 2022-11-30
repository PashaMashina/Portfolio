package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.activity.UpdateActivity;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder>{

    private Context context;
    Activity activity;
    private ArrayList id, title, costNow, costBuy, amount, sum, profitFromBuy, typeAsset;

    Animation translateAnim;

    public CustomerAdapter(Activity activity,
                           Context context,
                           ArrayList id,
                           ArrayList title,
                           ArrayList costNow,
                           ArrayList costBuy,
                           ArrayList amount,
                           ArrayList sum,
                           ArrayList profitFromBuy,
                           ArrayList typeAsset){

        this.activity = activity;
        this.context = context;
        this.id = id;
        this.title = title;
        this.costNow = costNow;
        this.costBuy = costBuy;
        this.amount = amount;
        this.sum = sum;
        this.profitFromBuy = profitFromBuy;
        this.typeAsset = typeAsset;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.tittleTxt.setText(String.valueOf(title.get(position)));
        holder.typeAssetTxt.setText(String.valueOf(typeAsset.get(position)));
        holder.costNowTxt.setText(String.valueOf(costNow.get(position)));
        holder.amountTxt.setText(String.valueOf(amount.get(position)));
        holder.sumTxt.setText(String.valueOf(sum.get(position)));
        holder.profitFromBuyTxt.setText(String.valueOf(profitFromBuy.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("title", String.valueOf(title.get(position)));
                intent.putExtra("typeAsset", String.valueOf(typeAsset.get(position)));
                intent.putExtra("costBuy", String.valueOf(costBuy.get(position)));
                intent.putExtra("costNow", String.valueOf(costNow.get(position)));
                intent.putExtra("amount", String.valueOf(amount.get(position)));
                intent.putExtra("sum", String.valueOf(sum.get(position)));
                intent.putExtra("profitFromBuy", String.valueOf(profitFromBuy.get(position)));
                activity.startActivityForResult(intent,1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tittleTxt, typeAssetTxt, costNowTxt, amountTxt, sumTxt, profitFromBuyTxt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tittleTxt = itemView.findViewById(R.id.tittleTxt);
            typeAssetTxt = itemView.findViewById(R.id.typeAssetTxt);
            costNowTxt = itemView.findViewById(R.id.costNowTxt);
            amountTxt = itemView.findViewById(R.id.amountTxt);
            sumTxt = itemView.findViewById(R.id.sumTxt);
            profitFromBuyTxt = itemView.findViewById(R.id.profitFromBuyTxt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            translateAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translateAnim);
        }
    }
}
