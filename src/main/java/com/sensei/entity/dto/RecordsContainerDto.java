package com.sensei.entity.dto;

import com.sensei.entity.Record;

import java.util.List;

public class RecordsContainerDto {
    private final String userName;
    private final List<Record> records;
    private final int numberOfDoneRecords;
    private final int NumberOfActiveRecords;

    public RecordsContainerDto(String userName, List<Record> records, int numberOfDoneRecords, int NumberOfActiveRecords) {
        this.userName = userName;
        this.records = records;
        this.numberOfDoneRecords = numberOfDoneRecords;
        this.NumberOfActiveRecords = NumberOfActiveRecords;
    }

    public String getUserName() {
        return userName;
    }

    public List<Record> getRecords() {
        return records;
    }

    public int getNumberOfDoneRecords() {
        return numberOfDoneRecords;
    }

    public int getNumberOfActiveRecords() {
        return NumberOfActiveRecords;
    }

}
