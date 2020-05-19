package com.imdb.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.imdb.R;
import com.imdb.databinding.RowMovieListBinding;
import com.imdb.ui.OnPosterClick;
import com.imdb.utility.AppConstants;

import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private final Context context;

    List<HomeResponse.ResultsBean> mData;

    OnPosterClick mlistener;

    public HomeAdapter(Context context, List<HomeResponse.ResultsBean> boardData) {
        this.context = context;
        mData = boardData;
    }

    public void setListener(OnPosterClick listener) {
        mlistener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowMovieListBinding binder = DataBindingUtil.bind(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movie_list, parent, false));
        return new ViewHolder(binder);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.rowMovieListBinding.tvTitle.setText(mData.get(position).getTitle());
        holder.rowMovieListBinding.tvRelease.setText(mData.get(position).getRelease_date());
        holder.rowMovieListBinding.tvDesc.setText(mData.get(position).getOverview());
        holder.rowMovieListBinding.tvRating.setText(String.valueOf(mData.get(position).getVote_average()));
        Glide.with(context)
                .load(AppConstants.IMAGEURL + mData.get(position).getPoster_path())
                .into(holder.rowMovieListBinding.ivPoster);
        holder.rowMovieListBinding.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mlistener != null) mlistener.onPosterItemClick(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addAll(List<HomeResponse.ResultsBean> boardData) {
        if (boardData != null && !boardData.isEmpty()) {
            int prevPos = mData.size();
            mData.addAll(boardData);
            notifyItemRangeInserted(prevPos, boardData.size());
        }
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        RowMovieListBinding rowMovieListBinding;

        ViewHolder(RowMovieListBinding binder) {
            super(binder.getRoot());
            this.rowMovieListBinding = binder;
        }

    }

}

