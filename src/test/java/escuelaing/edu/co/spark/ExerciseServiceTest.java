package escuelaing.edu.co.spark;

import escuelaing.edu.co.spark.ExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseServiceTest {

    private ExerciseService exerciseService;

    @BeforeEach
    void setUp() {
        exerciseService = new ExerciseService();
    }

    @Test
    void testAddExercise() {
        exerciseService.addExercise("Dominadas", 3);
        ArrayList<Exercise> exercises = exerciseService.getExercises();
        assertEquals(1, exercises.size());

        Exercise exercise = exercises.get(0);
        assertEquals("Dominadas", exercise.getName());
        assertEquals(3, exercise.getSeries());
    }

    @Test
    void testGetExercises_NonEmptyList() {
        exerciseService.addExercise("Remo T", 4);
        exerciseService.addExercise("Pull-ups", 2);
        ArrayList<Exercise> exercises = exerciseService.getExercises();
        assertEquals(2, exercises.size());
        assertEquals("Remo T", exercises.get(0).getName());
        assertEquals(4, exercises.get(0).getSeries());

        assertEquals("Pull-ups", exercises.get(1).getName());
        assertEquals(2, exercises.get(1).getSeries());
    }

    @Test
    void testGetExercises_EmptyList() {
        ArrayList<Exercise> exercises = exerciseService.getExercises();
        assertTrue(exercises.isEmpty());
    }

}