package dtos;

public record UserRegisteredEvent(long userId, String firstName, String lastName) {
}