package comw.example.rplrus26.midsemester12rpl;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int Tabs;

    public PagerAdapter(FragmentManager fm, int Tab) {
        super(fm);
        this.Tabs = Tab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment_nowPlaying nowPlaying = new fragment_nowPlaying();
                return nowPlaying;
            case 1:
                fragment_upcoming upcoming = new fragment_upcoming();
                return upcoming;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return Tabs;
    }
}
