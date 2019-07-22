package kalymnos.skemelio.kava.Model;

import java.io.Serializable;

public class Quantity implements Serializable {
    private int atom, container;

    Quantity(int atom, int container) {
        this.atom = atom;
        this.container = container;
    }

    public int getAtom() {
        return atom;
    }

    public int getContainer() {
        return container;
    }

    public void addAtom() {
        ++atom;
    }

    public void addContainer() {
        ++container;
    }

    public void removeAtom() {
        if (atom == 0) {
            return;
        }
        --atom;
    }

    public void removeContainer() {
        if (container == 0) {
            return;
        }
        --container;
    }

    public void reset() {
        container = 0;
        atom = 0;
    }

    public String getQuantityText(String containersLabel, String atomsLabel) {
        return String.format("%s %d, %s %d", containersLabel, getContainer(), atomsLabel, getAtom());
    }
}
