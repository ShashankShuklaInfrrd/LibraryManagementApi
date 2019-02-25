package ai.infrrd.LibraryProject.service;

import java.sql.*;
import ai.infrrd.LibraryProject.Book;
import ai.infrrd.LibraryProject.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class JDBCConnection {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/LIBRARY";
    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "admin123";
    private static final int BAD_REQUEST = 400;
    private static final String MSG = "incorrect data";


    public ResponseEntity<Book> updateConnection(Book book) throws ApiException {
        /*
         * Establish a connection with database and check for book existence.
         * If book not present in database then update the database with values provided by the user.
         */
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            String bookName = book.getBookName();
            String author = book.getAuthor();
            int bookId = book.getBookId();
            int quantity = book.getQuantity();
            try {
                if (bookId == 0 || quantity == 0 || bookName == null || author == null || validate(bookName)) {
                    throw new ApiException(BAD_REQUEST, MSG);
                }
            }
            catch (ApiException e) {
                return new ResponseEntity<Book>(HttpStatus.BAD_REQUEST);
            }

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "insert into BOOKS "
                    + " (book_id, book_name, author,quantity)" + " values (?, ?, ?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookId);
            stmt.setString(2, bookName);
            stmt.setString(3, author);
            stmt.setInt(4, quantity);
            stmt.executeUpdate();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ResponseEntity<Book>(book,HttpStatus.OK);

    }





    public Book retrieveConnection(Book book) {
        /*
          check for a book to be present in database
          if present then display all the details of the book,else print no book to show.
         */
        Connection conn;
        Statement stmt ;
        try {
            Class.forName(JDBC_DRIVER);
        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String bookName = book.getBookName();
            String sql = "SELECT book_id,book_name,author,quantity FROM BOOKS"
                    + " WHERE book_name='"+bookName+"'";
            stmt=conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(!rs.next()) {
                System.out.println("no books to show");
            }
            else {
                do {
                    book.setBookId(rs.getInt("book_id"));
                    book.setBookName(rs.getString("book_name"));
                    book.setAuthor(rs.getString("author"));
                    book.setQuantity(rs.getInt("quantity"));
                }
                while(rs.next());
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return book;
    }



    private boolean validate(String book_name) {
        /**
         * check weather a particular book is present in database.
         * @param book_name: name of the book to be checked in database.
         * @return result:   boolean value denoting the presence of book in database.
         */
        Connection conn=null;
        Statement stmt =null;
        boolean result = false;
        try {
            Class.forName(JDBC_DRIVER);
        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT book_id,book_name,author,quantity FROM BOOKS"
                    + " WHERE book_name='" + book_name + "'";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            result = rs.next();
            rs.close();
            stmt.close();
            conn.close();

        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    }
