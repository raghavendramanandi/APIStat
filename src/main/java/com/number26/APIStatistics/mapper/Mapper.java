package com.number26.APIStatistics.mapper;

import com.number26.APIStatistics.enums.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Mapper {
    public static ResponseEntity<Void> responseMapper(Status status){
        switch (status){
            case FAILLED:
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            case INVALID:
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            case SUCCESS:
                return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return null;
    }
}
