package com.realme_demo.realmeapp.activities.rm_stylify;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by idanhahn on 8/18/2016.
 */
public class RmStylifyActivity extends AppCompatActivity{


    private List<StylifyItem> mStylifyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stylify);

        mStylifyList = new ArrayList<>();
        mStylifyList.add(new StylifyItem("model_d1","Model1", "Designer: Designer1", "stars5", "0", true));
        mStylifyList.add(new StylifyItem("model_mickey","Model1", "Designer: Designer1", "stars5", "4.99$", false));
        mStylifyList.add(new StylifyItem("model_d2","Model2", "Designer: Designer1", "stars5", "9.95$", false));
        mStylifyList.add(new StylifyItem("model_d3","Model3", "Designer: Designer1", "stars5", "4.95$", false));
        mStylifyList.add(new StylifyItem("model_d4","Model4", "Designer: Designer1", "stars5", "1.99$", false));
        mStylifyList.add(new StylifyItem("model1","Model5", "Designer: Designer2", "stars5", "19.90$", false));
        mStylifyList.add(new StylifyItem("model2","Model6", "Designer: Designer2", "stars5", "19.90$", false));
        mStylifyList.add(new StylifyItem("model3","Model7", "Designer: Designer2", "stars5", "19.90$", false));
        mStylifyList.add(new StylifyItem("model4","Model8", "Designer: Designer2", "stars5", "19.90$", false));

        StylifyAdapter adapter = new StylifyAdapter(this, (ArrayList<StylifyItem>) this.mStylifyList);
        ListView stylifyList = (ListView) findViewById(R.id.stylify_list);
        stylifyList.setAdapter(adapter);

    }

    private class StylifyItem{

        String model;
        String title;
        String designer;
        String rating;
        String price;
        boolean acquired;

        public StylifyItem(String model, String title, String designer, String rating, String price, boolean acquired) {
            this.model = model;
            this.title = title;
            this.designer = designer;
            this.rating = rating;
            this.price = price;
            this.acquired = acquired;
        }
    }

    private class StylifyAdapter extends ArrayAdapter<StylifyItem> {

        private ArrayList<StylifyItem> data;

        public StylifyAdapter(Context context, ArrayList<StylifyItem> data) {
            super(context, 0, data);
            this.data = data;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {

            View view;

            if (convertView == null){
                view = LayoutInflater.from(getContext()).inflate(R.layout.stylify_item, parent, false);
            } else {
                view = convertView;
            }

            return this.bindData(view, i);

        }

        public View bindData(View view, int i){

            if(this.data.get(i) == null){
                return view;
            }

            StylifyItem item = this.data.get(i);

            ImageView model_img = (ImageView) view.findViewById(R.id.stylify_item_img);
            model_img.setImageResource(view.getContext().getResources().getIdentifier("drawable/" + item.model,null,view.getContext().getPackageName()));

            TextView title = (TextView) view.findViewById(R.id.stylify_item_title);
            title.setText(item.title);

            TextView designer = (TextView) view.findViewById(R.id.stylify_item_designer);
            designer.setText(item.designer);

            ImageView img = (ImageView) view.findViewById(R.id.stylify_item_rating);
            img.setImageResource(view.getContext().getResources().getIdentifier("drawable/" + item.rating,null,view.getContext().getPackageName()));

            if (!item.acquired){
                TextView price = (TextView) view.findViewById(R.id.stylify_item_price);
                price.setText(item.price);
            }

            return view;

        }


    }

}
