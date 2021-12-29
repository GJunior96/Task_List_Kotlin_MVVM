package com.example.tasklist.di

import android.app.Application
import androidx.room.Room
import com.example.tasklist.feature_task.data.data_source.TaskDataBase
import com.example.tasklist.feature_task.data.repository.TaskRepositoryImp
import com.example.tasklist.feature_task.domain.repository.TaskRepository
import com.example.tasklist.feature_task.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskDataBase(app: Application): TaskDataBase {
        return Room.databaseBuilder(
            app,
            TaskDataBase::class.java,
            TaskDataBase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db: TaskDataBase): TaskRepository {
        return TaskRepositoryImp(db.taskDao)
    }

    @Provides
    @Singleton
    fun provideTaskUseCases(repository: TaskRepository): TaskUseCases {
        return TaskUseCases(
            getAllTasks = GetAllTasks(repository),
            getTasksByDate = GetTasksByDate(repository),
            deleteTask = DeleteTask(repository),
            addTask = AddTask(repository),
            getTask = GetTask(repository)
        )
    }
}