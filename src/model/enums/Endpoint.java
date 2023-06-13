package model.enums;

public enum Endpoint {
    GET_TASKS,
    GET_TASKS_TASK,
    GET_TASKS_TASK_ID,
    GET_TASKS_EPIC,
    GET_TASKS_EPIC_ID,
    GET_TASKS_SUBTASK,
    GET_TASKS_SUBTASK_EPIC_ID,
    GET_TASKS_HISTORY,
    POST_TASKS_TASK,
    POST_TASKS_EPIC,
    POST_TASKS_SUBTASK,
    DELETE_TASKS_TASK,
    DELETE_TASKS_TASK_ID,
    DELETE_TASKS_SUBTASK_ID,
    DELETE_TASKS_EPIC_ID,
    UNKNOWN
}