package selab.threetier.logic;

import selab.threetier.storage.Storage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.SimpleFormatter;

class CustomComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        return o1.getStart().compareTo(o2.getStart());
    }
}

public class Task extends Entity {
    private Integer id;
    private String title;
    private Date start;
    private Date end;
    private Boolean published;

    public void setDeleted () { published = false; }
    public boolean getPublished () { return published;}
    public String getTitle() { return title; }
    public void setTitle(String value) { published = true; title = value; }

    public void setStart(Date value) { start = value; }
    public String getStartDate() {
        return new SimpleDateFormat("YYYY-MM-DD").format(start);
    }
    public String getStartTime() {
        return new SimpleDateFormat("HH:mm:ss").format(start);
    }

    public Date getStart() {
        return start;
    }
    public Date getEnd() {
        return end;
    }
    public boolean checkOverlap() {
        if(start == null || end == null){
            return false;
        }
        if(start.compareTo(end) > 0){
            return true;
        }
        ArrayList<Task> tasks = Task.getAll();
        for(int i = 0;i<tasks.size();i++){
            Task task = tasks.get(i);
            if(!task.getPublished()){
                continue;
            }
            if(start.compareTo(task.getEnd()) > 0 || end.compareTo(task.getStart()) < 0){
                continue;
            }
            return true;
        }
        return false;
    }

    public void setEnd(Date value) { end = value; }
    public String getEndTime() {
        return new SimpleDateFormat("HH:mm:ss").format(end);
    }

    public void save() {
        if(checkOverlap()){
            return;
        }
        System.out.println("SAVING");
        Storage.getInstance().getTasks().addOrUpdate(this);
    }

    public static ArrayList<Task> getAll() {
        ArrayList<Task> tasks = Storage.getInstance().getTasks().getAll();
        ArrayList<Task> publishedTasks = new ArrayList<Task>();
        for(int i=0;i<tasks.size();i++) {
            Task t = tasks.get(i);
            if(t.getPublished()) {
                publishedTasks.add(tasks.get(i));
            }
        }
        Collections.sort(publishedTasks,new CustomComparator());
        return publishedTasks;
    }
    public static void deleteTask(Integer id){
        System.out.println("HEREEEE");
        Task t = new Task();
        t.setId(id);
        t.setDeleted();
        t.save();
    }
}
