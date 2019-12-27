package sip;

public interface MessageProcessor
{
    void processMessage(User sender, String message);
    void processError(String errorMessage);
    void processInfo(String errorMessage);

    void processRegister(User user, String message);

    void processCancel(User fromUser, String message);
}
