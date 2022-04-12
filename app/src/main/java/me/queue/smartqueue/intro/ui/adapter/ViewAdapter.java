package me.queue.smartqueue.intro.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;

import me.queue.smartqueue.R;

public class ViewAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images ={R.raw.hello,R.raw.login,R.raw.queue,R.raw.free};
    private String[] text ={"Welcome to Smart Queue","First Login","Test our app","Most important of all, Its Free!!"};


    public ViewAdapter(Context context){
        this.context = context;
    }



    @Override
    public int getCount() {
        return images.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.animationview ,null);

        LottieAnimationView imageView = view.findViewById(R.id.animation_view);
        TextView textView =view.findViewById(R.id.tv_anim);

        imageView.setAnimation(images[position]);
        textView.setText(text[position]);

        ViewPager viewPager= (ViewPager) container;
        viewPager.addView(view,0);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}