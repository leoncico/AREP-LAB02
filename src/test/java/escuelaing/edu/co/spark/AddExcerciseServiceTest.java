package escuelaing.edu.co.spark;

import escuelaing.edu.co.spark.AddExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddExerciseServiceTest {

    private ExerciseService exerciseService;
    private AddExerciseService addExerciseService;

    @BeforeEach
    void setUp() {
        exerciseService = new ExerciseService();
        addExerciseService = new AddExerciseService(exerciseService);
    }

    @Test
    void testResponse_InvalidInput1() {
        String request = "name=Push-ups&series=dos";
        String response = addExerciseService.response(request);

        assertEquals("{\"error\":\"Invalid request\"}", response);
        assertEquals(0, exerciseService.getExercises().size());
    }

    @Test
    void testResponse_InvalidInput2() {
        String request = "name=&series=3";
        String response = addExerciseService.response(request);

        assertEquals("{\"error\":\"Invalid request\"}", response);
        assertEquals(0, exerciseService.getExercises().size());
    }
}