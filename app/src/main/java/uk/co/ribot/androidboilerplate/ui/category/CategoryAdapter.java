package uk.co.ribot.androidboilerplate.ui.category;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Application;
import uk.co.ribot.androidboilerplate.data.model.Category;
import uk.co.ribot.androidboilerplate.util.CommonUtils;
import uk.co.ribot.androidboilerplate.util.OnItemClickListener;

/**
 * Created by adhamenaya on 4/8/2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> mCategories;
    private OnItemClickListener onItemClickListener;
    private Context context;

    @Inject
    public CategoryAdapter() {
        mCategories = new ArrayList<>();
    }

    public void setOnItemClickListener(Context context) {
        this.onItemClickListener = (OnItemClickListener) context;
        this.context = context;
    }

    public void setCategories(List<Category> categories) {
        mCategories = categories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = mCategories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_view_name)
        TextView textViewName;

        @Bind(R.id.image_view_icon)
        ImageView imageViewIcon;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Category category) {

            String iconName = category.attributes.label.toLowerCase().split(" ")[0];

            textViewName.setText(category.attributes.label);

            int id = CommonUtils.getIconId(context, iconName);
            if (id > 0)
                imageViewIcon.setImageDrawable(context.getResources().getDrawable(id));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(category, getAdapterPosition());
                }
            });

            itemView.setBackgroundColor(Color.parseColor(CommonUtils.getBackgroundColor(context, getAdapterPosition())));
        }
    }
}