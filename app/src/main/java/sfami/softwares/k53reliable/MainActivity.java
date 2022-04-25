package sfami.softwares.k53reliable;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private LearnersManualFragment learnersManualFragment;
    private DriversManualFragment driversManualFragment;
    private MoreFragment moreFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });

        MyRoadRuleData[] myRoadRuleData = new MyRoadRuleData[]{
                new MyRoadRuleData(GlobalElements.learnerFAQ1),
                new MyRoadRuleData(GlobalElements.learnerFAQ2),
                new MyRoadRuleData(GlobalElements.learnerFAQ3),
                new MyRoadRuleData(GlobalElements.learnerFAQ4),
                new MyRoadRuleData(GlobalElements.learnerFAQ5),
                new MyRoadRuleData(GlobalElements.learnerFAQ6),
                new MyRoadRuleData(GlobalElements.learnerFAQ7),
                new MyRoadRuleData(GlobalElements.learnerFAQ8),
                new MyRoadRuleData(GlobalElements.learnerFAQ9),
                new MyRoadRuleData(GlobalElements.learnerFAQ10),
                new MyRoadRuleData(GlobalElements.dummyRule),
//                new MyRoadRuleData(GlobalElements.dummyRule),
        };

        String toolbarTitle = String.format("FAQs (%s)", myRoadRuleData.length);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(toolbarTitle);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        learnersManualFragment = new LearnersManualFragment(myRoadRuleData);
        driversManualFragment = new DriversManualFragment(myRoadRuleData);
        moreFragment = new MoreFragment();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(learnersManualFragment, "LEARNER'S");
        viewPagerAdapter.addFragment(driversManualFragment, "DRIVER'S");
//        viewPagerAdapter.addFragment(moreFragment, "MORE");
        viewPager.setAdapter(viewPagerAdapter);

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}
