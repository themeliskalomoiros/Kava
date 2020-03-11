package kalymnos.skemelio.kava.persistance;

public interface TitleRepo {
    void save(String title);

    void clear();

    String loadTitle();
}
