package ar.edu.utn.frc.tup.lciv.dtos.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
