package escuelaing.edu.co.spark;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ListExerciseService implements RESTService{
    private ExerciseService exerciseService;

    public ListExerciseService(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @Override
public String response(String request) {
    ArrayList<Exercise> exercises = exerciseService.getExercises();
    return "{\"exercises\":[" + 
            exercises.stream()
            .map((Exercise exercise) -> String.format("{\"name\":\"%s\", \"series\":%d}", exercise.getName(), exercise.getSeries()))
            .collect(Collectors.joining(",")) + 
        "]}";
}
}
