package kalymnos.skemelio.kava.Model;

public class Bottle extends Item {
    public final String volume;

    public Bottle(int categoryId, String picturePath, String title, String volume) {
        super(categoryId, picturePath, title);
        this.volume = volume;
    }

    @Override
    public String toString() {
        return title + ", " + volume;
    }

    @Override
    public boolean equals(Object obj) {
        Bottle bottle = (Bottle) obj;
        if (bottle == null) return false;
        return hasSameValuesWith(bottle);
    }

    protected boolean hasSameValuesWith(Item item) {
        Bottle bottle = (Bottle) item;
        return super.hasSameValuesWith(bottle) && volume == bottle.volume;
    }
}
