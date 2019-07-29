package selab.threetier.presentation;

import org.json.JSONArray;
import org.json.JSONObject;
import selab.threetier.logic.Task;
import java.io.InputStream;
import java.util.ArrayList;

public class TasksListPresentation extends JSONPresentation {
    @Override
    public JSONObject getData(String method, InputStream body) {
        JSONObject result = new JSONObject();
        ArrayList<Task> tasks = Task.getAll();
        ArrayList<Task> publishedTasks = new ArrayList<Task>();
        for(int i=0;i<tasks.size();i++) {
            Task t = tasks.get(i);

            if(t.getPublished()==true) {
                publishedTasks.add(tasks.get(i));
            }
        }
        result.put("tasks", new JSONArray(publishedTasks));
        return result;
    }
}
