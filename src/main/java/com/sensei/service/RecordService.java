package com.sensei.service;


import com.sensei.entity.User;
import com.sensei.entity.dto.RecordsContainerDto;
import com.sensei.repository.RecordRepository;
import com.sensei.entity.Record;
import com.sensei.entity.RecordStatus;
import com.sensei.entity.dto.RecordsContainerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class RecordService {
    private  final RecordRepository recordRepository;
    private final UserService userService;

    @Autowired
    public RecordService(RecordRepository recordRepository, UserService userService) {
        this.recordRepository = recordRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public RecordsContainerDto findAllRecords(String filterMode){
        User user = userService.getCurrentUser();
        List<Record> records  = user.getRecords().stream()
                .sorted(Comparator.comparingInt(Record::getId))
                .collect(Collectors.toList());
        int numberOffDoneRecords = (int)records.stream().filter(record -> record.getStatus() == RecordStatus.DONE).count();
        int numberOffActiveRecords = (int)records.stream().filter(record -> record.getStatus() == RecordStatus.ACTIVE).count();

        if (filterMode == null || filterMode.isBlank()){
            return new RecordsContainerDto(user.getName(),records,numberOffDoneRecords,numberOffActiveRecords);
        }

        String filterModeInUpperCase = filterMode.toUpperCase();
        List<String> allowedFilterModes = Arrays.stream(RecordStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        if(allowedFilterModes.contains(filterModeInUpperCase)){
            List<Record> filteredRecords =  records.stream()
                    .filter(record -> record.getStatus() == RecordStatus.valueOf(filterModeInUpperCase))
                    .collect(Collectors.toList());
            return new RecordsContainerDto(user.getName(),filteredRecords,numberOffDoneRecords,numberOffActiveRecords);

        }
        else return new RecordsContainerDto(user.getName(),records,numberOffDoneRecords,numberOffActiveRecords);
    }

    public void saveRecord(String title){
        if (title != null && !title.isBlank()){
            User user = userService.getCurrentUser();
            recordRepository.save(new  Record(title,user));
        }
    }

    public void updateRecordStatus(int id,RecordStatus newStatus){

        recordRepository.update(id, newStatus);

    }

    public void deleteRecord(int id){
        recordRepository.deleteById(id);
    }
}
