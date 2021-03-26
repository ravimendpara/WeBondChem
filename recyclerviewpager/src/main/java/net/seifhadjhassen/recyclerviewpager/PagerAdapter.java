package net.seifhadjhassen.recyclerviewpager;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.ViewHolder> {

    List<PagerModel> list;
    Context context;
    OnItemClickListener mOnItemClickListener;
    int posAttached = 0;

    public PagerAdapter(Context context, List<PagerModel> categorie) {
        this.list = categorie;
        this.context = context;
    }


    @Override
    public PagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pager, parent, false);
        return new PagerAdapter.ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(final PagerAdapter.ViewHolder holder, final int position) {

        //holder.imageView.setImageResource(list.get(position).getImg());
//        Picasso.get()
//                .load(list.get(position).getImg())
//                .into(holder.imageView);
        try {
            Glide.with(context)
                    .load(list.get(position).getImg())
                    .placeholder(R.drawable.default_slider_img)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.default_slider_img)
                    .into(holder.imageView);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

//        holder.textView.setText(list.get(position).getTitle());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        posAttached = holder.getAdapterPosition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        }
        super.onViewAttachedToWindow(holder);

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();


    }

    public int getPosAttached() {
        return posAttached;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView imageView;
        //        TextView textView;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_header_background);
//            textView=itemView.findViewById(R.id.txt_item_header);
            cardView = itemView.findViewById(R.id.card_item_header);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(getAdapterPosition());
                }
            });


        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnclickItemListener(OnItemClickListener onclickItemListener) {
        this.mOnItemClickListener = onclickItemListener;
    }
}
