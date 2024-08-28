package escuelaing.edu.co.spark;

import java.util.ArrayList;

public class ExerciseService {
    private ArrayList<Exercise> exercises = new ArrayList<Exercise>();

    public ArrayList<Exercise> getExercises(){
        return exercises;
    }

    public void addExercise(String name, int series){
        exercises.add(new Exercise(name, series));
    }
}