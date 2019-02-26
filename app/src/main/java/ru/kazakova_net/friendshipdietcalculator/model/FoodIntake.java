package ru.kazakova_net.friendshipdietcalculator.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by nkazakova on 15/02/2019.
 */
@Entity(tableName = "food_intake")
public class FoodIntake {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String type;
    @ColumnInfo(name = "time_millis")
    private long timeMillis;
    private String note;
    
    public FoodIntake() {
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public long getTimeMillis() {
        return timeMillis;
    }
    
    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    public boolean hasNote() {
        return !(this.note == null || this.note.equals(""));
    }
}
