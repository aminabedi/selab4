package selab.threetier.presentation;

import org.json.JSONArray;
import org.json.JSONObject;
import selab.threetier.logic.Task;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;



public class TasksListPresentation extends JSONPresentation {
    @Override
    public JSONObject getData(String method, InputStream body) {
        JSONObject result = new JSONObject();
        ArrayList<Task> tasks = Task.getAll();
        result.put("tasks", new JSONArray(tasks));
        return result;
    }
}
