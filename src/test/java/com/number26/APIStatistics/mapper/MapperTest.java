package com.number26.APIStatistics.mapper;

import com.number26.APIStatistics.enums.Status;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

public class MapperTest {

    @Test
    public void shouldReturn204IfInvalid(){
        assertEquals(new ResponseEntity<Void>(HttpStatus.NO_CONTENT), Mapper.responseMapper(Status.INVALID));
    }

    @Test
    public void shouldReturn200IfInvalid(){
        assertEquals(new ResponseEntity<Void>(HttpStatus.OK), Mapper.responseMapper(Status.SUCCESS));
    }

    @Test
    public void shouldReturn500IfInvalid(){
        assertEquals(new ResponseEntity<Void>(HttpStatus.BAD_REQUEST), Mapper.responseMapper(Status.FAILLED));
    }

}