package si.unm_fei.logic;

public class Question {
    public String text;
    public String[] answers;
    public String correct;
    public Kategorija kategorija;

    public Question(String text, String[] answers, String correct, Kategorija kategorija) {
        this.text = text;
        this.answers = answers;
        this.correct = correct;
        this.kategorija=kategorija;
    }
    public Kategorija getKategorija(){
        return kategorija;
    }
}

