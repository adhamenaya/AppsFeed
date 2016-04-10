package com.adhamenaya.grability.appsfeed.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.adhamenaya.grability.appsfeed.R;
import com.adhamenaya.grability.appsfeed.data.model.Application;
import com.adhamenaya.grability.appsfeed.util.OnItemClickListener;


public class ApplicationsAdapter extends RecyclerView.Adapter<ApplicationsAdapter.ApplicationViewHolder> {

    private List<Application> mApplications;

    @Inject
    Picasso picasso;

    public OnItemClickListener onItemClickListener;

    @Inject
    public ApplicationsAdapter() {
        mApplications = new ArrayList<>();
    }

    public void setApplications(Context context,List<Application> Applications) {

        this.onItemClickListener = (OnItemClickListener) context;
        mApplications = Applications;
    }

    @Override
    public ApplicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_application, parent, false);
        return new ApplicationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ApplicationViewHolder holder, int position) {
        Application application = mApplications.get(position);
        holder.bind(application);
    }

    @Override
    public int getItemCount() {
        return mApplications.size();
    }

    class ApplicationViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image_view_application)
        ImageView imageViewApplication;

        @Bind(R.id.text_view_name) TextView textViewName;
        @Bind(R.id.text_view_price) TextView textViewPrice;

        public ApplicationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Application application) {

            textViewName.setText(application.name.label);
            if(application.price.attributes.amount.equals("0"))
                textViewPrice.setText("Free");
            else
                textViewPrice.setText(application.price.attributes.amount + " " + application.price.attributes.currency);
            if(picasso !=null
                    && application.images.size()>0
                    && !application.images.get(0).label.isEmpty()){

                final String imagePath = application.images.get(0).label;

                picasso.load(imagePath)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(imageViewApplication, new Callback() {
                            @Override
                            public void onSuccess() {
                            }
                            @Override
                            public void onError() {
                                // Try to get image online
                                picasso.load(imagePath).into(imageViewApplication);
                            }
                        });
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(application, getAdapterPosition(), itemView);
                }
            });
        }
    }
}
