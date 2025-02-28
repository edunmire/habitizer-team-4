package edu.ucsd.cse110.habitizer.app;

import android.app.Application;

import androidx.room.Room;

import edu.ucsd.cse110.habitizer.app.data.db.HabitizerDatabase;
import edu.ucsd.cse110.habitizer.app.data.db.RoomRoutineRepository;
import edu.ucsd.cse110.habitizer.lib.data.InMemoryDataSource;
import edu.ucsd.cse110.habitizer.lib.domain.RoutineRepository;

public class HabitizerApplication extends Application {
    private InMemoryDataSource dataSource;
    private RoutineRepository routineRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        var database = Room.databaseBuilder(
                        getApplicationContext(),
                        HabitizerDatabase.class,
                        "habitizer-database")
                .allowMainThreadQueries()
                .build();

        this.dataSource = InMemoryDataSource.fromDefault();
        this.routineRepository = new RoomRoutineRepository(
                database.routineDao(), database.routineTaskDao());

        var sharedPreferences = getSharedPreferences("habitizer", MODE_PRIVATE);
        var isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);

        routineRepository.clearRoutineTable();
        routineRepository.clearTaskTable();
        routineRepository.addRoutineList(dataSource.getRoutineList());


        if (isFirstRun && database.routineDao().getRoutineCount() == 0 &&
                database.routineTaskDao().count() == 0) {
            var routines = dataSource.getRoutineList();

            routineRepository.addRoutineList(routines);

            sharedPreferences.edit().putBoolean("isFirstRun", false).apply();
        }
    }

    public RoutineRepository getRoutineRepository() {
        return routineRepository;
    }
}
