package escuelaing.edu.co.spark;
import escuelaing.edu.co.spark.ListExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListExerciseServiceTest {

    private ExerciseService exerciseService;
    private ListExerciseService listExerciseService;

    @BeforeEach
    public void setUp() {
        exerciseService = new ExerciseService();
        listExerciseService = new ListExerciseService(exerciseService);
    }

    @Test
    void testResponse_EmptyList() {
        String request = "";
        String response = listExerciseService.response(request);
        assertEquals("{\"exercises\":[]}", response);
    }

    @Test
    void testResponse_WithExercises() {
        exerciseService.addExercise("Push-ups", 3);
        exerciseService.addExercise("Squats", 4);
        String request = ""; 
        String response = listExerciseService.response(request);
        assertEquals("{\"exercises\":[{\"name\":\"Push-ups\", \"series\":3},{\"name\":\"Squats\", \"series\":4}]}", response);
    }
}