package kalymnos.skemelio.kava.Model.pojos;

import java.io.Serializable;

public class Quantity implements Serializable {
    private int atom, container;

    public Quantity(int atom, int container) {
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

    public boolean isEmpty() {
        return atom == 0 && container == 0;
    }

    public String getFullText(String containersLabel, String atomsLabel) {
        return String.format("%s %d, %s %d", containersLabel, getContainer(), atomsLabel, getAtom());
    }

    public String getTextWithoutEmptyValues(String containersLabel, String atomsLabel) {
        if (atom == 0 && container == 0) {
            return null;
        } else if (atom == 0) {
            return String.format("%s %d", containersLabel, container);
        } else if (container == 0) {
            return String.format("%s %d", atomsLabel, atom);
        }
        return String.format("%s %d, %s %d", containersLabel, getContainer(), atomsLabel, getAtom());
    }

    @Override
    public boolean equals(Object obj) {
        Quantity other = (Quantity) obj;
        if (other == null) return false;
        return atom == other.atom && container == other.container;
    }

    @Override
    public int hashCode() {
        return atom * 2647 + container * 6173;
    }
}
