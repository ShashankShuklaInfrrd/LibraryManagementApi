package ai.infrrd.LibraryProject.exception;

public class ApiException extends Exception {
    public String getMessage;
    private String message;
    private int code;

    public ApiException(int code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getCode() {
        return this.code;
    }
    public void setCode(int code) {
        this.code = code;
    }

}
