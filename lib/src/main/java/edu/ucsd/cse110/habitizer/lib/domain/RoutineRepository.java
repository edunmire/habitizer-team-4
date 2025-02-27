package edu.ucsd.cse110.habitizer.lib.domain;

import java.util.List;

import edu.ucsd.cse110.habitizer.lib.util.Subject;

public interface RoutineRepository {
    // return a List of Routine
    Subject<List<Routine>> getRoutineList();

    // return a Routine with id
    Subject<Routine> getRoutineWithId(int routineId);

    // return the in-progess Routine
    Subject<Routine> getInProgressRoutine();

    // return a List of RoutineTask
    Subject<List<RoutineTask>> getTaskList(int routineId);

    Subject<RoutineTask> getTaskWithId(int id, int routineId);

    void updateInProgressRoutine(int newRoutineId, boolean newInProgress);

    void addTaskToRoutine(int routineId, RoutineTask task);

    void checkOffTask(int id, int routineId);
    boolean getIsTaskChecked(int id, int routineId);

    void updateTaskTitle(int id, int routineId, String newTitle);

    void updateTime(int routineId, String routineElapsedTime, String taskElapsedTIme);

    void updateGoalTime(int routineId, String newTime);

    void updateIsDone(int routineId, boolean newIsDone);

    void initializeStates(int routineId);

}
