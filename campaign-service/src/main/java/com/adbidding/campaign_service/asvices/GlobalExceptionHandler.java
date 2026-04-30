package com.adbidding.campaign_service.asvices;



import com.adbidding.campaign_service.exceptions.BudgetExceededException;
import com.adbidding.campaign_service.exceptions.CampaignNotFoundException;
import com.adbidding.campaign_service.exceptions.InvalidCampaignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CampaignNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(CampaignNotFoundException ex, HttpServletRequest request) {
        return new ErrorResponse(
                ex.getMessage(),
                ex.getErrorCode(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(InvalidCampaignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalid(InvalidCampaignException ex, HttpServletRequest request) {
        return new ErrorResponse(
                ex.getMessage(),
                ex.getErrorCode(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(BudgetExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBudget(BudgetExceededException ex, HttpServletRequest request) {
        return new ErrorResponse(
                ex.getMessage(),
                ex.getErrorCode(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception ex, HttpServletRequest request) {
        return new ErrorResponse(
                "Something went wrong",
                "INTERNAL_SERVER_ERROR",
                request.getRequestURI()
        );
    }
}