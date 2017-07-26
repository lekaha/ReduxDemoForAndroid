package com.ntop.demo;

import android.app.Application;

import com.ntop.demo.redux.Reducer;
import com.ntop.demo.redux.Redux;
import com.ntop.demo.todo.ActionHelper;
import com.ntop.demo.todo.AppState;
import com.ntop.demo.todo.TodoStore;
import com.ntop.demo.todo.reducers.FilterReducer;
import com.ntop.demo.todo.reducers.TodoReducer;

/**
 * Created by ntop on 16/6/22.
 */
public class App extends Application {
    private TodoStore todoStore;
    private ActionHelper actionHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        di();
    }

    /**
     * 此处的key{ "filter", "tasks" } 对应AppState对象的属性字段名称
     */
    private void di() {
        Reducer taskReducer = new TodoReducer();
        Reducer filterReducer = new FilterReducer();
        String[] keys = new String[]{ "filter", "tasks" };
        Reducer[] reducers = new Reducer[] { filterReducer, taskReducer };
        Reducer combineReducer = Redux.combineReducers(AppState.class, keys, reducers);

        todoStore = Redux.createStore(TodoStore.class, combineReducer);
        actionHelper = ActionHelper.newActionHelper(todoStore);
    }

    public TodoStore getStore() {
        return todoStore;
    }

    public ActionHelper getActionHelper() {
        return actionHelper;
    }
}
