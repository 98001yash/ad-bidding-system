package com.adbidding.auction_service.advices;


import com.adbidding.auction_service.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoBidsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoBids(NoBidsException ex, HttpServletRequest request) {
        return new ErrorResponse(ex.getMessage(),
                ex.getErrorCode(),
                request.getRequestURI());
    }

    @ExceptionHandler(InvalidBidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidBid(InvalidBidException ex, HttpServletRequest request) {
        return new ErrorResponse(ex.getMessage(),
                ex.getErrorCode(),
                request.getRequestURI());
    }

    @ExceptionHandler(AuctionTimeoutException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public ErrorResponse handleTimeout(AuctionTimeoutException ex, HttpServletRequest request) {
        return new ErrorResponse(ex.getMessage(),
                ex.getErrorCode(),
                request.getRequestURI());
    }

    @ExceptionHandler(BidderUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse handleBidderUnavailable(BidderUnavailableException ex, HttpServletRequest request) {
        return new ErrorResponse(ex.getMessage(),
                ex.getErrorCode(),
                request.getRequestURI());
    }

    @ExceptionHandler(AuctionProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleProcessing(AuctionProcessingException ex, HttpServletRequest request) {
        return new ErrorResponse(ex.getMessage(),
                ex.getErrorCode(),
                request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception ex, HttpServletRequest request) {
        return new ErrorResponse("Unexpected error occurred",
                "INTERNAL_ERROR",
                request.getRequestURI());
    }
}