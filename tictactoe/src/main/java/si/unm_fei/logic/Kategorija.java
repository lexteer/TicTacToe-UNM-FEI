package si.unm_fei.logic;

public enum Kategorija {
    UPRAVLJANJE("Upravljanje in poslovanje"),
    INFORMATIKA("Poslovna informatika"),
    RACUNALNISTVO("Računalništvo in informatika"),
    OKOLJE("Upravljanje z okoljem");

    private final String fullName;

    Kategorija(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public static Kategorija fromString(String name) {
        for(Kategorija k : Kategorija.values()) {
            if(k.getFullName().equals(name)) {
                return k;
            }
        }
        throw new IllegalArgumentException("No enum constant with label: " + name);
    }
}
