package com.bestbusiness.android.task;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private Button mAddButton;
    private Button mRemoveButton;
    private Button mNotifyButton;
    private NotificationManagerCompat notificationManager;
    private MyPagerAdapter mFragmentPagerAdapter;
    private Model mModel;

    private static final String PAGE_NUMBER = "com.bestbusiness.android.task.pageNumber";

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int page = intent.getIntExtra(PAGE_NUMBER, -1);
        if (page >= 0) {
            mViewPager.setCurrentItem(page);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mModel = Model.getModel();
        FragmentManager fragmentManager = getSupportFragmentManager();
        notificationManager = NotificationManagerCompat.from(getApplication());

        mViewPager = (ViewPager) findViewById(R.id.fragment_container);
        mAddButton = (Button) findViewById(R.id.button_add);
        mNotifyButton = (Button) findViewById(R.id.button_notify);
        mRemoveButton = (Button) findViewById(R.id.button_remove);

        mFragmentPagerAdapter = new MyPagerAdapter(fragmentManager);
        mViewPager.setAdapter(mFragmentPagerAdapter);


        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resultPos = mModel.add(mViewPager.getCurrentItem());
                mViewPager.getAdapter().notifyDataSetChanged();
                mViewPager.setCurrentItem(resultPos);
            }
        });


        mNotifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra(PAGE_NUMBER, mViewPager.getCurrentItem());
                i.setAction(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);

                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                Notification notification = new NotificationCompat.Builder(getApplication())
                        .setTicker(null)
                        .setSmallIcon(android.R.drawable.ic_menu_report_image)
                        .setContentTitle(getResources().getString(R.string.notification_title))
                        .setContentText(getResources().getString(R.string.notification_text) + mModel.getItem(mViewPager.getCurrentItem()))
                        .setContentIntent(pi)
                        .setSound(uri)
                        .setAutoCancel(true)
                        .build();
                notificationManager.notify(mModel.getItem(mViewPager.getCurrentItem()), notification);
            }
        });

        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationManager.cancel( mModel.remove(mViewPager.getCurrentItem()));
                mViewPager.getAdapter().notifyDataSetChanged();


            }
        });
    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MainFragment.newInstance(mModel.getItem(position));
        }

        @Override
        public int getItemPosition(Object object) {
            // refresh all fragments when data set changed
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mModel.itemCount();
        }
    }

}
