package rondou.github.com.tablayoutdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    SimpleFragmentPagerAdapter pagerAdapter;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SmartTabLayout tab = (SmartTabLayout) findViewById(R.id.tab);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        pagerAdapter.pages().add(new FragmentPage().title("yo").fragment(new Func0<Fragment>() {
            @Override public Fragment call() {
                return PageFragment.newInstance(0);
            }
        }));
        pagerAdapter.pages().add(new FragmentPage().title("yo2").fragment(() -> PageFragment.newInstance(1)));
        pagerAdapter.pages().add(new FragmentPage().title("yo3").fragment(() -> PageFragment.newInstance(2)));

        pager.setAdapter(pagerAdapter);
        tab.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

        public SimpleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public Fragment getItem(int position) {
            return pages.get(position).fragment();
        }

        private List<FragmentPage> pages = new ArrayList<>();

        public List<FragmentPage> pages() { return pages; }

        public void add(FragmentPage page) {
            pages.add(page);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pages.get(position).title();
        }
    }

    public interface Func0<T> {
        public T call();
    }

    public class FragmentPage {
        Func0<Fragment> fragmentFunc;
        CharSequence title;

        public FragmentPage fragment(Func0<Fragment> func0) {
            fragmentFunc = func0;
            return this;
        }

        public FragmentPage title(CharSequence title) {
            this.title = title;
            return this;
        }

        public Fragment fragment() {
            return fragmentFunc.call();
        }

        public CharSequence title() {
            return title;
        }
    }

}
