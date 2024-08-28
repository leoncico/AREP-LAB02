package escuelaing.edu.co.spark;

public class Exercise {
    private String name;
    private int series;

    public Exercise(String name, int series){
        this.name = name;
        this.series = series;
    }

    public String getName(){
        return name;
    }

    public int getSeries(){
        return series;
    }
}
