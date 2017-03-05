package com.minhuyenwallpaper.Activity;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.minhuyenwallpaper.Fragment.HomeFragment;
import com.minhuyenwallpaper.Model.Wallpaper;
import com.minhuyenwallpaper.Others.DepthPageTransformer;
import com.minhuyenwallpaper.R;

import java.io.IOException;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    int pos;

    Toolbar toolbar;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ArrayList<Wallpaper> wallpapers;
    private static Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wallpapers = HomeFragment.wallpapers;

        pos = getIntent().getIntExtra("pos", 0);

        setTitle(wallpapers.get(pos).wallpaperTitle);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), wallpapers);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(pos);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pos = position;
                setTitle(wallpapers.get(position).wallpaperTitle);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuSetWallpaper) {
            setImageAsWallpaper();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    * set image as wallpaper from image URL
    * */
    public void setImageAsWallpaper() {
        final ProgressDialog pg = new ProgressDialog(DetailActivity.this);
        pg.setMessage("Processing...<3<3");
        pg.setCanceledOnTouchOutside(false);
        pg.show();

        Glide.with(this)
                .load(wallpapers.get(pos).wallpaperURL)
                .asBitmap()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        bitmap = resource;
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

                        try {
                            wallpaperManager.setBitmap(bitmap);
                            Toast.makeText(getApplicationContext(), "Set Wallpaper Succeed!", Toast.LENGTH_LONG).show();
                        } catch (IOException ex) {
                            Toast.makeText(getApplicationContext(), "Set Wallpaper Failed!", Toast.LENGTH_LONG).show();
                            ex.printStackTrace();
                        }
                        pg.dismiss();
                    }
                });
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public ArrayList<Wallpaper> wallpapers = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<Wallpaper> wallpapers) {
            super(fm);
            this.wallpapers = wallpapers;
        }

        @Override
        public Fragment getItem(int position) {

            Wallpaper wallpaper = wallpapers.get(position);
            return PlaceholderFragment.newInstance(position, wallpaper.wallpaperTitle, wallpaper.wallpaperURL);
        }

        @Override
        public int getCount() {
            return wallpapers.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return wallpapers.get(position).wallpaperTitle;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        String name, url;
        int pos;
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_IMG_TITLE = "image_title";
        private static final String ARG_IMG_URL = "image_url";

        @Override
        public void setArguments(Bundle args) {
            super.setArguments(args);
            this.pos = args.getInt(ARG_SECTION_NUMBER);
            this.name = args.getString(ARG_IMG_TITLE);
            this.url = args.getString(ARG_IMG_URL);
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, String name, String url) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_IMG_TITLE, name);
            args.putString(ARG_IMG_URL, url);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onStart() {
            super.onStart();

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            final ImageView imageView = (ImageView) rootView.findViewById(R.id.detail_image);

            Glide.with(this)
                    .load(url)
                    .asBitmap()
                    .thumbnail(0.5f)
                    .error(R.drawable.ic_no_image)
                    .placeholder(R.drawable.ic_no_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);

            return rootView;
        }

    }
}