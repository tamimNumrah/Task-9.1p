package com.tamim.task71p;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    VerticalAdapter adapter;
    AdvertDatabaseHelper db;
    List<Advert> advertList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recyclerView);
        db = new AdvertDatabaseHelper(this);
        setupList();
    }

    private void setupList() {
        advertList = db.getAllAdverts();
        adapter = new VerticalAdapter(advertList, new OnItemClickListener() {
            @Override public void onItemClick(Advert advert) {
                System.out.println("Advert "+advert.getName()+" clicked!");
                Intent intent = new Intent(ListActivity.this, ItemDetailsActivity.class);
                intent.putExtra("advert", advert);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupList();
    }

    public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.ViewHolder> {

        private List<Advert> advertList;
        private final OnItemClickListener listener;

        public VerticalAdapter(List<Advert> advertList, OnItemClickListener listener) {
            this.advertList = advertList;
            this.listener = listener;
            System.out.println("initialized Adapter with " + advertList.size());
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView titleTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                titleTextView = itemView.findViewById(R.id.titleTextView);
            }

            public void bind(Advert advert, final OnItemClickListener listener) {
                titleTextView.setText(advert.getType().getPostTypeValue() + " " + advert.getName());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        listener.onItemClick(advert);
                    }
                });
            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item_details, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Advert advert = advertList.get(position);
            holder.bind(advert, listener);
        }

        @Override
        public int getItemCount() {
            if(this.advertList == null) {
                System.out.println("getItemCount advertList is NULL");
                return 0;
            }
            return this.advertList.size();
        }
    }
}