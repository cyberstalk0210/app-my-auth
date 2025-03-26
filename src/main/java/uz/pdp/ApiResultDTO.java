package uz.pdp;


public class ApiResultDTO {

    private boolean success;

    private String message;

    public ApiResultDTO(String message) {
        this.success = false;
        this.message = message;
    }

    public ApiResultDTO() {
        success = true;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
