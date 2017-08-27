package com.fime.fsw.huella.huella.Data.Modelos.RealmObjects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Quique on 29/06/2017.
 */

public class Task extends RealmObject {

    //Table fields de Task
    public static final String _ID_FIELD = "_id";
    public static final String SEQUENCE_FIELD = "sequence";
    public static final String TASK_STATE_FIELD = "taskState";

    //Keys para el objeto Task
    public static final String SEQUENCE_KEY = "taskSequence";
    public static final String PERIOD_KEY = "taskPeriod";
    public static final String LANGUAGE_KEY = "taskLanguage";
    public static final String GROUP_KEY = "taskGroup";
    public static final String MODALITY_KEY = "taskModality";


    //Estados del Task
    public static final int STATE_NO_HA_PASADO = 0;
    public static final int STATE_PASO_VINO_MAESTRO = 1;
    public static final int STATE_PASO_NO_VINO_MAESTRO = 2;

    @PrimaryKey
    private String _id;
    private int sequence;
    private String period;
    private String language;
    private String group;
    private Room room;
    private Assignment assignment;
    private Owner owner;
    private String modality;
    private Checkout checkout;
    private int taskState;

    public Task(){}

    public static Task create(String _id, int sequence, String period, String language, String group, Room room, Assignment assignment, Owner owner, String modality, Checkout checkout) {
        Task task = new Task();
        task._id = _id;
        task.sequence = sequence;
        task.period = period;
        task.language = language;
        task.group = group;
        task.room = room;
        task.assignment = assignment;
        task.owner = owner;
        task.modality = modality;
        task.checkout = checkout;
        task.taskState = 0;
        return task;
    }

    public String get_id() {
        return _id;
    }

    public int getSequence() {
        return sequence;
    }

    public String getPeriod() {
        return period;
    }

    public String getLanguage() {
        return language;
    }

    public String getGroup() {
        return group;
    }

    public Room getRoom() {
        return room;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getModality() {
        return modality;
    }

    public Checkout getCheckout() {
        return checkout;
    }

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }

    @Override
    public String toString() {
        return "Task{" +
                "_id='" + _id + '\'' +
                ", sequence=" + sequence +
                ", period='" + period + '\'' +
                ", language='" + language + '\'' +
                ", group='" + group + '\'' +
                ", room=" + room +
                ", assignment=" + assignment +
                ", owner=" + owner +
                ", modality='" + modality + '\'' +
                ", checkout=" + checkout +
                ", taskState=" + taskState +
                '}';
    }
}