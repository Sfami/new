package sfami.softwares.k53reliable;

import android.content.Intent;
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

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FragmentsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private LearnFragment learnFragment;
    private ProgressFragment progressFragment;
    private TestFragment testFragment;
    private String title, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MyRoadSignData[] myLearnFragmentData = new MyRoadSignData[]{
                new MyRoadSignData(GlobalElements.roadSignsSign),
                new MyRoadSignData(GlobalElements.roadRulesSign),
                new MyRoadSignData(GlobalElements.controlsSign),
        };


        MyRoadSignData[] myTestFragmentData = new MyRoadSignData[]{
                new MyRoadSignData(new Sign("Controls Test", "Take Controls example test.","","","",R.drawable.controls)),
                new MyRoadSignData(new Sign("Road Signs Test", "Take Controls example test.","","","",R.drawable.signs)),
                new MyRoadSignData(new Sign("Road Rules Test", "Take Controls example test.","","","",R.drawable.rules)),
                new MyRoadSignData(new Sign("K53 Test", "Take Controls example test.","","","",R.drawable.k53)),
                new MyRoadSignData(new Sign("Book Test", "Take Controls example test.","","","",R.drawable.books)),
        };

        DataBaseHelper dataBaseHelper = new DataBaseHelper(FragmentsActivity.this);
        List<TestResultModel> allTestsResults = dataBaseHelper.getAllTestsResults();
        //Get title using intent like before

        String toolbarTitle = String.format("Learner's Manual", myLearnFragmentData.length);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(toolbarTitle);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        learnFragment = new LearnFragment(myLearnFragmentData);
        testFragment = new TestFragment(myTestFragmentData);
        progressFragment = new ProgressFragment(allTestsResults);


        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(testFragment, "TEST");
        viewPagerAdapter.addFragment(learnFragment, "LEARN");
        viewPagerAdapter.addFragment(progressFragment, "PROGRESS");

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