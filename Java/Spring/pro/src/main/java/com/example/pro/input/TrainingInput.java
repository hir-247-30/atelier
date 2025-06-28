package com.example.pro.input;

import java.io.Serializable;
import java.util.Date;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

@SuppressWarnings("serial")
public class TrainingInput implements Serializable {
    private String id;
    
    @NotBlank(message = "タイトルは必須です")
    private String title;
    
    @NotNull(message = "開始日時は必須です")
    private Date startDateTime;
    
    @NotNull(message = "終了日時は必須です")
    private Date endDateTime;
    
    @Min(value = 0, message = "予約数は0以上にしてください")
    private int reserved;

    @Min(value = 1, message = "定員は1以上にしてください")
    private int capacity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}