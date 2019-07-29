package selab.threetier.presentation;

import org.json.JSONArray;
import org.json.JSONObject;
import selab.threetier.logic.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import selab.threetier.logic.Task;

public class RemoveTaskPresentation extends JSONPresentation {

    @Override
    public JSONObject getData(String method, InputStream body) throws IOException {
        if (!method.equals("POST"))
            throw new IOException("Method not supported");

        JSONObject request = new JSONObject(new BufferedReader(new InputStreamReader(body)).lines().collect(Collectors.joining("\n")));

        int id = request.getInt("id");

        //
        Task.deleteTask(id);
        System.out.println("DELETED SUCCESSFULLY!");
        JSONObject result = new JSONObject();
        ArrayList<Task> tasks = Task.getAll();
        ArrayList<Task> publishedTasks = new ArrayList<Task>();
        for(int i=0;i<tasks.size();i++) {
            Task t = tasks.get(i);
            if(t.getPublished()) {
                publishedTasks.add(tasks.get(i));
            }
        }
        result.put("tasks", new JSONArray(publishedTasks));
        return result;
    }
}
