package kalymnos.skemelio.kava.Model;

public class Quantity {
    public int atom, container;

    public Quantity(int atom, int container) {
        this.atom = atom;
        this.container = container;
    }

    public void addAtom() {
        ++atom;
    }

    public void addContainer() {
        ++container;
    }
}
