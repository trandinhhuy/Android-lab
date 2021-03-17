package com.example.everhope;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class IntroAdapter extends PagerAdapter {
    Context context;
    List<IntroItem> mListScreen;

    public IntroAdapter(Context context, List<IntroItem> introItems){
        this.context = context;
        this.mListScreen = introItems;
    }

    public Object instantiateItem(ViewGroup container, int position){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.layout_intro, null);

        ImageView imgSlide = layoutScreen.findViewById(R.id.introImage);
        TextView title = layoutScreen.findViewById(R.id.introTitle);
        TextView description = layoutScreen.findViewById(R.id.introDescription);

        title.setText(mListScreen.get(position).getTitle());
        imgSlide.setImageResource(mListScreen.get(position).getImageID());
        description.setText(mListScreen.get(position).getGuileLine());

        container.addView(layoutScreen);
        return layoutScreen;
    }
    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
