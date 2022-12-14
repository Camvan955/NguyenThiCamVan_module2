package ss15_exception_debug.exercise.exercise2.esercise1.model;

public class Teacher extends Person implements Comparable<Teacher> {
    private String technique;

    public Teacher () {
    }

    public Teacher(String technique) {
        this.technique = technique;
    }

    public Teacher(String code, String name, String dayOfBirth, Boolean gender, String technique) {
        super(code, name, dayOfBirth, gender);
        this.technique = technique;
    }

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    @Override
    public String toString() {
        return  super.toString() +
                " technique='" + technique + '\'' +
                '}';
    }

    @Override
    public int compareTo(Teacher o) {
        if (this.getName().compareTo(o.getName()) != 0 ) {
            return this.getName().compareTo(o.getName());
        }
        return this.getCode().compareTo(o.getCode());
    }
}
