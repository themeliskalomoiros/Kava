package kalymnos.skemelio.kava.persistance;

import android.content.Context;
import android.content.SharedPreferences;

public class TitleRepoImpl implements TitleRepo {
    private static final String TITLE_KEY = "checkout_user_title_key";
    private static final String TITLE_FILE = "checkout_title";

    private final SharedPreferences sp;

    public TitleRepoImpl(Context c) {
        sp = c.getSharedPreferences(TITLE_FILE, Context.MODE_PRIVATE);
    }

    @Override
    public void save(String title) {
        sp.edit().putString(TITLE_KEY, title).apply();
    }

    @Override
    public void clear() {
        sp.edit().clear().apply();
    }

    @Override
    public String loadTitle() {
        return sp.getString(TITLE_KEY, null);
    }


}
