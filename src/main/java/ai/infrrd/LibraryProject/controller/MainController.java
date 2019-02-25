package ai.infrrd.LibraryProject.controller;

import ai.infrrd.LibraryProject.exception.ApiException;
import ai.infrrd.LibraryProject.service.JDBCConnection;
import ai.infrrd.LibraryProject.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class MainController {

    @RequestMapping(value = "/update", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> updateInDatabase(@RequestBody Book request) throws IOException, ApiException {

        JDBCConnection jdbcConnection = new JDBCConnection();
        return jdbcConnection.updateConnection(request);
    }


    @RequestMapping(value = "/retrieve", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String retrieveFromDatabase(@RequestBody Book request1) throws IOException {

        JDBCConnection jdbcConnection = new JDBCConnection();
        Book book = new Book();
        book = jdbcConnection.retrieveConnection(request1);
        ObjectMapper mapperObj = new ObjectMapper();
        String jsonStr = mapperObj.writeValueAsString(book);
        System.out.println("Success");
        return jsonStr;
    }
}

