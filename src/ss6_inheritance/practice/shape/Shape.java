package ss6_inheritance.practice.shape;

public class Shape {
    private String color = "green";
    private boolean filled = true;

    public Shape() {
    }

    public Shape(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }

    public Shape(double side, String color, boolean filled) {
    }

    public String getColor() {
        return color;
    }

    public Boolean isFilled() {
        return filled;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setFilled(Boolean filled) {
        this.filled = filled;
    }

    @Override
    public String toString() {
        return " Hình có màu: "
                + getColor()
                + " và "
                + (isFilled() ? "filled" : "not filled");
    }

}
