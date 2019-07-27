package com.shashank.user.veggiefest.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shashank.user.veggiefest.R;

import java.util.ArrayList;

/**
 * Created by User on 2/19/2019.
 */
public class MyAdapterViewPager extends PagerAdapter
{
    ArrayList<Integer> images;
    LayoutInflater inflater;

    public MyAdapterViewPager(Context context, ArrayList<Integer> images)
    {
        this.images=images;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        // super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public int getCount()
    {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {

        View v=inflater.inflate(R.layout.viewpager_custom,container,false);
        ImageView imageView= (ImageView) v.findViewById(R.id.imageview_viewpager);
        imageView.setImageResource(images.get(position));
        container.addView(v,0);

        return v;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view.equals(object);
    }
}
