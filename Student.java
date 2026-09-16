package TugasP2;

public class Student {
    String name;
    double score;
    boolean passed;

    public Student(String name, double score) {
        this.name = name;
        this.score = score;
    }

    public void checkPassed() {
        if (this.score >= 70) {
            this.passed = true;
        } else {
            this.passed = false;
        }
    }
}
