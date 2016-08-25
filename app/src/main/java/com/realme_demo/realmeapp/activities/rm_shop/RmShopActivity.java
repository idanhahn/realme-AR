package com.realme_demo.realmeapp.activities.rm_shop;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.realme_demo.realmeapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by idanhahn on 8/17/2016.
 */
public class RmShopActivity extends AppCompatActivity {

    private List<ShopItem> mShopList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        // setting list items
        mShopList = new ArrayList<>();
        mShopList.add(new ShopItem("Shirt1","Type1","Price1","stars5","shirt1"));
        mShopList.add(new ShopItem("Shirt2","Type2","Price2","stars4","shirt2"));
        mShopList.add(new ShopItem("Shirt3","Type3","Price3","stars4","shirt3"));
        mShopList.add(new ShopItem("Shirt4","Type4","Price4","stars5","shirt4"));

        ShopItemAdapter adapter = new ShopItemAdapter(this, (ArrayList<ShopItem>) this.mShopList);
        ListView shopList = (ListView) findViewById(R.id.shop_list);
        shopList.setAdapter(adapter);

    }

    private class ShopItemAdapter extends ArrayAdapter<ShopItem> {


        private ArrayList<ShopItem> data;

        public ShopItemAdapter(Context context, ArrayList<ShopItem> data) {
            super(context, 0, data);
            this.data = data;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {

            View view;

            if (convertView == null){
                view = LayoutInflater.from(getContext()).inflate(R.layout.shop_item, parent, false);
            } else {
                view = convertView;
            }

            return this.bindData(view, i);
        }


        public View bindData(View view, int i){

            if(this.data.get(i) == null){
                return view;
            }

            ShopItem item = this.data.get(i);

            TextView title = (TextView) view.findViewById(R.id.shop_item_title);
            title.setText(item.title);

            TextView type = (TextView) view.findViewById(R.id.shop_item_type);
            type.setText(item.type);

            ImageView rating = (ImageView) view.findViewById(R.id.shop_item_rating);
            rating.setImageResource(view.getContext().getResources().getIdentifier("drawable/"+ item.rating,null,view.getContext().getPackageName()));

            TextView price = (TextView) view.findViewById(R.id.shop_item_price);
            price.setText(item.price);

            ImageView img = (ImageView) view.findViewById(R.id.shop_item_img);
            img.setImageResource(view.getContext().getResources().getIdentifier("drawable/" + item.img,null,view.getContext().getPackageName()));

            return view;

        }
    }

}
