package com.behzad.behzadimagescreen;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by behzad on 19/08/17.
 */

public class ImageScreenBuilder
{
    //static fields:
    private static ImageScreenBuilder _instance;
    public static boolean screenBack()
    {
        if(_instance == null)
            return false;
        if(_instance.isActive())
        {
            _instance.hide();
            return true;
        }
        return false;
    }

    //settings:
    private String title;
    private String[] images;
    private boolean hasMenuButton;
    private int menuIcon = -1;
    private View.OnClickListener menuOnclick;

    //members:
    private AppCompatActivity context;
    private View view;


    public ImageScreenBuilder(AppCompatActivity context){this.context=context;_instance = this;}

    public ImageScreenBuilder setTitle(String title){this.title = title;return this;}
    public ImageScreenBuilder setImages(String[] images){this.images=images;return  this;}
    public ImageScreenBuilder setHasMenuButton(boolean b){this.hasMenuButton = b;return this;}
    public ImageScreenBuilder setMenuIcon(int idRes){this.menuIcon = idRes;return  this;}
    public ImageScreenBuilder setOnMenuClickListener(View.OnClickListener onMenuClickListener){this.menuOnclick = onMenuClickListener;return this;}


    public void show()
    {
        final ViewGroup decor  =  (ViewGroup) context.getWindow().getDecorView();
        view = LayoutInflater.from(context).inflate(R.layout.page_image_screen,null,false);
        decor.addView(view);
        Picasso.with(context).load(images[0])
                .into((ImageView) view.findViewById(R.id.img));
        ((PhotoView)view.findViewById(R.id.img)).setAllowParentInterceptOnEdge(true);
        View.OnClickListener  hideClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        };
        //((PhotoView)view.findViewById(R.id.img)).setClipToOutline(true);
        ((PhotoView)view.findViewById(R.id.img)).setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                hide();
            }
        });
        //((PhotoView)view.findViewById(R.id.img)).setAllowParentInterceptOnEdge(true);
        view.findViewById(R.id.black_screen).setOnClickListener(hideClick);
        if(title != null)
        {
            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            toolbar.setTitle(title);
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setNavigationIcon(ContextCompat.getDrawable(context,R.drawable.ic_action_back));
            toolbar.setNavigationOnClickListener(hideClick);
            ImageView menuBtn = (ImageView) toolbar.findViewById(R.id.menu_btn);
            menuBtn.setVisibility(View.GONE);
            if(hasMenuButton)
            {
                menuBtn.setVisibility(View.VISIBLE);
                if(menuIcon != -1)
                    menuBtn.setImageResource(menuIcon);
                if(menuOnclick != null)
                    menuBtn.setOnClickListener(menuOnclick);
            }
        }
    }
    public void hide()
    {
        final ViewGroup decor  =  (ViewGroup) context.getWindow().getDecorView();
        decor.removeView(view);
        view = null;
    }
    public boolean isActive()
    {
        return view != null;
    }



}
